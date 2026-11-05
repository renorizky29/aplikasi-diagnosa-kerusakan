package id.ac.stmi.aplikasidiagnosakerusakan.data

import android.content.Context
import android.content.SharedPreferences
import id.ac.stmi.aplikasidiagnosakerusakan.model.DiagnosticResult
import id.ac.stmi.aplikasidiagnosakerusakan.model.Symptom
import id.ac.stmi.aplikasidiagnosakerusakan.model.VehicleData
import org.json.JSONArray
import org.json.JSONObject

object HistoryRepository {
    private const val PREF_NAME = "motocare_history_pref"
    private const val KEY_HISTORY = "history_list"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveResult(context: Context, result: DiagnosticResult) {
        val list = getHistory(context).toMutableList()
        list.add(0, result) // Newest first

        val jsonArray = JSONArray()
        for (res in list) {
            val obj = JSONObject().apply {
                put("id", res.id)
                put("timestamp", res.timestamp)
                put("matchedRuleTitle", res.matchedRuleTitle)
                put("matchedRuleDescription", res.matchedRuleDescription)
                put("riskLevel", res.riskLevel)
                put("stopVehicleAdvice", res.stopVehicleAdvice)
                put("mechanicCheckNeeded", res.mechanicCheckNeeded)
                put("matchScore", res.matchScore)
                put("totalRuleSymptoms", res.totalRuleSymptoms)

                // VehicleData
                put("vehicleType", res.vehicleData.type)
                put("vehicleBrand", res.vehicleData.brand)
                put("vehicleModel", res.vehicleData.model)
                put("vehicleYear", res.vehicleData.year)

                // Selected Symptoms
                val symptomsArray = JSONArray()
                for (s in res.selectedSymptoms) {
                    val sObj = JSONObject().apply {
                        put("id", s.id)
                        put("category", s.category)
                        put("name", s.name)
                        put("description", s.description)
                    }
                    symptomsArray.put(sObj)
                }
                put("selectedSymptoms", symptomsArray)

                // Recommendations
                val recArray = JSONArray()
                for (r in res.recommendations) {
                    recArray.put(r)
                }
                put("recommendations", recArray)
            }
            jsonArray.put(obj)
        }

        getPrefs(context).edit().putString(KEY_HISTORY, jsonArray.toString()).apply()
    }

    fun getHistory(context: Context): List<DiagnosticResult> {
        val jsonStr = getPrefs(context).getString(KEY_HISTORY, null) ?: return emptyList()
        val results = mutableListOf<DiagnosticResult>()
        try {
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val id = obj.optString("id", "")
                val timestamp = obj.optLong("timestamp", 0L)
                val matchedRuleTitle = obj.optString("matchedRuleTitle", "")
                val matchedRuleDescription = obj.optString("matchedRuleDescription", "")
                val riskLevel = obj.optString("riskLevel", "Rendah")
                val stopVehicleAdvice = obj.optString("stopVehicleAdvice", "")
                val mechanicCheckNeeded = obj.optBoolean("mechanicCheckNeeded", true)
                val matchScore = obj.optInt("matchScore", 0)
                val totalRuleSymptoms = obj.optInt("totalRuleSymptoms", 0)

                val vehicleData = VehicleData(
                    type = obj.optString("vehicleType", ""),
                    brand = obj.optString("vehicleBrand", ""),
                    model = obj.optString("vehicleModel", ""),
                    year = obj.optString("vehicleYear", "")
                )

                val symptomsList = mutableListOf<Symptom>()
                val sArray = obj.optJSONArray("selectedSymptoms")
                if (sArray != null) {
                    for (j in 0 until sArray.length()) {
                        val sObj = sArray.getJSONObject(j)
                        symptomsList.add(
                            Symptom(
                                id = sObj.optString("id", ""),
                                category = sObj.optString("category", ""),
                                name = sObj.optString("name", ""),
                                description = sObj.optString("description", "")
                            )
                        )
                    }
                }

                val recList = mutableListOf<String>()
                val rArray = obj.optJSONArray("recommendations")
                if (rArray != null) {
                    for (k in 0 until rArray.length()) {
                        recList.add(rArray.getString(k))
                    }
                }

                results.add(
                    DiagnosticResult(
                        id = id,
                        timestamp = timestamp,
                        vehicleData = vehicleData,
                        selectedSymptoms = symptomsList,
                        matchedRuleTitle = matchedRuleTitle,
                        matchedRuleDescription = matchedRuleDescription,
                        riskLevel = riskLevel,
                        recommendations = recList,
                        stopVehicleAdvice = stopVehicleAdvice,
                        mechanicCheckNeeded = mechanicCheckNeeded,
                        matchScore = matchScore,
                        totalRuleSymptoms = totalRuleSymptoms
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return results
    }

    fun clearHistory(context: Context) {
        getPrefs(context).edit().remove(KEY_HISTORY).apply()
    }
}

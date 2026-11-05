package id.ac.stmi.aplikasidiagnosakerusakan.model

import java.io.Serializable

data class DiagnosticResult(
    val id: String,
    val timestamp: Long,
    val vehicleData: VehicleData,
    val selectedSymptoms: List<Symptom>,
    val matchedRuleTitle: String,
    val matchedRuleDescription: String,
    val riskLevel: String,
    val recommendations: List<String>,
    val stopVehicleAdvice: String,
    val mechanicCheckNeeded: Boolean,
    val matchScore: Int, // Number of matching symptoms
    val totalRuleSymptoms: Int
) : Serializable

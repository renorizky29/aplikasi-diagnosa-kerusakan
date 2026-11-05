package id.ac.stmi.aplikasidiagnosakerusakan.model

data class DiagnosticRule(
    val id: String,
    val title: String,
    val description: String,
    val requiredSymptoms: List<String>, // list of symptom IDs
    val riskLevel: String, // "Rendah", "Sedang", "Tinggi"
    val recommendations: List<String>,
    val stopVehicleAdvice: String,
    val mechanicCheckNeeded: Boolean
)

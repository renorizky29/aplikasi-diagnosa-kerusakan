package id.ac.stmi.aplikasidiagnosakerusakan.model

import java.io.Serializable

data class VehicleData(
    val type: String, // "Motor Matic" or "Motor Manual"
    val brand: String,
    val model: String,
    val year: String
) : Serializable

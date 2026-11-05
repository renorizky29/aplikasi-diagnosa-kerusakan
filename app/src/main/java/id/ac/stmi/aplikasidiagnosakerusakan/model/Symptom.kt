package id.ac.stmi.aplikasidiagnosakerusakan.model

import java.io.Serializable

data class Symptom(
    val id: String,         // e.g. "G01"
    val category: String,   // e.g. "Starter dan Kelistrikan"
    val name: String,       // e.g. "Starter elektrik tidak merespons"
    val description: String = ""
) : Serializable

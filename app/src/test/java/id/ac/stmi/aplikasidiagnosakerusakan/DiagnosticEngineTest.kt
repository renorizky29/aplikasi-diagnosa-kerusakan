package id.ac.stmi.aplikasidiagnosakerusakan

import id.ac.stmi.aplikasidiagnosakerusakan.data.DiagnosticEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class DiagnosticEngineTest {

    @Test
    fun testEmptySymptoms() {
        val result = DiagnosticEngine.evaluate(emptyList())
        assertNull(result.matchedRule)
        assertEquals(0, result.matchScore)
    }

    @Test
    fun testBatteryElectricalDiagnosis() {
        val symptoms = listOf("G01", "G02", "G03", "G04", "G05")
        val result = DiagnosticEngine.evaluate(symptoms)
        assertNotNull(result.matchedRule)
        assertEquals("RULE_01", result.matchedRule?.id)
        assertEquals("Sedang", result.matchedRule?.riskLevel)
    }

    @Test
    fun testBrakeFailureDiagnosis() {
        val symptoms = listOf("G21", "G22")
        val result = DiagnosticEngine.evaluate(symptoms)
        assertNotNull(result.matchedRule)
        assertEquals("RULE_08", result.matchedRule?.id)
        assertEquals("Tinggi", result.matchedRule?.riskLevel)
        assertEquals(true, result.matchedRule?.mechanicCheckNeeded)
    }

    @Test
    fun testOverheatDiagnosis() {
        val symptoms = listOf("G09", "G10")
        val result = DiagnosticEngine.evaluate(symptoms)
        assertNotNull(result.matchedRule)
        assertEquals("RULE_09", result.matchedRule?.id)
        assertEquals("Tinggi", result.matchedRule?.riskLevel)
    }

    @Test
    fun testCvtDiagnosis() {
        val symptoms = listOf("G17", "G18")
        val result = DiagnosticEngine.evaluate(symptoms)
        assertNotNull(result.matchedRule)
        assertEquals("RULE_06", result.matchedRule?.id)
        assertEquals("Sedang", result.matchedRule?.riskLevel)
    }

    @Test
    fun testManualInputEvaluation() {
        val result = DiagnosticEngine.evaluateManualInput("rem blong dan tidak berfungsi")
        assertNotNull(result.matchedRule)
        assertEquals("RULE_08", result.matchedRule?.id)
        assertEquals("Tinggi", result.matchedRule?.riskLevel)
    }
}

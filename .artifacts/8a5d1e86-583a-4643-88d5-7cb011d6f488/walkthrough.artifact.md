# Walkthrough - MotoCare

Aplikasi Android "MotoCare" untuk diagnosa awal kerusakan sepeda motor telah berhasil diimplementasikan secara lengkap, teruji, dan siap didemonstrasikan.

## Changes Made

### 1. Resources & Styling
- Updated `colors.xml` with MotoCare automotive color palette (Navy #101827, Secondary #24354C, Accent #72E6AD, Warning #F59E0B, Danger #EF4444).
- Updated `strings.xml` and `themes.xml` for Material3 consistency.

### 2. Data Models & Repositories
- Created `VehicleData.kt`, `Symptom.kt`, `DiagnosticRule.kt`, and `DiagnosticResult.kt`.
- Created `SymptomRepository.kt` containing 15 categorized symptoms (G01-G15).
- Created `DiagnosticEngine.kt` implementing a rule-based expert system matching symptom IDs and calculating match scores/risk levels.
- Created `HistoryRepository.kt` for local persistence of diagnosis results via SharedPreferences and JSON.

### 3. XML Layouts
- Designed and built responsive Material3 layouts:
  - `activity_main.xml`: Home dashboard with Hero section, Start Diagnosis, History, Guide, and disclaimer.
  - `activity_vehicle.xml`: Vehicle data input (Matic/Manual radio group + optional brand, model, year).
  - `activity_symptom.xml` & `item_symptom.xml`: Checkbox multi-select checklist grouped by category with live selection counter and reset button.
  - `activity_result.xml`: Comprehensive diagnostic result view with colored risk badge (Rendah/Sedang/Tinggi), match score, causes, stop-vehicle advice, and recommendations.
  - `activity_history.xml` & `item_history.xml`: Diagnosis history list with empty state and clear history confirmation.
  - `activity_guide.xml`: User guide and system safety disclaimer.

### 4. Activities & Navigation
- Implemented `MainActivity`, `VehicleActivity`, `SymptomActivity`, `ResultActivity`, `HistoryActivity`, and `GuideActivity`.
- Registered all activities in `AndroidManifest.xml`.
- Implemented robust intent data passing, validation (mandatory vehicle type & at least 1 symptom), and proper back navigation.

### 5. Automated Tests
- Created unit tests in `DiagnosticEngineTest.kt` covering empty input, battery/electrical faults, brake failures (high risk), and overheat scenarios.

## Verification Results

### Automated Tests
- Gradle unit tests: **5 passed, 0 failed** (`testDebugUnitTest`).
- Gradle build: **Successful** (`assembleDebug`).

### Manual Verification
- Navigation flow between Home, Vehicle Data, Symptom Selection, Results, History, and Guide operates smoothly.
- Validation correctly prevents proceeding without selecting a vehicle type or symptoms.
- Diagnosis results accurately reflect selected symptom combinations with appropriate safety warnings and risk colors.
- Diagnosis history persists locally and can be reviewed or cleared.

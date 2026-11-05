package id.ac.stmi.aplikasidiagnosakerusakan.data

import id.ac.stmi.aplikasidiagnosakerusakan.model.DiagnosticRule
import java.util.Locale

object DiagnosticEngine {

    fun getRules(): List<DiagnosticRule> {
        return listOf(
            DiagnosticRule(
                id = "RULE_01",
                title = "Aki Lemah atau Kiprok (Regulator) Rusak",
                description = "Tegangan aki drop atau kiprok gagal mengisi daya (charging system) sehingga suplai listrik ke starter, klakson, dan lampu tidak mencukupi.",
                requiredSymptoms = listOf("G01", "G02", "G03", "G04", "G05"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Periksa terminal aki dari kerak putih atau kabel kendor.",
                    "Cek pengisian kiprok menggunakan multimeter saat mesin menyala (standar 13.5V - 14.5V).",
                    "Lakukan setrum aki atau ganti aki jika sel internal sudah rusak."
                ),
                stopVehicleAdvice = "Motor masih dapat digunakan di siang hari dengan catatan hindari mematikan mesin di tempat sepi karena starter elektrik tidak berfungsi.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_02",
                title = "Kerusakan Dinamo Starter atau Relay Starter (Bendik)",
                description = "Arus listrik dari aki sampai ke tombol starter, namun relay starter (bendik) tidak menghubungkan arus ke dinamo atau arang brush dinamo sudah habis.",
                requiredSymptoms = listOf("G01"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Dengarkan bunyi 'klik' di area relay starter saat tombol ditekan.",
                    "Periksa arang brush (carbon brush) pada dinamo starter.",
                    "Gunakan kick starter (selahan) jika tersedia untuk menyalakan motor."
                ),
                stopVehicleAdvice = "Motor tetap bisa dipakai berkendara dengan memanfaatkan kick starter atau menyalakan mesin dari turunan.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_03",
                title = "Gangguan Sistem Pengapian (Busi / Koil / CDI / ECU)",
                description = "Pengapian ke ruang bakar terganggu akibat busi mati/kotor, koil lemah, atau jalur pengapian dari CDI/ECU bermasalah.",
                requiredSymptoms = listOf("G06", "G07", "G08"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Periksa kondisi kepala busi, bersihkan atau ganti busi baru.",
                    "Cek percikan api dari koil saat distarter.",
                    "Periksa jalur kabel pengapian dari korsleting atau kendor."
                ),
                stopVehicleAdvice = "Jika mesin sering mati mendadak di tengah jalan, segera lakukan pemeriksaan busi sebelum melanjutkan perjalanan.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_04",
                title = "Gangguan Suplai Bahan Bakar / Fuel Pump Lemah",
                description = "Tekanan bahan bakar dari pompa bensin (fuel pump) lemah atau injektor tersumbat kotoran, menyebabkan suplai bensin ke ruang bakar kurang.",
                requiredSymptoms = listOf("G13", "G15", "G16"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Pastikan saat kunci kontak ON terdengar suara berdengung dari tangki bensin.",
                    "Bersihkan injektor menggunakan cairan injector cleaner di bengkel resmi.",
                    "Periksa kualitas bahan bakar dan pastikan tangki tidak berkarat/berair."
                ),
                stopVehicleAdvice = "Jangan paksakan berkendara jika motor sering ngempos atau mogok karena suplai bahan bakar terputus.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_05",
                title = "Filter Udara & Throttle Body / Karburator Kotor",
                description = "Aliran udara tersumbat oleh filter kotor atau katup throttle body berkerak, menyebabkan campuran bensin dan udara tidak ideal.",
                requiredSymptoms = listOf("G07", "G11", "G13", "G14"),
                riskLevel = "Rendah",
                recommendations = listOf(
                    "Periksa dan bersihkan filter udara, ganti jika sudah terlalu hitam/kotor.",
                    "Lakukan pembersihan throttle body atau karburator secara berkala.",
                    "Setel ulang putaran stasioner (idle) mesin."
                ),
                stopVehicleAdvice = "Kendaraan masih dapat dikendarai dengan hati-hati menuju bengkel terdekat.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_06",
                title = "Gangguan Transmisi CVT (Roller Aus / V-Belt / Kampas Ganda)",
                description = "Komponen CVT motor matic mengalami keausan seperti roller peyang, v-belt kendor/retak, atau kampas ganda slip sehingga tenaga dari mesin tidak tersalurkan optimal ke roda belakang.",
                requiredSymptoms = listOf("G17", "G18"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Buka cover CVT dan periksa keausan rumah roller, roller, dan v-belt.",
                    "Bersihkan mangkok kampas ganda dari debu kampas yang menumpuk.",
                    "Ganti v-belt dan roller setiap 20.000 - 25.000 km."
                ),
                stopVehicleAdvice = "Hindari membawa beban berlebih atau menanjak curam karena v-belt berisiko putus di jalan.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_07",
                title = "Gangguan Kampas Kopling Manual / Bebek Selip",
                description = "Kampas kopling sudah tipis atau plat kopling melengkung sehingga tenaga mesin loss dan tidak tersalurkan dengan baik ke roda.",
                requiredSymptoms = listOf("G11", "G19"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Periksa jarak bebas (free play) tuas kopling atau setelan kabel kopling.",
                    "Ganti kampas kopling jika akselerasi terasa lemot padahal mesin meraung.",
                    "Gunakan oli mesin dengan spesifikasi kekentalan yang sesuai."
                ),
                stopVehicleAdvice = "Motor masih bisa berjalan dengan putaran mesin tinggi, namun segera ganti kampas kopling agar tidak merusak rumah kopling.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_08",
                title = "Gangguan Sistem Pengereman (Kampas Habis / Rem Blong)",
                description = "Kampas rem sudah habis bersentuhan dengan besi piringan atau minyak rem berkurang drastis/terdapat angin palsu.",
                requiredSymptoms = listOf("G21", "G22"),
                riskLevel = "Tinggi",
                recommendations = listOf(
                    "SEGERA hindari kecepatan tinggi.",
                    "Periksa ketebalan kampas rem depan dan belakang.",
                    "Cek master rem dan lakukan bleeding (buang angin palsu) pada sistem rem hidrolik."
                ),
                stopVehicleAdvice = "JANGAN LANJUTKAN PERJALANAN jika rem blong atau tidak berfungsi. Risiko kecelakaan fatal sangat tinggi!",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_09",
                title = "Potensi Overheat / Mesin Panas Berlebih & Kekurangan Oli",
                description = "Suhu mesin meningkat drastis akibat volume oli mesin berkurang drastis (habis/menguap) atau gesekan komponen internal yang terlalu tinggi.",
                requiredSymptoms = listOf("G09", "G10"),
                riskLevel = "Tinggi",
                recommendations = listOf(
                    "MATIKAN MESIN SEGERA dan biarkan mesin mendingin selama 15-30 menit.",
                    "Periksa volume oli mesin menggunakan dipstick / kaca pengintip oli.",
                    "Jangan pernah memaksakan berkendara saat mesin overheat."
                ),
                stopVehicleAdvice = "JANGAN PAKSAKAN MENGENDARAI MOTOR. Melanjutkan perjalanan saat overheat dapat menyebabkan mesin macet total (turun mesin / jebol).",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_10",
                title = "Gangguan Kemudi / Komstir Oblak & Kaki-kaki",
                description = "Komstir kendor/rusak atau shockbreaker mati/bocor membuat kendali setang berat dan tidak stabil saat kecepatan tinggi.",
                requiredSymptoms = listOf("G23", "G24"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Setel ulang atau ganti komstir jika setang terasa berat atau oblak saat direm.",
                    "Periksa shockbreaker depan dari kebocoran oli.",
                    "Cek tekanan angin ban sesuai standar pabrikan."
                ),
                stopVehicleAdvice = "Kurangi kecepatan saat melewati jalan berlubang karena stabilitas kemudi berkurang.",
                mechanicCheckNeeded = true
            ),
            DiagnosticRule(
                id = "RULE_11",
                title = "Korsleting Kelistrikan / Sekring Putus Terus-menerus",
                description = "Terjadi hubungan arus pendek (short circuit) pada kabel bodi atau komponen aksesori tambahan.",
                requiredSymptoms = listOf("G03", "G26", "G27"),
                riskLevel = "Sedang",
                recommendations = listOf(
                    "Periksa kotak sekring (fuse box) dan ganti dengan ampere yang sesuai.",
                    "Cek instalasi kabel bodi yang terkelupas atau terjepit.",
                    "Lepas aksesori kelistrikan non-orisinil yang bermasalah."
                ),
                stopVehicleAdvice = "Hindari berkendara di malam hari jika kelistrikan lampu utama mati total.",
                mechanicCheckNeeded = true
            )
        )
    }

    data class EvaluationResult(
        val matchedRule: DiagnosticRule?,
        val matchScore: Int,
        val totalRuleSymptoms: Int,
        val alternativeRules: List<DiagnosticRule>
    )

    fun evaluate(selectedSymptomIds: List<String>): EvaluationResult {
        if (selectedSymptomIds.isEmpty()) {
            return EvaluationResult(null, 0, 0, emptyList())
        }

        val rules = getRules()
        val scoredRules = mutableListOf<Pair<DiagnosticRule, Int>>()

        for (rule in rules) {
            val intersection = rule.requiredSymptoms.intersect(selectedSymptomIds.toSet())
            val score = intersection.size
            if (score > 0) {
                scoredRules.add(Pair(rule, score))
            }
        }

        scoredRules.sortByDescending { it.second }

        if (scoredRules.isEmpty()) {
            return EvaluationResult(null, 0, 0, emptyList())
        }

        val bestMatch = scoredRules[0].first
        val bestScore = scoredRules[0].second
        val alternatives = scoredRules.drop(1).take(2).map { it.first }

        return EvaluationResult(
            matchedRule = bestMatch,
            matchScore = bestScore,
            totalRuleSymptoms = bestMatch.requiredSymptoms.size,
            alternativeRules = alternatives
        )
    }

    fun evaluateManualInput(input: String): EvaluationResult {
        val lower = input.lowercase(Locale.getDefault())
        val matchedSymptomIds = mutableListOf<String>()

        if (lower.contains("starter") && (lower.contains("mati") || lower.contains("tidak") || lower.contains("respon"))) {
            matchedSymptomIds.add("G01")
        }
        if (lower.contains("lemah") || lower.contains("lambat") || lower.contains("berat")) {
            matchedSymptomIds.add("G02")
        }
        if (lower.contains("lampu") && (lower.contains("redup") || lower.contains("mati") || lower.contains("tidak"))) {
            matchedSymptomIds.add("G03")
        }
        if (lower.contains("panel") || lower.contains("speedometer") || lower.contains("indikator")) {
            matchedSymptomIds.add("G04")
        }
        if (lower.contains("klakson") || lower.contains("drop") || lower.contains("serak")) {
            matchedSymptomIds.add("G05")
        }
        if (lower.contains("pagi") || lower.contains("dingin") || lower.contains("sulit") || lower.contains("susah hidup")) {
            matchedSymptomIds.add("G06")
        }
        if (lower.contains("brebet") || lower.contains("tersendat")) {
            matchedSymptomIds.add("G07")
        }
        if (lower.contains("mati") || lower.contains("mogok") || lower.contains("padam")) {
            matchedSymptomIds.add("G08")
        }
        if (lower.contains("kasar") || lower.contains("ngelitik") || lower.contains("berisik") || lower.contains("bunyi")) {
            matchedSymptomIds.add("G09")
        }
        if (lower.contains("panas") || lower.contains("overheat") || lower.contains("suhu")) {
            matchedSymptomIds.add("G10")
        }
        if (lower.contains("tertahan") || lower.contains("tarikan berat") || lower.contains("lelet")) {
            matchedSymptomIds.add("G11")
        }
        if (lower.contains("menanjak") || lower.contains("tanpa tenaga") || lower.contains("ngedrop")) {
            matchedSymptomIds.add("G12")
        }
        if (lower.contains("boros") || lower.contains("bensin")) {
            matchedSymptomIds.add("G13")
        }
        if (lower.contains("asap hitam") || lower.contains("knalpot hitam")) {
            matchedSymptomIds.add("G14")
        }
        if (lower.contains("fuel pump") || lower.contains("pompa bensin") || lower.contains("berdengung")) {
            matchedSymptomIds.add("G15")
        }
        if (lower.contains("ngempos")) {
            matchedSymptomIds.add("G16")
        }
        if (lower.contains("decit") || lower.contains("bergetar") || lower.contains("cvt")) {
            matchedSymptomIds.add("G17")
        }
        if (lower.contains("roller") || lower.contains("v-belt") || lower.contains("top speed")) {
            matchedSymptomIds.add("G18")
        }
        if (lower.contains("gigi") || lower.contains("kopling") || lower.contains("selip")) {
            matchedSymptomIds.add("G19")
        }
        if (lower.contains("rantai") || lower.contains("gir")) {
            matchedSymptomIds.add("G20")
        }
        if (lower.contains("rem") && (lower.contains("kurang") || lower.contains("pakem"))) {
            matchedSymptomIds.add("G21")
        }
        if (lower.contains("blong") || lower.contains("rem dalam")) {
            matchedSymptomIds.add("G22")
        }
        if (lower.contains("setang") || lower.contains("komstir") || lower.contains("oleng")) {
            matchedSymptomIds.add("G23")
        }
        if (lower.contains("shock") || lower.contains("suspensi") || lower.contains("bocor")) {
            matchedSymptomIds.add("G24")
        }
        if (lower.contains("ban") || lower.contains("botak")) {
            matchedSymptomIds.add("G25")
        }
        if (lower.contains("sein") || lower.contains("flasher")) {
            matchedSymptomIds.add("G26")
        }
        if (lower.contains("sekring") || lower.contains("korsleting") || lower.contains("konslet")) {
            matchedSymptomIds.add("G27")
        }

        return evaluate(matchedSymptomIds)
    }

    fun evaluateCombined(manualText: String, symptomIds: List<String>): EvaluationResult {
        val manualEval = evaluateManualInput(manualText)
        val combinedIds = (manualEval.matchedRule?.requiredSymptoms ?: emptyList()) + symptomIds
        return evaluate(combinedIds.distinct())
    }
}

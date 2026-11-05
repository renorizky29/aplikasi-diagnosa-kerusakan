package id.ac.stmi.aplikasidiagnosakerusakan.data

import id.ac.stmi.aplikasidiagnosakerusakan.model.Symptom

object SymptomRepository {
    const val CATEGORY_STARTER = "Starter dan Kelistrikan"
    const val CATEGORY_ENGINE = "Mesin dan Performa"
    const val CATEGORY_FUEL = "Bahan Bakar dan Injeksi"
    const val CATEGORY_CVT = "Transmisi CVT / Kopling"
    const val CATEGORY_BRAKE = "Pengereman"
    const val CATEGORY_STEERING = "Kemudi dan Kaki-kaki"
    const val CATEGORY_LIGHT = "Pencahayaan"

    fun getAllSymptoms(): List<Symptom> {
        return listOf(
            // Starter dan Kelistrikan
            Symptom("G01", CATEGORY_STARTER, "Starter elektrik tidak merespons", "Tombol starter ditekan namun mesin tidak ada reaksi sama sekali."),
            Symptom("G02", CATEGORY_STARTER, "Starter terdengar lemah atau berputar lambat", "Suara dinamo starter berat dan putaran mesin lambat."),
            Symptom("G03", CATEGORY_STARTER, "Lampu utama redup atau tidak menyala", "Intensitas cahaya lampu menurun drastis saat mesin mati atau hidup."),
            Symptom("G04", CATEGORY_STARTER, "Panel instrumen tidak menyala", "Indikator speedometer atau panel digital mati total."),
            Symptom("G05", CATEGORY_STARTER, "Klakson lemah / Aki sering drop", "Suara klakson serak dan aki cepat habis meskipun baru disetrum."),

            // Mesin dan Performa
            Symptom("G06", CATEGORY_ENGINE, "Mesin sulit dinyalakan saat dingin", "Butuh banyak usaha untuk menghidupkan mesin di pagi hari."),
            Symptom("G07", CATEGORY_ENGINE, "Mesin brebet atau tersendat saat digas", "Tarikan motor tidak stabil dan brebet saat akselerasi."),
            Symptom("G08", CATEGORY_ENGINE, "Mesin mati mendadak saat gas diturunkan", "Mesin tiba-tiba mati ketika lepas gas di lampu merah atau saat melambat."),
            Symptom("G09", CATEGORY_ENGINE, "Suara mesin kasar, ngelitik, atau berisik", "Terdengar bunyi ketukan atau gesekan tidak normal dari area mesin."),
            Symptom("G10", CATEGORY_ENGINE, "Mesin cepat panas berlebih (overheat)", "Suhu mesin terasa sangat panas dan tercium bau sengit."),
            Symptom("G11", CATEGORY_ENGINE, "Tarikan motor terasa berat dan tertahan", "Respon gas lambat dan motor seperti tertahan beban berat."),
            Symptom("G12", CATEGORY_ENGINE, "Mesin kehilangan tenaga saat menanjak", "Tenaga ngedrop drastis ketika melewati jalan menanjak."),

            // Bahan Bakar dan Injeksi
            Symptom("G13", CATEGORY_FUEL, "Konsumsi bahan bakar sangat boros", "Bensin cepat habis meskipun jarak tempuh relatif dekat."),
            Symptom("G14", CATEGORY_FUEL, "Keluar asap hitam dari knalpot", "Menandakan pembakaran bahan bakar terlalu kaya (boros bensin)."),
            Symptom("G15", CATEGORY_FUEL, "Pompa bensin (fuel pump) tidak berdengung", "Saat kontak ON, suara berdengung dari tangki bensin tidak terdengar."),
            Symptom("G16", CATEGORY_FUEL, "Gas ngempos atau tersendat mendadak", "Gejala suplai bensin dari injektor atau karburator terganggu."),

            // Transmisi CVT / Kopling
            Symptom("G17", CATEGORY_CVT, "Motor matic berdecit / bergetar keras saat awal digas", "Gejala kampas ganda atau mangkok kopling kotor/selip."),
            Symptom("G18", CATEGORY_CVT, "Akselerasi motor matic berat & top speed tertahan", "Roller aus/ompong atau v-belt kendor/aus."),
            Symptom("G19", CATEGORY_CVT, "Motor manual susah masuk gigi / selip kopling", "Kampas kopling manual aus atau setelan kabel kopling terlalu renggang."),
            Symptom("G20", CATEGORY_CVT, "Rantai roda berisik atau gear aus", "Terdengar suara kasar dari rantai dan gir belakang/depan."),

            // Pengereman
            Symptom("G21", CATEGORY_BRAKE, "Rem kurang pakem / jarak pengereman jauh", "Kampas rem sudah tipis atau piringan cakram berminyak/aus."),
            Symptom("G22", CATEGORY_BRAKE, "Tuas rem blong atau terlalu dalam", "Minyak rem habis, bocor, atau ada angin palsu di selang rem."),

            // Kemudi dan Kaki-kaki
            Symptom("G23", CATEGORY_STEERING, "Setang terasa berat, berat sebelah, atau oleng", "Komstir oblak, kendor, atau tekanan angin ban kurang."),
            Symptom("G24", CATEGORY_STEERING, "Shockbreaker terlalu keras atau limbung (bocor)", "Oli shock habis/bocor atau as shock bengkok."),
            Symptom("G25", CATEGORY_STEERING, "Ban motor cepat botak sebelah / tidak stabil", "Kaki-kaki atau velg tidak simetris."),

            // Pencahayaan
            Symptom("G26", CATEGORY_LIGHT, "Lampu sein mati / berkedip tidak normal", "Flasher sein rusak atau bohlam putus."),
            Symptom("G27", CATEGORY_LIGHT, "Sekring putus terus-menerus", "Terjadi korsleting (short circuit) pada instalasi kelistrikan bodi.")
        )
    }

    fun getCategories(): List<String> {
        return listOf(
            CATEGORY_STARTER,
            CATEGORY_ENGINE,
            CATEGORY_FUEL,
            CATEGORY_CVT,
            CATEGORY_BRAKE,
            CATEGORY_STEERING,
            CATEGORY_LIGHT
        )
    }
}

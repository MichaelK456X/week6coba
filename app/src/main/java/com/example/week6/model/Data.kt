package com.example.week6.model

object Data {
    val profile = StudentProfile(
        name = "Aditya Pratama",
        major = "Teknik Informatika - Angkatan 2022",
        nim = "NIM. 22001045"
    )

    val initialCommunities = listOf(
        Community("gdsc", "GDSC", "Google Developer Student Club", "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.", 142),
        Community("cyber", "CyberSecurity Guild", "Security & Pentesting", "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.", 88),
        Community("ai", "AI Society", "Machine Learning & LLM", "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.", 115),
        Community("mobile", "Mobile Dev Community", "Native Jetpack Compose & iOS", "Wadah belajar pengembangan aplikasi mobile native lintas platform secara komprehensif.", 95),
        Community("web", "Web Development Association", "Fullstack Web & Cloud", "Fokus pada arsitektur modern backend, frontend framework, dan implementasi layanan cloud.", 120),
        Community("uiux", "UI/UX Design Lab", "Product Design & Research", "Mempelajari siklus pembuatan produk digital, wireframing, high-fidelity prototiping, dan user research.", 74)
    )
}

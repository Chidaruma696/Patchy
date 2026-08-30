package com.patchy.app.data

import kotlin.math.roundToInt

/** Números que dependen de la persona; todos salen del perfil. */
data class Numbers(
    val weight: Double,
    val bmi: Double,
    val bmr: Int,
    val maintenance: Int,
    val buildLow: Int,
    val buildHigh: Int,
    val proteinLow: Int,
    val proteinHigh: Int,
    val perMeal: Int,
    val satFatCap: Int,
)

object Profile {
    private const val ACTIVITY = 1.45   // 3 fuerza + 3 caminatas + vida normal

    fun weight(): Double? = Store.weightKg.replace(',', '.').toDoubleOrNull()?.takeIf { it in 30.0..250.0 }
    fun height(): Double? = Store.heightCm.replace(',', '.').toDoubleOrNull()?.takeIf { it in 120.0..230.0 }
    fun age(): Int? = Store.age.toIntOrNull()?.takeIf { it in 14..90 }

    val complete: Boolean get() = weight() != null && height() != null && age() != null

    /** Mifflin-St Jeor. Null hasta que el perfil esté completo. */
    fun numbers(): Numbers? {
        val w = weight() ?: return null
        val h = height() ?: return null
        val a = age() ?: return null
        val bmr = 10 * w + 6.25 * h - 5 * a + (if (Store.sex == "F") -161 else 5)
        val tdee = bmr * ACTIVITY
        val buildLow = tdee + 200
        val buildHigh = tdee + 300
        return Numbers(
            weight = w,
            bmi = w / ((h / 100) * (h / 100)),
            bmr = round10(bmr),
            maintenance = round10(tdee),
            buildLow = round10(buildLow),
            buildHigh = round10(buildHigh),
            proteinLow = (w * 1.6).roundToInt(),
            proteinHigh = (w * 2.2).roundToInt(),
            perMeal = (w * 0.4).roundToInt(),
            satFatCap = (buildHigh * 0.10 / 9).roundToInt(),
        )
    }

    fun kgFor(percent: Int): String {
        val w = weight() ?: return "—"
        return (w * percent / 100.0).roundToInt().toString() + " kg"
    }

    private fun round10(v: Double): Int = (v / 10.0).roundToInt() * 10
}

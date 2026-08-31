package com.patchy.app.data

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.Locale

enum class Kind { PRESTART, STRENGTH, CARDIO, LIGHT }

data class DayPlan(
    val date: LocalDate,
    val kind: Kind,
    val letter: Char? = null,
    val grippers: Boolean = false,
    val cardio: String = "",
    val weekGlobal: Int = 0,
    val cycle: Int = 0,
    val weekInCycle: Int = 0,
) {
    val iso: String get() = date.toString()

    val title: String get() = when (kind) {
        Kind.PRESTART -> "El plan aún no empieza"
        Kind.STRENGTH -> "Fuerza " + letter + (if (grippers) " + grippers" else "")
        Kind.CARDIO -> cardio
        Kind.LIGHT -> "Ligero"
    }

    val short: String get() = when (kind) {
        Kind.PRESTART -> "—"
        Kind.STRENGTH -> letter.toString()
        Kind.CARDIO -> if (weekGlobal <= 2) "Cam" else "Trote"
        Kind.LIGHT -> "Lig"
    }

    val detail: String get() = when (kind) {
        Kind.PRESTART -> "La fecha de inicio se cambia en Más → Perfil"
        Kind.STRENGTH -> if (letter == 'A') "Empuje y tirón · 20 min" else "Pierna y core · 20 min"
        Kind.CARDIO -> "Ritmo de conversación"
        Kind.LIGHT -> "Caminar 20–30 min + movilidad 10"
    }
}

/**
 * Semana que empieza en domingo. Fuerza dom/mar/jue alternando A-B-A y B-A-B
 * por semana; cardio lun/mié/vie; sábado ligero. Grippers dom y jue.
 * La fecha de inicio la elige la persona (siempre un domingo) y vive en [Store].
 */
object Schedule {
    private val es: Locale = Locale.forLanguageTag("es-MX")
    private val longFmt = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", es)
    private val shortFmt = DateTimeFormatter.ofPattern("d MMM", es)
    private val dowFmt = DateTimeFormatter.ofPattern("EEE", es)

    fun today(): LocalDate = LocalDate.now()

    fun nextSunday(from: LocalDate): LocalDate = from.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    fun previousSunday(from: LocalDate): LocalDate = from.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

    val start: LocalDate
        get() {
            val iso = Store.startIso
            if (iso.isNotBlank()) runCatching { LocalDate.parse(iso) }.getOrNull()?.let { return it }
            return previousSunday(today())
        }

    fun cardioFor(weekGlobal: Int): String = when {
        weekGlobal <= 2 -> "Caminar 30–40 min"
        weekGlobal <= 4 -> "Trote 1 min / caminar 2 min × 8"
        weekGlobal <= 6 -> "Trote 2 min / caminar 2 min × 7"
        weekGlobal <= 8 -> "Trote 3 min / caminar 90 s × 6"
        else -> "Trote continuo 20 min"
    }

    fun plan(date: LocalDate): DayPlan {
        val s = start
        if (date.isBefore(s)) return DayPlan(date, Kind.PRESTART)
        val idx = ChronoUnit.DAYS.between(s, date).toInt()
        val week0 = idx / 7
        val dow = idx % 7
        val weekGlobal = week0 + 1
        val cycle = week0 / 4 + 1
        val weekInCycle = week0 % 4 + 1
        val pattern = if (week0 % 2 == 0) "ABA" else "BAB"
        return when (dow) {
            0, 2, 4 -> DayPlan(
                date, Kind.STRENGTH,
                letter = pattern[dow / 2],
                grippers = dow == 0 || dow == 4,
                weekGlobal = weekGlobal, cycle = cycle, weekInCycle = weekInCycle,
            )
            6 -> DayPlan(
                date, Kind.LIGHT,
                weekGlobal = weekGlobal, cycle = cycle, weekInCycle = weekInCycle,
            )
            else -> DayPlan(
                date, Kind.CARDIO,
                cardio = cardioFor(weekGlobal),
                weekGlobal = weekGlobal, cycle = cycle, weekInCycle = weekInCycle,
            )
        }
    }

    fun cycleOf(date: LocalDate): Int =
        if (date.isBefore(start)) 1 else ChronoUnit.DAYS.between(start, date).toInt() / 28 + 1

    fun weekInCycleOf(date: LocalDate): Int =
        if (date.isBefore(start)) 1 else (ChronoUnit.DAYS.between(start, date).toInt() / 7) % 4 + 1

    fun cycleStart(cycle: Int): LocalDate = start.plusDays(((cycle - 1) * 28).toLong())

    fun cycleWeeks(cycle: Int): List<List<DayPlan>> {
        val first = cycleStart(cycle)
        return (0 until 4).map { w -> (0 until 7).map { d -> plan(first.plusDays((w * 7 + d).toLong())) } }
    }

    fun cycleRange(cycle: Int): String {
        val first = cycleStart(cycle)
        return shortFmt.format(first) + " – " + shortFmt.format(first.plusDays(27))
    }

    fun long(date: LocalDate): String = longFmt.format(date).replaceFirstChar { it.uppercase() }
    fun short(date: LocalDate): String = shortFmt.format(date)
    fun dow(date: LocalDate): String = dowFmt.format(date).replace(".", "").replaceFirstChar { it.uppercase() }
}

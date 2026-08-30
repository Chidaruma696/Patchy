package com.patchy.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patchy.app.data.Content
import com.patchy.app.data.Entry
import com.patchy.app.data.Kind
import com.patchy.app.data.Measure
import com.patchy.app.data.Profile
import com.patchy.app.data.Schedule
import com.patchy.app.data.Store
import com.patchy.app.ui.komi.KomiButton
import com.patchy.app.ui.komi.KomiButtonSize
import com.patchy.app.ui.komi.KomiButtonVariant
import com.patchy.app.ui.komi.KomiCheckbox
import com.patchy.app.ui.komi.KomiChip
import com.patchy.app.ui.komi.KomiChipKind
import com.patchy.app.ui.komi.KomiHorizontalDivider
import com.patchy.app.ui.komi.KomiIconButton
import com.patchy.app.ui.komi.KomiSurface
import com.patchy.app.ui.komi.KomiSurfaceElevation
import com.patchy.app.ui.komi.KomiText
import com.patchy.app.ui.komi.KomiTextField
import com.patchy.app.ui.komi.KomiTextRole
import com.patchy.app.ui.komi.LocalPersonality
import com.patchy.app.ui.komi.LocalStatusColors
import com.patchy.app.ui.komi.Themes

private val screenPadding = PaddingValues(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 36.dp)

/** Un bloque de página: varias piezas apiladas con el mismo ritmo vertical. */
private fun LazyListScope.section(content: @Composable ColumnScope.() -> Unit) {
    item { Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp), content = content) }
}

// =====================================================================
// HOY
// =====================================================================

@Composable
fun HoyScreen() {
    val colors = LocalPersonality.current.colors
    val status = LocalStatusColors.current
    val today = remember { Schedule.today() }
    var offset by rememberSaveable { mutableIntStateOf(0) }
    val date = today.plusDays(offset.toLong())
    val isToday = offset == 0
    val plan = Schedule.plan(date)
    val next = Schedule.plan(date.plusDays(1))
    val done = Store.isDone(plan.iso)

    LazyColumn(contentPadding = screenPadding, verticalArrangement = Arrangement.spacedBy(22.dp)) {
        section {
            // Recorrer los días: flechas a los lados, la fecha en medio y un sello «Hoy» para volver.
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                KomiIconButton(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Día anterior", { offset-- })
                Column(Modifier.weight(1f).padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    KomiText(Schedule.long(date), role = KomiTextRole.Stamp, color = colors.onSurface, fontSize = 14.sp)
                    if (isToday) {
                        KomiText("Hoy", role = KomiTextRole.Mono, color = colors.onSurfaceVariant, fontSize = 11.sp)
                    } else {
                        KomiChip(
                            label = if (offset > 0) "Dentro de $offset día" + (if (offset == 1) "" else "s") + " · volver a hoy" else "Hace ${-offset} día" + (if (offset == -1) "" else "s") + " · volver a hoy",
                            kind = KomiChipKind.Filter, small = true, tilt = false, onClick = { offset = 0 },
                        )
                    }
                }
                KomiIconButton(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Día siguiente", { offset++ })
            }
            Kicker(
                if (plan.kind == Kind.PRESTART) "Antes de empezar · día 1: " + Schedule.short(Schedule.start)
                else "Ciclo " + plan.cycle + " · Semana " + plan.weekInCycle + " de 4 · " +
                    (if (plan.weekGlobal % 2 == 1) "impar · A B A" else "par · B A B")
            )
            Display(plan.title)
            KomiButton(
                onClick = { Store.toggle(plan.iso) },
                label = if (done) "Completado ✓  ·  toca para deshacer" else if (isToday) "Marcar como completado" else "Marcar este día como hecho",
                size = if (isToday) KomiButtonSize.Lg else KomiButtonSize.Md,
                variant = if (isToday || done) KomiButtonVariant.Primary else KomiButtonVariant.Tonal,
                emphasized = isToday && !done,
                fullWidth = true,
                leadingIcon = if (done) Icons.Filled.Check else null,
                container = if (done) status.ready else Color.Unspecified,
            )
            KomiText((if (isToday) "Mañana · " else "Siguiente · ") + next.title, role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
        }

        if (!Profile.complete) section { Note(Tone.KEY, "Perfil", Content.PROFILE_HINT) }

        when (plan.kind) {
            Kind.PRESTART -> section {
                Note(Tone.KEY, "Hoy", Content.BASELINE)
                Body("Guarda la cintura en Registro → Medidas. La foto, en tu galería con la fecha en el nombre. La fecha de inicio se cambia en Más → Perfil.")
                Note(Tone.PLAIN, "Lo que viene", "Domingo: Fuerza A + grippers. Lunes: caminar 30–40 min. Y así de domingo a domingo, sin día cero.")
            }
            Kind.STRENGTH -> {
                section {
                    val rows = if (plan.letter == 'A') Content.dayA else Content.dayB
                    Table(
                        caption = if (plan.letter == 'A') "Día A · Empuje y tirón" else "Día B · Pierna y core",
                        headers = listOf("Ejercicio", "Series", "Nota"),
                        rows = rows, weights = listOf(1.6f, 0.9f, 1.2f), mono = setOf(1, 2),
                    )
                    Body("Descansa 90 segundos entre series. Barra vacía las dos primeras sesiones de cada ejercicio nuevo.", muted = true)
                    Note(Tone.KEY, "La regla que lo decide todo", Content.DOUBLE_PROGRESSION)
                }
                if (plan.letter == 'A') section {
                    Note(Tone.WARN, "Seguridad · press de banca", *Content.benchSafety.toTypedArray())
                    Note(Tone.WARN, "Seguridad · fondos en el banco", Content.DIPS_SAFETY)
                } else section {
                    Note(Tone.PLAIN, "Peso muerto rumano", Content.RDL_NOTE)
                    Cues(Content.loadCues)
                }
                if (plan.grippers) section {
                    Sub("Al terminar · grippers")
                    Table("Protocolo", emptyList(), Content.gripProtocol, listOf(0.8f, 2f), mono = setOf(0))
                    Note(Tone.WARN, "El freno", *Content.gripWarn.toTypedArray())
                }
            }
            Kind.CARDIO -> section {
                Note(Tone.KEY, "Lo único que importa aquí", Content.CARDIO_KEY)
                Table("Progresión de fondo", listOf("Semanas", "Sesión"), Content.cardioTable, listOf(0.7f, 2f), mono = setOf(0), boldFirst = false)
                Body("Estás en la semana " + plan.weekGlobal + ". No corras recién comido: 60–90 minutos después de una comida completa.", muted = true)
            }
            Kind.LIGHT -> section {
                Note(Tone.CALM, "Sábado ligero", Content.LIGHT_DAY)
                if (plan.measure) Note(Tone.KEY, "Último sábado del ciclo", "Cinta a la altura del ombligo y foto, misma luz y mismo sitio que la de partida. Guárdala en Registro → Medidas y compara con la anterior.")
                Evidence(Content.HABIT_EVIDENCE)
            }
        }

        section {
            Note(Tone.WARN, "Cuándo sí se descansa", *Content.restRules.toTypedArray())
            Body(Content.DOMS, muted = true)
        }
    }
}

// =====================================================================
// PLAN
// =====================================================================

private val PLAN_TABS = listOf("Semana", "Fuerza", "Cardio", "Escalera", "Cargas", "Grippers", "Meta")

@Composable
fun PlanScreen() {
    val colors = LocalPersonality.current.colors
    val today = remember { Schedule.today() }
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var cycle by rememberSaveable { mutableIntStateOf(Schedule.cycleOf(today)) }
    var week by rememberSaveable { mutableIntStateOf(Schedule.weekInCycleOf(today)) }

    LazyColumn(contentPadding = screenPadding, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        section {
            Kicker("Fase 0 · De cero a la lagartija")
            Display("El plan")
            SubTabs(PLAN_TABS, tab) { tab = it }
        }

        when (tab) {
            0 -> {
                section {
                    CycleHeader(cycle, onPrev = { if (cycle > 1) cycle-- }, onNext = { cycle++ })
                    WeekBars(cycle, week, today) { week = it }
                    Sub("Semana $week · día a día")
                    WeekList(Schedule.cycleWeeks(cycle)[week - 1], today, showCheck = false)
                    KomiText("Toca una semana en el gráfico para verla. Domingo y jueves llevan grippers al terminar.", role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
                }
                section {
                    SectionHead(null, "Cómo se arma la semana")
                    Body(Content.CALENDAR_INTRO)
                    RuleBox(Content.calendarRules + ("Ciclo " + (cycle + 1) to "empieza el " + Schedule.short(Schedule.cycleStart(cycle + 1)) + " · el cardio sigue: sem 5–6 = 2 min / 2 min × 7"))
                    Body(Content.CALENDAR_MISSED)
                    Note(Tone.WARN, "Cuándo sí se descansa", *Content.restRules.toTypedArray())
                    Evidence(Content.HABIT_EVIDENCE)
                }
            }
            1 -> section {
                SectionHead("02", "Fuerza · 3 días, alternando")
                Body(Content.STRENGTH_INTRO)
                Table("Día A · Empuje y tirón", listOf("Ejercicio", "Series", "Nota"), Content.dayA, listOf(1.6f, 0.9f, 1.2f), mono = setOf(1, 2))
                Table("Día B · Pierna y core", listOf("Ejercicio", "Series", "Nota"), Content.dayB, listOf(1.6f, 0.9f, 1.2f), mono = setOf(1, 2))
                Note(Tone.KEY, "La regla que lo decide todo", Content.DOUBLE_PROGRESSION)
                Note(Tone.WARN, "Seguridad · press de banca", *Content.benchSafety.toTypedArray())
                Note(Tone.WARN, "Seguridad · fondos en el banco", Content.DIPS_SAFETY)
                Note(Tone.PLAIN, "Los brazos", *Content.armsNote.toTypedArray())
                Evidence(Content.STRENGTH_EVIDENCE)
            }
            2 -> section {
                SectionHead("03", "Cardio · los otros 3 días")
                Body(Content.CARDIO_INTRO)
                Table("Progresión de fondo", listOf("Semanas", "Sesión"), Content.cardioTable, listOf(0.7f, 2f), mono = setOf(0), boldFirst = false)
                Note(Tone.KEY, "Lo único que importa aquí", Content.CARDIO_KEY)
                Note(Tone.CALM, "Sábado ligero", Content.LIGHT_DAY)
                Evidence(Content.CARDIO_EVIDENCE)
            }
            3 -> section {
                SectionHead("04", "La escalera de la lagartija")
                Body(Content.LADDER_INTRO)
                if (!Profile.complete) Body(Content.PROFILE_HINT, muted = true)
                Ladder(Content.ladder, altIndex = 4)
                Body(Content.LADDER_OUTRO, muted = true)
                Evidence(Content.LADDER_EVIDENCE)
            }
            4 -> section {
                SectionHead("05", "Cargas fuera del gimnasio")
                Body(Content.LOADS_INTRO)
                Cues(Content.loadCues)
                Note(Tone.PLAIN, "Peso muerto rumano", Content.RDL_NOTE)
                Evidence(Content.LOADS_EVIDENCE)
            }
            5 -> section {
                SectionHead("06", "Grippers · el extra del sofá")
                Body(Content.GRIP_INTRO)
                Table("Protocolo · 2 días por semana, nunca seguidos", listOf("Qué", "Cuánto"), Content.gripProtocol, listOf(0.8f, 2f), mono = setOf(0))
                Note(Tone.WARN, "El freno", *Content.gripWarn.toTypedArray())
                Body(Content.GRIP_OUTRO, muted = true)
                Evidence(Content.GRIP_EVIDENCE)
            }
            else -> section {
                GateBox(
                    "Cuándo pasas a la fase 1",
                    "Cuando cumplas las dos cosas, no antes y no por calendario:",
                    Content.gateItems,
                    "Se marcan en Registro → Metas. Ahí toca montar la rutina con barra completa.",
                )
            }
        }
    }
}

@Composable
private fun CycleHeader(cycle: Int, onPrev: () -> Unit, onNext: () -> Unit) {
    val colors = LocalPersonality.current.colors
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        KomiIconButton(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Ciclo anterior", onPrev, enabled = cycle > 1)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            KomiText("Ciclo " + cycle, role = KomiTextRole.Stamp, color = colors.onSurface)
            KomiText(Schedule.cycleRange(cycle), role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
        }
        KomiIconButton(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Ciclo siguiente", onNext)
    }
}

// =====================================================================
// COMIDA
// =====================================================================

@Composable
fun ComidaScreen() {
    val n = Profile.numbers()
    LazyColumn(contentPadding = screenPadding, verticalArrangement = Arrangement.spacedBy(30.dp)) {
        section {
            Kicker(if (n != null) "%.0f kg · IMC %.1f".format(n.weight, n.bmi) else "Completa tu perfil en Más")
            Display("Comida")
            Body(Content.FOOD_INTRO)
        }
        section {
            if (n != null) {
                Table(
                    "Tus números · Fase 0", listOf("Qué", "Cuánto", "De dónde sale"),
                    listOf(
                        listOf("IMC", "%.1f".format(n.bmi), "peso / estatura²"),
                        listOf("Gasto en reposo", "≈ ${n.bmr} kcal", "Mifflin-St Jeor"),
                        listOf("Mantenimiento", "≈ ${n.maintenance} kcal", "× 1.45 con 3 fuerza + 3 caminatas"),
                        listOf("Para construir", "${n.buildLow}–${n.buildHigh} kcal", "+200–300, ganancia magra sin engordar"),
                        listOf("Proteína", "${n.proteinLow} g · 4 × ${n.perMeal} g", "1.6 g/kg; hasta ${n.proteinHigh} g"),
                        listOf("Grasa saturada, tope", "< ${n.satFatCap} g", "10 % de las calorías (OMS)"),
                    ),
                    listOf(1.1f, 1f, 1.5f), mono = setOf(1, 2),
                )
            } else {
                Note(Tone.KEY, "Tus números", Content.PROFILE_HINT)
            }
            Body(Content.PROTEIN_SPLIT, muted = true)
            Table("Un día de ejemplo · ~110 g", listOf("Alimento", "Proteína"), Content.food, listOf(2.4f, 0.7f), mono = setOf(1), boldFirst = false, totalLast = true)
        }
        section {
            Note(Tone.CALM, "Huevos", *Content.eggsNote.toTypedArray())
            Note(Tone.CALM, "Leche", Content.MILK_NOTE)
            Note(Tone.CALM, "Frijoles", *Content.beansNote.toTypedArray())
            Note(Tone.PLAIN, "Consejo · estómago", Content.STOMACH_TIP)
            Evidence(Content.FOOD_EVIDENCE)
        }
        section {
            SectionHead("07", "Suplementos")
            Body(Content.SUPP_INTRO)
            Cues(Content.supplements)
            Note(Tone.CALM, "Creatina y agua", Content.CREATINE_WATER)
            Evidence(Content.SUPP_EVIDENCE)
        }
    }
}

// =====================================================================
// REGISTRO
// =====================================================================

private val REG_TABS = listOf("Semanas", "Bitácora", "Medidas", "Metas")

@Composable
fun RegistroScreen() {
    val colors = LocalPersonality.current.colors
    val today = remember { Schedule.today() }
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var cycle by rememberSaveable { mutableIntStateOf(Schedule.cycleOf(today)) }
    var week by rememberSaveable { mutableIntStateOf(Schedule.weekInCycleOf(today)) }
    val cycleWeeks = Schedule.cycleWeeks(cycle)
    val doneInCycle = cycleWeeks.flatten().count { Store.isDone(it.iso) }

    var waist by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }
    var entry by rememberSaveable { mutableStateOf("") }

    val run = streak(today)

    LazyColumn(contentPadding = screenPadding, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        section {
            Kicker("Ciclo " + cycle + " · " + doneInCycle + " / 28 hechos · racha " + run)
            Display("Registro")
            SubTabs(REG_TABS, tab) { tab = it }
        }

        when (tab) {
            0 -> {
                section {
                    CycleHeader(cycle, onPrev = { if (cycle > 1) cycle-- }, onNext = { cycle++ })
                    WeekBars(cycle, week, today) { week = it }
                    Body("Cada barra es una semana del ciclo: cuánto llevas de 7. Toca una para ver sus días abajo y marcar los que hiciste.", muted = true)
                }
                section {
                    Sub("Semana $week · marca lo hecho")
                    WeekList(cycleWeeks[week - 1], today, showCheck = true)
                    KomiText("El sábado ligero cuenta igual: la casilla es el hábito.", role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
                }
            }
            1 -> {
                section {
                    SectionHead(null, "Bitácora")
                    Body("Lo que quieras apuntar: cómo fue la sesión, qué pesaste, qué dolió, qué comiste, cualquier cosa. Se guarda con la fecha de hoy.", muted = true)
                    KomiTextField(value = entry, onValueChange = { entry = it }, placeholder = "Escribe aquí…", multiline = true, rows = 4, modifier = Modifier.fillMaxWidth())
                    KomiButton(
                        onClick = { if (entry.isNotBlank()) { Store.addEntry(Entry(today.toString(), entry.trim())); entry = "" } },
                        label = "Guardar en la bitácora", enabled = entry.isNotBlank(), fullWidth = true,
                    )
                }
                section {
                    if (Store.journal.isEmpty()) {
                        Body("Todavía no hay entradas.", muted = true)
                    } else {
                        Store.journal.forEach { e ->
                            KomiSurface(elevation = KomiSurfaceElevation.Flat, contentPadding = PaddingValues(start = 12.dp, top = 10.dp, bottom = 10.dp, end = 6.dp)) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        KomiText(e.date, role = KomiTextRole.Mono, color = colors.onSurfaceVariant, fontSize = 11.sp)
                                        KomiText(e.text, role = KomiTextRole.Body, color = colors.onSurface)
                                    }
                                    KomiIconButton(Icons.Filled.Delete, "Borrar", { Store.removeEntry(e) }, size = 34.dp)
                                }
                            }
                        }
                    }
                }
            }
            2 -> {
                section {
                    SectionHead(null, "Gripper")
                    Body("Los kilos a los que cierras 10–12 veces con dificultad. Cuando salgan 3×12 con las dos manos, sube 2.5–5.", muted = true)
                    KomiTextField(value = Store.gripKg, onValueChange = { Store.updateGripKg(it) }, label = "Resistencia actual (kg)", placeholder = "por ejemplo 15", keyboardType = KeyboardType.Number, modifier = Modifier.fillMaxWidth())
                }
                section {
                    SectionHead(null, "Cintura")
                    Body("A la altura del ombligo, el mismo día de cada mes. La de partida antes de empezar; la siguiente, el último sábado del ciclo.", muted = true)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        KomiTextField(value = waist, onValueChange = { waist = it }, label = "cm", keyboardType = KeyboardType.Decimal, modifier = Modifier.weight(0.7f))
                        KomiTextField(value = note, onValueChange = { note = it }, label = "Nota", placeholder = "opcional", modifier = Modifier.weight(1.3f))
                    }
                    KomiButton(
                        onClick = { if (waist.isNotBlank()) { Store.addMeasure(Measure(today.toString(), waist.trim(), note.trim())); waist = ""; note = "" } },
                        label = "Guardar medida de hoy", enabled = waist.isNotBlank(), fullWidth = true,
                    )
                    if (Store.measures.isEmpty()) {
                        Body("Todavía no hay medidas.", muted = true)
                    } else {
                        KomiSurface(elevation = KomiSurfaceElevation.Card, contentPadding = PaddingValues(0.dp)) {
                            Column {
                                Store.measures.forEachIndexed { i, m ->
                                    Row(Modifier.fillMaxWidth().padding(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        KomiText(m.date, Modifier.width(92.dp), role = KomiTextRole.Mono, color = colors.onSurfaceVariant, fontSize = 11.sp)
                                        KomiText(m.waistCm + " cm", Modifier.width(70.dp), role = KomiTextRole.Stamp, color = colors.onSurface, fontSize = 14.sp)
                                        KomiText(m.note, Modifier.weight(1f), role = KomiTextRole.Body, color = colors.onSurface, fontSize = 13.sp)
                                        KomiIconButton(Icons.Filled.Delete, "Borrar", { Store.removeMeasure(m) }, size = 34.dp)
                                    }
                                    if (i < Store.measures.lastIndex) KomiHorizontalDivider()
                                }
                            }
                        }
                    }
                }
                section {
                    SectionHead(null, "Cómo medir que funciona")
                    Body(Content.MEASURE_INTRO)
                    Cues(Content.measureCues)
                    Body(Content.MEASURE_OUTRO, muted = true)
                    Evidence(Content.MEASURE_EVIDENCE)
                }
            }
            else -> section {
                SectionHead(null, "Cuándo pasas a la fase 1")
                Body("Cuando cumplas las dos cosas, no antes y no por calendario:")
                GateRow(Content.gateItems[0], Store.gateFlex) { Store.updateGateFlex(it) }
                GateRow(Content.gateItems[1], Store.gateHang) { Store.updateGateHang(it) }
                if (Store.gateFlex && Store.gateHang) {
                    Row(Modifier.fillMaxWidth().padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                        Starburst("¡Listo!")
                        KomiText("Las dos cumplidas. Toca montar la rutina con barra completa.", Modifier.weight(1f), role = KomiTextRole.Body, color = colors.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
private fun Swatch(color: Color) {
    val colors = LocalPersonality.current.colors
    Box(Modifier.size(14.dp).background(color).border(1.5.dp, colors.outline))
}

@Composable
private fun GateRow(text: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    val colors = LocalPersonality.current.colors
    KomiSurface(
        elevation = KomiSurfaceElevation.Flat,
        onClick = { onChange(!checked) },
        borderColor = if (checked) colors.gold else Color.Unspecified,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KomiCheckbox(checked = checked, onCheckedChange = onChange)
            KomiText(text, role = KomiTextRole.Body, color = colors.onSurface)
        }
    }
}

// =====================================================================
// MÁS
// =====================================================================

private const val KOMI_URL = "https://github.com/kurikomi-labs/komi-store"

@Composable
fun MasScreen() {
    val colors = LocalPersonality.current.colors
    val uri = LocalUriHandler.current
    val today = remember { Schedule.today() }
    val n = Profile.numbers()

    LazyColumn(contentPadding = screenPadding, verticalArrangement = Arrangement.spacedBy(30.dp)) {
        section {
            Kicker("Perfil, principios y fuentes")
            Display("Qué hay detrás")
        }
        section {
            SectionHead(null, "Perfil")
            Body("De aquí salen los números de Comida y los kilos de la escalera. Solo se guarda en este teléfono.", muted = true)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KomiTextField(value = Store.weightKg, onValueChange = { Store.updateWeight(it) }, label = "Peso (kg)", keyboardType = KeyboardType.Decimal, modifier = Modifier.weight(1f))
                KomiTextField(value = Store.heightCm, onValueChange = { Store.updateHeight(it) }, label = "Estatura (cm)", keyboardType = KeyboardType.Number, modifier = Modifier.weight(1f))
                KomiTextField(value = Store.age, onValueChange = { Store.updateAge(it) }, label = "Edad", keyboardType = KeyboardType.Number, modifier = Modifier.weight(0.8f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KomiChip("Hombre", kind = KomiChipKind.Filter, selected = Store.sex == "M", index = 0, onClick = { Store.updateSex("M") })
                KomiChip("Mujer", kind = KomiChipKind.Filter, selected = Store.sex == "F", index = 1, onClick = { Store.updateSex("F") })
            }
            if (n != null) {
                KomiText("IMC %.1f · mantenimiento ≈ %d kcal · proteína %d g/día".format(n.bmi, n.maintenance, n.proteinLow), role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
            }
            Sub("Fecha de inicio")
            KomiText(Schedule.long(Schedule.start) + " · siempre un domingo", role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                KomiButton(onClick = { Store.updateStart(Schedule.previousSunday(today).toString()) }, label = "Domingo pasado", variant = KomiButtonVariant.Outline, size = KomiButtonSize.Sm)
                KomiButton(onClick = { Store.updateStart(Schedule.nextSunday(today).toString()) }, label = "Próximo domingo", variant = KomiButtonVariant.Outline, size = KomiButtonSize.Sm)
            }
        }
        section {
            SectionHead("01", "Para quién es")
            Content.forWhom.forEach { Body(it) }
            Evidence(Content.START_EVIDENCE)
        }
        section {
            SectionHead("00", "En seis frases")
            Cues(Content.principles, color = colors.gold)
        }
        section {
            SectionHead(null, "Tema")
            Body("Un personaje, una paleta. Claro y oscuro con el mismo carácter.", muted = true)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("system" to "Sistema", "light" to "Claro", "dark" to "Oscuro").forEachIndexed { i, (id, label) ->
                    KomiChip(label, kind = KomiChipKind.Filter, selected = Store.themeMode == id, index = i, onClick = { Store.updateThemeMode(id) })
                }
            }
            Themes.all.chunked(2).forEach { pair ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    pair.forEach { t ->
                        val sel = Store.themeId == t.id
                        val swatch = if (LocalPersonality.current.colors.isDark) t.dark else t.light
                        KomiSurface(
                            elevation = if (sel) KomiSurfaceElevation.Card else KomiSurfaceElevation.Flat,
                            onClick = { Store.updateTheme(t.id) },
                            contentPadding = PaddingValues(10.dp),
                            modifier = Modifier.weight(1f),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Swatch(Color(swatch.primary)); Swatch(Color(swatch.secondary)); Swatch(Color(swatch.gold))
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Swatch(Color(swatch.page)); Swatch(Color(swatch.ink)); Swatch(Color(swatch.error))
                                    }
                                }
                                KomiText((if (sel) "✓ " else "") + t.name, role = KomiTextRole.Stamp, color = if (sel) colors.primary else colors.onSurface, fontSize = 14.sp)
                            }
                        }
                    }
                    if (pair.size == 1) Spacer(Modifier.weight(1f))
                }
            }
            Body(Content.DISCLAIMER, muted = true)
        }
        section {
            SectionHead(null, "Créditos")
            Note(
                Tone.KEY, "Diseño de interfaz",
                "La interfaz de Patchy usa el sistema de diseño de **Komi Store** (kurikomi-labs), personalidad «Manga»: papel y tinta, bordes duros, sombras sin desenfoque, sellos inclinados, tramas y líneas de velocidad. Código adaptado bajo **Apache License 2.0**; los cambios están anotados en cada archivo de `ui/komi`.",
                "Paleta inspirada en Patchouli Knowledge (Touhou Project, Team Shanghai Alice): morado, rosa, dorado de la luna y carmesí de los listones. Tipografías: Anton, Noto Sans y JetBrains Mono, bajo SIL Open Font License.",
            )
            KomiButton(onClick = { uri.openUri(KOMI_URL) }, label = "Ver Komi Store en GitHub", variant = KomiButtonVariant.Outline, fullWidth = true)
        }
        section {
            SectionHead(null, "Fuentes")
            KomiSurface(elevation = KomiSurfaceElevation.Card, contentPadding = PaddingValues(0.dp)) {
                Column {
                    Content.sources.forEachIndexed { i, s ->
                        Row(
                            Modifier.fillMaxWidth().clickable { uri.openUri(s.url) }.padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.Top,
                        ) {
                            KomiText((i + 1).toString().padStart(2, '0'), Modifier.width(32.dp), role = KomiTextRole.Stamp, color = colors.onSurfaceVariant, fontSize = 12.sp)
                            KomiText(AnnotatedString(s.name, SpanStyle(textDecoration = TextDecoration.Underline)), role = KomiTextRole.Body, color = colors.onSurface, fontSize = 13.5.sp)
                        }
                        if (i < Content.sources.lastIndex) KomiHorizontalDivider()
                    }
                }
            }
            KomiText("Patchy 1.1 · plan Fase 0", role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
        }
    }
}

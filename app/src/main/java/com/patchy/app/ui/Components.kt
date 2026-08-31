package com.patchy.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patchy.app.data.DayPlan
import com.patchy.app.data.Kind
import com.patchy.app.data.LadderStep
import com.patchy.app.data.Profile
import com.patchy.app.data.Schedule
import com.patchy.app.data.Store
import com.patchy.app.ui.komi.KomiCheckbox
import com.patchy.app.ui.komi.KomiChip
import com.patchy.app.ui.komi.KomiChipKind
import com.patchy.app.ui.komi.KomiHeadline
import com.patchy.app.ui.komi.KomiHorizontalDivider
import com.patchy.app.ui.komi.KomiScreentone
import com.patchy.app.ui.komi.KomiSurface
import com.patchy.app.ui.komi.KomiSurfaceElevation
import com.patchy.app.ui.komi.KomiSurfacePaper
import com.patchy.app.ui.komi.KomiText
import com.patchy.app.ui.komi.KomiTextRole
import com.patchy.app.ui.komi.LocalPersonality
import com.patchy.app.ui.komi.LocalStatusColors
import com.patchy.app.ui.komi.StarburstShape
import com.patchy.app.ui.komi.hardShadow
import java.time.LocalDate

/** Convierte **negritas** en spans; el resto del texto va tal cual. */
fun rich(text: String): AnnotatedString = buildAnnotatedString {
    text.split("**").forEachIndexed { i, part ->
        if (i % 2 == 1) withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(part) } else append(part)
    }
}

enum class Tone { PLAIN, WARN, CALM, KEY }

@Composable
fun toneColor(tone: Tone): Color {
    val colors = LocalPersonality.current.colors
    val status = LocalStatusColors.current
    return when (tone) {
        Tone.PLAIN -> colors.onSurfaceVariant
        Tone.WARN -> status.error
        Tone.CALM -> colors.gold
        Tone.KEY -> colors.primary
    }
}

// ---------------------------------------------------------------- texto

/** Sello inclinado con el acento: la etiqueta pequeña que abre una pantalla. */
@Composable
fun Kicker(text: String) {
    KomiChip(label = text, kind = KomiChipKind.Filter, small = true, selected = true, tilt = true)
}

@Composable
fun Display(text: String) {
    KomiText(text, role = KomiTextRole.Display)
}

@Composable
fun SectionHead(number: String?, text: String) {
    val colors = LocalPersonality.current.colors
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        if (!number.isNullOrBlank()) KomiText("No. $number", role = KomiTextRole.Stamp, color = colors.onSurfaceVariant, fontSize = 12.sp)
        KomiHeadline(text)
    }
}

@Composable
fun Sub(text: String) {
    KomiText(text, role = KomiTextRole.Stamp, color = LocalPersonality.current.colors.onSurfaceVariant)
}

@Composable
fun Body(text: String, muted: Boolean = false) {
    val colors = LocalPersonality.current.colors
    KomiText(rich(text), role = KomiTextRole.Body, color = if (muted) colors.onSurfaceVariant else colors.onSurface)
}

@Composable
fun ToneStamp(label: String, tone: Tone) {
    KomiChip(label = label, kind = KomiChipKind.Filter, small = true, tilt = false, fill = toneColor(tone))
}

/** Panel con barra de tono a la izquierda: avisos, correcciones, reglas clave. */
@Composable
fun Note(tone: Tone, label: String, vararg paragraphs: String) {
    val colors = LocalPersonality.current.colors
    val bar = toneColor(tone)
    KomiSurface(elevation = KomiSurfaceElevation.Flat, contentPadding = PaddingValues(0.dp)) {
        Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            Box(Modifier.width(7.dp).fillMaxHeight().background(bar))
            Column(
                Modifier.fillMaxWidth().background(bar.copy(alpha = if (tone == Tone.PLAIN) 0f else 0.07f)).padding(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                ToneStamp(label, tone)
                paragraphs.forEach { KomiText(rich(it), role = KomiTextRole.Body, color = colors.onSurface) }
            }
        }
    }
}

@Composable
fun Evidence(text: String) {
    val colors = LocalPersonality.current.colors
    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Box(Modifier.width(2.5.dp).fillMaxHeight().background(colors.outline.copy(alpha = 0.4f)))
        Column(Modifier.padding(start = 12.dp, top = 2.dp, bottom = 2.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            KomiText("Evidencia", role = KomiTextRole.Stamp, color = colors.onSurfaceVariant, fontSize = 12.sp)
            KomiText(text, role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
        }
    }
}

@Composable
fun Cues(items: List<String>, color: Color = Color.Unspecified) {
    val colors = LocalPersonality.current.colors
    val fill = if (color != Color.Unspecified) color else colors.primary
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.forEach {
            Row {
                Box(Modifier.padding(top = 7.dp).size(9.dp).background(fill).border(2.dp, colors.outline))
                Spacer(Modifier.width(12.dp))
                KomiText(rich(it), role = KomiTextRole.Body, color = colors.onSurface)
            }
        }
    }
}

/** Tabla: cabecera en placa de tinta, columnas con pesos, las de [mono] en JetBrains Mono. */
@Composable
fun Table(
    caption: String,
    headers: List<String>,
    rows: List<List<String>>,
    weights: List<Float>,
    mono: Set<Int> = emptySet(),
    boldFirst: Boolean = true,
    totalLast: Boolean = false,
) {
    val colors = LocalPersonality.current.colors
    KomiSurface(elevation = KomiSurfaceElevation.Card, contentPadding = PaddingValues(0.dp)) {
        Column {
            Box(Modifier.fillMaxWidth().background(colors.onSurface).padding(horizontal = 12.dp, vertical = 9.dp)) {
                KomiText(caption, role = KomiTextRole.Stamp, color = colors.surface)
            }
            if (headers.isNotEmpty()) {
                Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp)) {
                    headers.forEachIndexed { i, h ->
                        KomiText(h, Modifier.weight(weights[i]), role = KomiTextRole.Label, color = colors.onSurfaceVariant, fontSize = 10.5.sp)
                    }
                }
                KomiHorizontalDivider(color = colors.outline)
            }
            rows.forEachIndexed { ri, row ->
                val isTotal = totalLast && ri == rows.lastIndex
                if (isTotal) KomiHorizontalDivider(color = colors.outline)
                Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 9.dp), verticalAlignment = Alignment.Top) {
                    row.forEachIndexed { ci, cell ->
                        val isMono = ci in mono
                        val bold = (boldFirst && ci == 0) || isTotal
                        KomiText(
                            rich(cell),
                            Modifier.weight(weights[ci]).padding(end = if (ci < row.lastIndex) 8.dp else 0.dp),
                            role = if (isMono) KomiTextRole.Mono else KomiTextRole.Body,
                            color = if (isMono && !bold) colors.onSurfaceVariant else colors.onSurface,
                            fontWeight = if (bold) FontWeight.Bold else null,
                            fontSize = if (isMono) 12.sp else 14.sp,
                        )
                    }
                }
                if (ri < rows.lastIndex && !(totalLast && ri == rows.lastIndex - 1)) KomiHorizontalDivider()
            }
        }
    }
}

@Composable
fun RuleBox(lines: List<Pair<String, String>>) {
    val colors = LocalPersonality.current.colors
    KomiSurface(elevation = KomiSurfaceElevation.Flat, screentone = KomiScreentone.Corner, contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            lines.forEach { (k, v) ->
                Row(verticalAlignment = Alignment.Top) {
                    KomiText(k, Modifier.width(96.dp), role = KomiTextRole.Stamp, color = colors.onSurface, fontSize = 13.sp)
                    KomiText(v, Modifier.weight(1f), role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun Ladder(steps: List<LadderStep>, altIndex: Int = -1) {
    val colors = LocalPersonality.current.colors
    KomiSurface(elevation = KomiSurfaceElevation.Card, contentPadding = PaddingValues(0.dp)) {
        Column {
            steps.forEachIndexed { i, s ->
                val hint = (if (s.measured) "" else "≈ ") + s.percent + " % · " + Profile.kgFor(s.percent)
                Row(
                    Modifier.fillMaxWidth().background(if (i == altIndex) colors.surfaceVariant else Color.Transparent).padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    KomiText(s.step, Modifier.width(34.dp), role = KomiTextRole.Stamp, color = colors.onSurface, fontSize = 13.sp)
                    KomiText(s.name, Modifier.weight(1f), role = KomiTextRole.Body, color = colors.onSurface, fontSize = 14.sp)
                    KomiText(hint, role = KomiTextRole.Mono, color = colors.onSurfaceVariant, fontSize = 11.sp)
                }
                if (i < steps.lastIndex) KomiHorizontalDivider()
            }
        }
    }
}

@Composable
fun GateBox(title: String, intro: String, items: List<String>, outro: String) {
    val colors = LocalPersonality.current.colors
    KomiSurface(
        elevation = KomiSurfaceElevation.Raised,
        paper = KomiSurfacePaper.Background,
        screentone = KomiScreentone.Corner,
        contentPadding = PaddingValues(18.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            KomiText(title, role = KomiTextRole.Title, color = colors.onBackground)
            KomiText(intro, role = KomiTextRole.Body, color = colors.onBackground)
            Cues(items, color = colors.onBackground)
            KomiText(outro, role = KomiTextRole.Mono, color = colors.onSurfaceVariant)
        }
    }
}

/** Estrella de manga con texto en el centro, para el momento en que se cumple una meta. */
@Composable
fun Starburst(text: String, size: Int = 112) {
    val colors = LocalPersonality.current.colors
    val shape = StarburstShape(spikes = 14, innerRatio = 0.7f)
    Box(
        Modifier
            .rotate(-8f)
            .size(size.dp)
            .hardShadow(DpOffset(4.dp, 4.dp), colors.shadow, shape)
            .background(colors.primary, shape)
            .border(3.dp, colors.outline, shape),
        contentAlignment = Alignment.Center,
    ) {
        KomiText(text, role = KomiTextRole.Title, color = colors.onPrimary, textAlign = TextAlign.Center, fontSize = 18.sp, lineHeight = 18.sp)
    }
}

// ---------------------------------------------------------------- navegación interna

/** Fila de sellos para cambiar de subsección dentro de una pantalla. */
@Composable
fun SubTabs(items: List<String>, selected: Int, onSelect: (Int) -> Unit) {
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEachIndexed { i, label ->
            KomiChip(label = label, kind = KomiChipKind.Filter, selected = i == selected, index = i, onClick = { onSelect(i) })
        }
    }
}

// ---------------------------------------------------------------- calendario

@Composable
fun kindColor(d: DayPlan): Color {
    val colors = LocalPersonality.current.colors
    return when (d.kind) {
        Kind.STRENGTH -> if (d.letter == 'A') colors.primary else colors.secondary
        Kind.CARDIO -> colors.gold
        Kind.LIGHT -> colors.surfaceVariant
        Kind.PRESTART -> colors.outlineVariant
    }
}

@Composable
fun kindInk(d: DayPlan): Color {
    val colors = LocalPersonality.current.colors
    return when (d.kind) {
        Kind.STRENGTH -> if (d.letter == 'A') colors.onPrimary else colors.onSecondary
        Kind.CARDIO -> colors.onGold
        Kind.LIGHT -> colors.onSurfaceVariant
        Kind.PRESTART -> colors.background
    }
}

/** Un día como fila legible: color del tipo, fecha, título, detalle y casilla. */
@Composable
fun DayRow(d: DayPlan, isToday: Boolean, done: Boolean, onToggle: () -> Unit, showCheck: Boolean = true) {
    val colors = LocalPersonality.current.colors
    val tags = buildList {
        if (d.grippers) add("+ grippers")
        add(d.detail)
    }.joinToString(" · ")
    KomiSurface(
        elevation = if (isToday) KomiSurfaceElevation.Card else KomiSurfaceElevation.Flat,
        borderColor = if (isToday) colors.outline else colors.outline.copy(alpha = 0.45f),
        onClick = if (showCheck) onToggle else null,
        contentPadding = PaddingValues(0.dp),
    ) {
        Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(10.dp).fillMaxHeight().background(kindColor(d)))
            Column(Modifier.width(60.dp).padding(start = 10.dp, top = 10.dp, bottom = 10.dp)) {
                KomiText(Schedule.dow(d.date), role = KomiTextRole.Stamp, color = if (isToday) colors.primary else colors.onSurface, fontSize = 14.sp)
                KomiText(d.date.dayOfMonth.toString(), role = KomiTextRole.Mono, color = colors.onSurfaceVariant, fontSize = 12.sp)
            }
            Column(Modifier.weight(1f).padding(vertical = 10.dp, horizontal = 6.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                KomiText(d.title, role = KomiTextRole.Body, color = colors.onSurface, fontWeight = FontWeight.Bold, fontSize = 14.5.sp)
                KomiText(tags, role = KomiTextRole.Mono, color = colors.onSurfaceVariant, fontSize = 10.5.sp)
            }
            if (showCheck) {
                KomiCheckbox(checked = done, onCheckedChange = { onToggle() }, modifier = Modifier.padding(end = 14.dp))
            } else if (done) {
                Icon(Icons.Filled.Check, contentDescription = "Hecho", modifier = Modifier.padding(end = 14.dp).size(18.dp), tint = colors.onSurface)
            }
        }
    }
}

@Composable
fun WeekList(week: List<DayPlan>, today: LocalDate, showCheck: Boolean = true) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        week.forEach { d ->
            DayRow(d, isToday = d.date == today, done = Store.isDone(d.iso), onToggle = { Store.toggle(d.iso) }, showCheck = showCheck)
        }
    }
}

/** Racha: días seguidos marcados, contando desde hoy o desde ayer. */
fun streak(today: LocalDate): Int {
    var d = if (Store.isDone(today.toString())) today else today.minusDays(1)
    var n = 0
    while (Store.isDone(d.toString())) {
        n++
        d = d.minusDays(1)
    }
    return n
}

/**
 * Gráfico del ciclo: una barra por semana con los días hechos sobre 7.
 * Tocar una barra elige esa semana para la lista de abajo.
 */
@Composable
fun WeekBars(cycle: Int, selected: Int, today: LocalDate, onSelect: (Int) -> Unit) {
    val colors = LocalPersonality.current.colors
    val weeks = Schedule.cycleWeeks(cycle)
    KomiSurface(elevation = KomiSurfaceElevation.Card, contentPadding = PaddingValues(0.dp)) {
        Column {
            Box(Modifier.fillMaxWidth().background(colors.onSurface).padding(horizontal = 12.dp, vertical = 9.dp)) {
                KomiText("Ciclo $cycle · días hechos por semana", role = KomiTextRole.Stamp, color = colors.surface)
            }
            weeks.forEachIndexed { i, week ->
                val w = i + 1
                val done = week.count { Store.isDone(it.iso) }
                val isSel = w == selected
                val isCurrent = week.any { it.date == today }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(if (isSel) colors.surfaceVariant else Color.Transparent)
                        .clickable { onSelect(w) }
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Column(Modifier.width(72.dp)) {
                        KomiText("Sem $w", role = KomiTextRole.Stamp, color = if (isSel) colors.primary else colors.onSurface, fontSize = 14.sp)
                        KomiText(Schedule.short(week[0].date) + if (isCurrent) " · hoy" else "", role = KomiTextRole.Mono, color = colors.onSurfaceVariant, fontSize = 10.sp)
                    }
                    Box(
                        Modifier
                            .weight(1f)
                            .height(22.dp)
                            .background(colors.background)
                            .border(if (isSel) 3.dp else 2.dp, colors.outline),
                    ) {
                        if (done > 0) {
                            Box(Modifier.fillMaxHeight().fillMaxWidth(done / 7f).padding(3.dp).background(colors.primary))
                        }
                    }
                    KomiText("$done / 7", Modifier.width(44.dp), role = KomiTextRole.Mono, color = colors.onSurface, fontSize = 12.sp, textAlign = TextAlign.End)
                }
                if (i < weeks.lastIndex) KomiHorizontalDivider()
            }
        }
    }
}

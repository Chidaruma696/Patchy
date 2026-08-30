package com.patchy.app.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Measure(val date: String, val waistCm: String, val note: String)
data class Entry(val date: String, val text: String)

/** Persistencia local mínima: SharedPreferences detrás de estado observable por Compose. */
object Store {
    private lateinit var prefs: SharedPreferences

    private const val RS = ""   // separador de registros
    private const val FS = ""   // separador de campos

    val done = mutableStateListOf<String>()
    val measures = mutableStateListOf<Measure>()
    val journal = mutableStateListOf<Entry>()

    var gateFlex by mutableStateOf(false); private set
    var gateHang by mutableStateOf(false); private set
    var gripKg by mutableStateOf(""); private set

    var weightKg by mutableStateOf(""); private set
    var heightCm by mutableStateOf(""); private set
    var age by mutableStateOf(""); private set
    var sex by mutableStateOf("M"); private set
    var startIso by mutableStateOf(""); private set

    var themeId by mutableStateOf("patchouli"); private set
    var themeMode by mutableStateOf("system"); private set

    fun init(ctx: Context) {
        if (::prefs.isInitialized) return
        prefs = ctx.getSharedPreferences("patchy", Context.MODE_PRIVATE)
        done.clear()
        done.addAll(prefs.getStringSet("done", emptySet()) ?: emptySet())
        measures.clear()
        (prefs.getString("measures", "") ?: "").split("\n").filter { it.isNotBlank() }.forEach { line ->
            val p = line.split("|")
            if (p.size >= 3) measures.add(Measure(p[0], p[1], p.drop(2).joinToString("|")))
        }
        journal.clear()
        (prefs.getString("journal", "") ?: "").split(RS).filter { it.isNotBlank() }.forEach { rec ->
            val p = rec.split(FS)
            if (p.size >= 2) journal.add(Entry(p[0], p.drop(1).joinToString(FS)))
        }
        gateFlex = prefs.getBoolean("gateFlex", false)
        gateHang = prefs.getBoolean("gateHang", false)
        gripKg = prefs.getString("gripKg", "") ?: ""
        weightKg = prefs.getString("weightKg", "") ?: ""
        heightCm = prefs.getString("heightCm", "") ?: ""
        age = prefs.getString("age", "") ?: ""
        sex = prefs.getString("sex", "M") ?: "M"
        startIso = prefs.getString("startIso", "") ?: ""
        themeId = prefs.getString("themeId", "patchouli") ?: "patchouli"
        themeMode = prefs.getString("themeMode", "system") ?: "system"
    }

    fun updateTheme(id: String) { themeId = id; prefs.edit().putString("themeId", id).apply() }
    fun updateThemeMode(mode: String) { themeMode = mode; prefs.edit().putString("themeMode", mode).apply() }

    fun isDone(iso: String): Boolean = done.contains(iso)

    fun toggle(iso: String) {
        if (done.contains(iso)) done.remove(iso) else done.add(iso)
        prefs.edit().putStringSet("done", done.toSet()).apply()
    }

    fun updateGateFlex(v: Boolean) { gateFlex = v; prefs.edit().putBoolean("gateFlex", v).apply() }
    fun updateGateHang(v: Boolean) { gateHang = v; prefs.edit().putBoolean("gateHang", v).apply() }
    fun updateGripKg(v: String) { gripKg = v; prefs.edit().putString("gripKg", v).apply() }

    fun updateWeight(v: String) { weightKg = v; prefs.edit().putString("weightKg", v).apply() }
    fun updateHeight(v: String) { heightCm = v; prefs.edit().putString("heightCm", v).apply() }
    fun updateAge(v: String) { age = v; prefs.edit().putString("age", v).apply() }
    fun updateSex(v: String) { sex = v; prefs.edit().putString("sex", v).apply() }
    fun updateStart(iso: String) { startIso = iso; prefs.edit().putString("startIso", iso).apply() }

    fun addMeasure(m: Measure) { measures.add(0, m); saveMeasures() }
    fun removeMeasure(m: Measure) { measures.remove(m); saveMeasures() }

    private fun saveMeasures() {
        prefs.edit().putString(
            "measures",
            measures.joinToString("\n") { it.date + "|" + it.waistCm + "|" + it.note.replace("\n", " ") },
        ).apply()
    }

    fun addEntry(e: Entry) { journal.add(0, e); saveJournal() }
    fun removeEntry(e: Entry) { journal.remove(e); saveJournal() }

    private fun saveJournal() {
        prefs.edit().putString("journal", journal.joinToString(RS) { it.date + FS + it.text }).apply()
    }
}

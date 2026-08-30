package com.patchy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.patchy.app.data.Store
import com.patchy.app.ui.ComidaScreen
import com.patchy.app.ui.HoyScreen
import com.patchy.app.ui.MasScreen
import com.patchy.app.ui.PlanScreen
import com.patchy.app.ui.RegistroScreen
import com.patchy.app.ui.komi.KomiBottomBar
import com.patchy.app.ui.komi.KomiNavItem
import com.patchy.app.ui.komi.KomiScaffold
import com.patchy.app.ui.komi.KomiTopBar
import com.patchy.app.ui.komi.PatchyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Store.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            PatchyTheme { PatchyApp() }
        }
    }
}

private val NAV = listOf(
    KomiNavItem("hoy", "Hoy", Icons.Filled.Home),
    KomiNavItem("plan", "Plan", Icons.Filled.DateRange),
    KomiNavItem("comida", "Comida", Icons.AutoMirrored.Filled.List),
    KomiNavItem("registro", "Registro", Icons.Filled.CheckCircle),
    KomiNavItem("mas", "Más", Icons.Filled.Info),
)

@Composable
fun PatchyApp() {
    var tab by rememberSaveable { mutableStateOf("hoy") }
    val subtitle = when (tab) {
        "hoy" -> "今日 · HOY"
        "plan" -> "計画 · PLAN · FASE 0"
        "comida" -> "食事 · COMIDA"
        "registro" -> "記録 · REGISTRO"
        else -> "情報 · QUÉ HAY DETRÁS"
    }
    KomiScaffold(
        topBar = { KomiTopBar(title = "Patchy", titleAccent = "chy", subtitle = subtitle) },
        bottomBar = { KomiBottomBar(items = NAV, selectedId = tab, onSelect = { tab = it }) },
    ) { pad ->
        Box(Modifier.fillMaxSize().padding(pad)) {
            when (tab) {
                "hoy" -> HoyScreen()
                "plan" -> PlanScreen()
                "comida" -> ComidaScreen()
                "registro" -> RegistroScreen()
                else -> MasScreen()
            }
        }
    }
}

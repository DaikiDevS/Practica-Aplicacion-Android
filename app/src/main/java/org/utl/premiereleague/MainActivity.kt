package org.utl.premiereleague

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
//import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.utl.premiereleague.ui.theme.PremiereLeagueTheme
import org.utl.premiereleague.viewmodel.TablaPosicionesViewModel
import org.utl.premiereleague.viewmodel.GoleadoresViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PremiereLeagueTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "noticias",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("noticias") { Noticias_Screen(navController) }
            composable("tabla_posiciones") { TablaPosiciones_Screen(navController) }
            composable("estadisticas") { TablaGoleadores_Screen(navController) }
        }
    }
}

/* ---------- Bottom Navigation Bar ---------- */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("noticias", "Noticias", Icons.Default.Home),
        BottomNavItem("tabla_posiciones", "Posiciones", Icons.Default.List),
        BottomNavItem("estadisticas", "Estadísticas", Icons.Default.Info)
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) }
            )
        }
    }
}

/* ---------- Data class para items del BottomNav ---------- */
data class BottomNavItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

/* ---------- Pantallas ---------- */
@Composable
fun Noticias_Screen(navController: NavHostController) {
    // especificamos el tipo para evitar el error de inferencia
    val noticias: List<Triple<Int, String, String>> = listOf(
        Triple(R.drawable.noticia1, "Yin Yang", "La nueva dupla del Brentford."),
        Triple(R.drawable.noticia2, "El faraón lo hizo de nuevo", "El derbi terminó con una remontada histórica de Salah."),
        Triple(R.drawable.noticia3, "Rice lesionado", "3 semanas sin arroz."),
        Triple(R.drawable.noticia4, "Hace frío en Londres", "Ha vuelto el capitán frio, Cole Palmer.")
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {

            item {
                Text(
                    text = "Noticias de la Premier League",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            items(noticias) { (imagen, titulo, descripcion) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Image(
                            painter = painterResource(id = imagen),
                            contentDescription = titulo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )

                        Text(
                            text = titulo,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TablaPosiciones_Screen(navController: NavHostController, vm: TablaPosicionesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val equipos = vm.lista

    val nombre = remember { mutableStateOf("") }
    val puntos = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Tabla de Posiciones", style = MaterialTheme.typography.titleLarge)

        // Formulario
        OutlinedTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre equipo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = puntos.value,
            onValueChange = { puntos.value = it },
            label = { Text("Puntos") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nombre.value.isNotBlank() && puntos.value.isNotBlank()) {
                    vm.agregar(nombre.value, puntos.value.toInt())
                    nombre.value = ""
                    puntos.value = ""
                }
            },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text("Agregar equipo")
        }

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(equipos) { equipo ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(equipo.nombre)
                        Text(equipo.puntos.toString())

                        Row {
                            Button(onClick = { vm.eliminar(equipo) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TablaGoleadores_Screen(navController: NavHostController, vm: GoleadoresViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val goleadores = vm.lista

    val jugador = remember { mutableStateOf("") }
    val goles = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Goleadores", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = jugador.value,
            onValueChange = { jugador.value = it },
            label = { Text("Jugador") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = goles.value,
            onValueChange = { goles.value = it },
            label = { Text("Goles") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (jugador.value.isNotBlank() && goles.value.isNotBlank()) {
                    vm.agregar(jugador.value, goles.value.toInt())
                    jugador.value = ""
                    goles.value = ""
                }
            },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text("Agregar goleador")
        }

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(goleadores) { gol ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(gol.jugador)
                        Text(gol.goles.toString())

                        Row {
                            Button(onClick = { vm.eliminar(gol) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    PremiereLeagueTheme {
        MyApp()
    }
}
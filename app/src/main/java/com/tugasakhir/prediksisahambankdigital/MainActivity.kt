package com.tugasakhir.prediksisahambankdigital

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tugasakhir.prediksisahambankdigital.ui.presentation.*
import com.tugasakhir.prediksisahambankdigital.ui.theme.LightGrey1
import com.tugasakhir.prediksisahambankdigital.ui.theme.PrediksiSahamBankDigitalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrediksiSahamBankDigitalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var isShowingSplashScreen by rememberSaveable { mutableStateOf(true) }

                    if (isShowingSplashScreen) {
                        SplashScreen {
                            isShowingSplashScreen = false
                        }
                    } else {
                        App()
                    }
                }
            }
        }
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == ScreenRoute.Beranda.route || currentRoute == ScreenRoute.Informasi.route) {
                BottomNavigationBar(navController = navController)
            }
        },
        content = { padding ->
            // NavHost: where screens are placed
            NavHostContainer(navController = navController, padding = padding)
        }
    )
}

@Composable
fun NavHostContainer(
    navController: NavHostController = rememberNavController(),
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Beranda.route,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            // Beranda
            composable(ScreenRoute.Beranda.route) {
                BerandaScreen(Modifier, navigateToDetailPerbandinganPrediksi = { kodeSaham ->
                    navController.navigate(
                        ScreenRoute.DetailPerbandinganPrediksi.createRoute(
                            kodeSaham
                        )
                    )
                })
            }

            // Informasi
            composable(ScreenRoute.Informasi.route) {
                InformasiScreen(Modifier, navController)
            }

            // Detail Perbandingan Prediksi
            composable(ScreenRoute.DetailPerbandinganPrediksi.route) {
                val kodeSaham = it.arguments?.getString("kodeSaham") ?: ""

                DetailPerbandinganPrediksiScreen(
                    kodeSaham = kodeSaham,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            // Panduan Penggunaan
            composable(ScreenRoute.PanduanPenggunaan.route) {
                PanduanPenggunaanScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }

            // Tentang Aplikasi
            composable(route = ScreenRoute.TentangAplikasi.route) {
                TentangAplikasiScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navController
                )
            }

            // Tentang Pengembang
            composable(route = ScreenRoute.TentangPengembang.route) {
                TentangPengembangScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navController
                )
            }

            // Kontak
            composable(ScreenRoute.Kontak.route) {
                KontakScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        })
}

@Composable
private fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    // Bottom bar
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier.height(75.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // Observe current route to change the icon color and label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavigationItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        modifier = Modifier.size(40.dp),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        fontWeight = if (currentRoute == navItem.route) FontWeight.Bold else FontWeight.Normal,
                        letterSpacing = 0.sp
                    )
                },
                alwaysShowLabel = true,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = LightGrey1
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrediksiSahamBankDigitalTheme {
        Greeting("Android")
    }
}
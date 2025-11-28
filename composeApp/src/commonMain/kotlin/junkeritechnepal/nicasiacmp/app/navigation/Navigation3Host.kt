package junkeritechnepal.nicasiacmp.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route

@Serializable
private sealed interface TopLevelRoute: Route {
    val icon: ImageVector
    val contentDescription: String
}

@Serializable
private data object PlayerList : TopLevelRoute {
    override val icon = Icons.Default.Person
    override val contentDescription = "Players"
}

@Serializable
private data class PlayerDetails(val playerId: Int) : Route

@Serializable
private data object FixtureList : TopLevelRoute {
    override val icon = Icons.Filled.DateRange
    override val contentDescription = "Fixtures"
}

@Serializable
private data object League : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Filled.List
    override val contentDescription = "Leagues"
}

@Serializable
private data object Settings : Route

private val topLevelRoutes: List<TopLevelRoute> = listOf(PlayerList, FixtureList, League)


@Composable
fun Navigation3Host() {
    val backStack: MutableList<Route> =
        rememberSerializable(serializer = SnapshotStateListSerializer()) {
            mutableStateListOf(PlayerList)
        }

    Scaffold(
        modifier = Modifier.padding(top = 32.dp),
        bottomBar = { topLevelRoutes.FantasyPremierLeagueBottomNavigation(backStack) }
    ){
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<PlayerList> {
                    LoginScreen(
                        onLoginSuccess = {
                            backStack.add(Dashboard)
                        }
                    )
                }

                entry<Dashboard> {
                    DashboardScreen()
                }
            }
        )
    }
}

@Composable
private fun List<TopLevelRoute>.FantasyPremierLeagueBottomNavigation(
    backStack: MutableList<Route>
) {
    var selectedType by remember { mutableStateOf<TopLevelRoute>(PlayerList) }
    NavigationBar {
        this@FantasyPremierLeagueBottomNavigation.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = topLevelRoute.icon,
                        contentDescription = topLevelRoute.contentDescription
                    )
                },
                selected = topLevelRoute == selectedType,
                onClick = {
                    selectedType = topLevelRoute
                    backStack.add(topLevelRoute)
                }
            )
        }
    }
}

@Serializable
private data object Dashboard : Route

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    androidx.compose.material3.Button(onClick = onLoginSuccess) {
        androidx.compose.material3.Text("Login")
    }
}

@Composable
fun DashboardScreen() {
    androidx.compose.material3.Text("Welcome to Dashboard!")
}
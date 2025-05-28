import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.app.navigation.LocalNavController
import junkeritechnepal.nicasiacmp.app.navigation.NavigationRoutes
import junkeritechnepal.nicasiacmp.modules.login.LoginViewModel
import kotlinx.coroutines.launch
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.nicasisa
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val loginViewModel by lazy { LoginViewModel() }

    Scaffold(
        topBar = {
            LoginNavHeaderView(scrollBehavior)
        }
    ) { padding ->
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {

                Text("Welcome", style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                LoginPasswordView(loginViewModel)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginNavHeaderView(scrollBehavior: TopAppBarScrollBehavior) {
    val navController = LocalNavController.current

    Column {
        TopAppBar(
            title = {
                // Center image using Box
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.nicasisa),
                        contentDescription = "App Logo",
                        modifier = Modifier.height(80.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(Icons.Outlined.Home, contentDescription = "Home")
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate(NavigationRoutes.DESIGN_SYSTEM.name) }) {
                    Icon(Icons.Outlined.Settings, contentDescription = "Language")
                }

                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Search, contentDescription = "Sms")
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.White),
            scrollBehavior = scrollBehavior
        )

        // Bottom divider line
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.padding(vertical = 8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginPasswordView(loginViewModel: LoginViewModel) {
    var password by remember { mutableStateOf("") }
    val showCountrySheet = loginViewModel.countrySheetState.isVisible
    val countrySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TextField(
        value = password,
        onValueChange = { password = it },
        placeholder = { Text("Password") },
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = {
                loginViewModel.fetchCountrySheet()

            }) {
                Icon(Icons.Default.Lock, contentDescription = "Password Icon")
            }
        },
        modifier = Modifier.fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    if(showCountrySheet) {
        ModalBottomSheet(
            shape = RoundedCornerShape(14.dp),
            onDismissRequest = {  },
            sheetState = countrySheetState
        ) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Choose a country", fontSize = 16.sp, fontStyle = FontStyle.Normal)
                    Image(imageVector = Icons.Outlined.Close, "")
                }
                loginViewModel.countrySheetState.data.forEach {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Image(imageVector = Icons.Outlined.Close, "")
                        Text("${it.name} ${it.emoji}")
                    }
                }
            }
        }
    }
}
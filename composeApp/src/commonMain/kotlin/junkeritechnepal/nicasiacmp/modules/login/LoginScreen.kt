import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.modules.designSystem.SampleTextView
import junkeritechnepal.nicasiacmp.modules.login.LoginViewModel
import nicasia_cmp.composeapp.generated.resources.Res
import nicasia_cmp.composeapp.generated.resources.compose_multiplatform
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
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Home, contentDescription = "Home")
                }
            },
            actions = {
                IconButton(onClick = { }) {
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
    var showCountrySheet = loginViewModel.showCountrySheet
    val countrySheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    TextField(
        value = password,
        onValueChange = { password = it },
        placeholder = { Text("Password") },
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = { loginViewModel.fetchCountrySheet() }) {
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

    SampleTextView()

    if(showCountrySheet) {
        ModalBottomSheet(
            shape = RoundedCornerShape(14.dp),
            onDismissRequest = { loginViewModel.showCountrySheet = false },
            sheetState = countrySheetState
        ) {
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Choose a country", fontSize = 16.sp, fontStyle = FontStyle.Normal)
                    Image(imageVector = Icons.Outlined.Close, "")
                }
            }
        }
    }
}
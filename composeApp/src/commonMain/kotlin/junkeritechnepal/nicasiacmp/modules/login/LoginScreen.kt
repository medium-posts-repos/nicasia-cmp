import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import junkeritechnepal.nicasiacmp.app.navigation.LocalNavController
import junkeritechnepal.nicasiacmp.app.navigation.NavigationRoutes
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTextStyle
import junkeritechnepal.nicasiacmp.modules.designSystem.AppTypography
import junkeritechnepal.nicasiacmp.modules.login.LoginCountryResDto
import junkeritechnepal.nicasiacmp.modules.login.LoginViewModel
import junkeritechnepal.nicasiacmp.modules.login.LoginViewModelExt
import junkeritechnepal.nicasiacmp.modules.login.LoginViewModelExt.dismissCountrySheet
import junkeritechnepal.nicasiacmp.modules.login.LoginViewModelExt.fetchCountrySheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 24.dp),
        ) {

            item {
                Text("Welcome", style = AppTypography.bodyLarge, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(32.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    InputMobileNumberView(loginViewModel)
                    InputPasswordView(loginViewModel)
                }
                RememberMeView()
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                ScalableButtonClick()
                Spacer(modifier = Modifier.height(24.dp))
                NeedHelpLoginView()
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
                IconButton(onClick = { navController.navigate(NavigationRoutes.LOGIN_SECONDARY_ROUTE.name) }) {
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
        HorizontalDivider(thickness = 0.4.dp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
private fun InputPasswordView(loginViewModel: LoginViewModel) {
    var password by remember { mutableStateOf("") }
    TextField(
        value = password,
        onValueChange = { password = it },
        placeholder = { Text("Password") },
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = {
                loginViewModel.fetchCountrySheet()

            }) {
                Icon(Icons.Outlined.Lock, contentDescription = "Password Icon")
            }
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)
            .border(width = 0.9.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputMobileNumberView(loginViewModel: LoginViewModel) {
    var userNumber by remember { mutableStateOf("") }
    var countryLeadingState by remember { mutableStateOf("\uD83C\uDDF3\uD83C\uDDF5 NP") }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val countrySheetState = loginViewModel.countrySheetState.collectAsState()

    val onItemClick: (item: LoginCountryResDto) -> Unit  = { countryLeadingState = "${it.emoji} ${it.code}" }

    TextField(
        value = userNumber,
        onValueChange = { userNumber = it },
        placeholder = { Text("Mobile Number") },
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = {
                loginViewModel.fetchCountrySheet()

            }) {
                Text(countryLeadingState)
            }
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)
            .border(width = 0.9.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    if(countrySheetState.value.isVisible) {
        println("showCountrySheet..")
        ModalBottomSheet(
            shape = RoundedCornerShape(14.dp),
            onDismissRequest = { loginViewModel.dismissCountrySheet() },
            sheetState = bottomSheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                    Text("Choose a country", fontSize = 16.sp, fontStyle = FontStyle.Normal)
                    Image(imageVector = Icons.Outlined.Close, "", modifier = Modifier.clickable {
                        loginViewModel.dismissCountrySheet()
                    })
                }
                loginViewModel.countrySheetState.value.data.forEach {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(vertical = 4.dp).clickable { onItemClick(it) } ) {
                        Text("${it.emoji} ${it.name} ")
                    }
                }
            }
        }
    }
}

@Composable
fun ScalableButtonClick() {
    val navigator = LocalNavController.current
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    var isAnimating = false

    val onClick = {
        scope.launch {
            scale.animateTo(0.96f, animationSpec = tween(250))
            scale.animateTo(1f, animationSpec = tween(150))
            isAnimating = false
            navigator.navigate(NavigationRoutes.DASHBOARD_ROUTE.name)
        }
    }

    Button(
        onClick = {
            if(!isAnimating) {
                isAnimating = true
                onClick()
            }
        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier.fillMaxWidth()
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
    ) {
        Text("Login", style = AppTextStyle.bodyLight(fontWeight = FontWeight.SemiBold))
    }
}

@Composable
private fun RememberMeView() {
    val checkBoxState = remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checkBoxState.value, colors = CheckboxDefaults.colors(checkedColor = Color.Red, uncheckedColor = Color.Red), onCheckedChange = { checkBoxState.value = it })
        Text("Remember Me", style = AppTextStyle.bodyNormalDark)
    }
}


@Composable
private fun NeedHelpLoginView() {
   Column(horizontalAlignment = Alignment.CenterHorizontally) {
       Button(
           contentPadding = PaddingValues(vertical = 2.dp),
           colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
           onClick = { /* */ },
           modifier = Modifier.width(IntrinsicSize.Max).padding(vertical = 2.dp)
       ) { // RowScope
           Text(text = "Unable to login?", style = AppTextStyle.largeDark())
           Spacer(modifier = Modifier.width(16.dp))
           Icon(
               painter = painterResource(
                   Res.drawable.compose_multiplatform
               ),
               contentDescription = null,
               Modifier.height(20.dp),
               tint = Color.Red
           )
       }

       Button(
           contentPadding = PaddingValues(vertical = 2.dp),
           colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
           onClick = { /* */ },
           modifier = Modifier.width(IntrinsicSize.Max)
       ) { // RowScope
           Text(text = "Need Help?", style = AppTextStyle.largeDark())
       }
   }
}
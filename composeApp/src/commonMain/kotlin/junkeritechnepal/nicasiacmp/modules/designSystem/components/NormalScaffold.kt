package junkeritechnepal.nicasiacmp.modules.designSystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import junkeritechnepal.nicasiacmp.app.navigation.LocalNavController
import junkeritechnepal.nicasiacmp.infrastructure.utils.isIos
import junkeritechnepal.nicasiacmp.modules.designSystem.backgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NormalScaffold(
    title: String,
    onBack: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable (PaddingValues) -> Unit
) {
    val navController = LocalNavController.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(title = { Text(title) }, navigationIcon = {
                    IconButton(onClick = {
                        onBack?.invoke() ?: navController.popBackStack()
                    }) {
                        Icon(if(isIos()) Icons.AutoMirrored.Outlined.KeyboardArrowLeft else Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }, scrollBehavior = scrollBehavior
            )
        },
        containerColor = backgroundColor
    ) { paddings ->
        content(paddings)
    }
}
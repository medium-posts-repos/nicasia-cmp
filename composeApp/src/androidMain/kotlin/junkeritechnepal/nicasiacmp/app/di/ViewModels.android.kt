package junkeritechnepal.nicasiacmp.app.di

import androidx.lifecycle.viewmodel.compose.viewModel
import junkeritechnepal.nicasiacmp.app.viewmodels.ShareViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module = module {
    viewModelOf(::ShareViewModel)
}
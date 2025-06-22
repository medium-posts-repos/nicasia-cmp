package junkeritechnepal.nicasiacmp.app.di

import junkeritechnepal.nicasiacmp.app.viewmodels.ShareViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule: Module = module {
    singleOf(::ShareViewModel)
}
package com.dreamhome.ui

import com.dreamhome.screens.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val UiModule = module {
    viewModel { SearchViewModel(searchRepository = get()) }
}
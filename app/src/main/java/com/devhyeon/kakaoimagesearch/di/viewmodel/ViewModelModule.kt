package com.devhyeon.kakaoimagesearch.di.viewmodel

import com.devhyeon.kakaoimagesearch.network.KakaoApiViewModel
import com.devhyeon.kakaoimagesearch.ui.activities.intro.IntroViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { IntroViewModel() }

    viewModel { KakaoApiViewModel(get()) }
}
package com.devhyeon.kakaoimagesearch.di.viewmodel

import com.devhyeon.kakaoimagesearch.viewmodels.KakaoApiViewModel
import com.devhyeon.kakaoimagesearch.viewmodels.IntroViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/** ViewModel 모듈 */
val ViewModelModule = module {
    viewModel { IntroViewModel() }

    viewModel { KakaoApiViewModel(get()) }
}
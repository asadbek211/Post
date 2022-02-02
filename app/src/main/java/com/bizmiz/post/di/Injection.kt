package com.bizmiz.post.di

import com.bizmiz.post.MainViewModel
import com.bizmiz.post.api.ApiClient
import com.bizmiz.post.helper.NetworkHelper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    single { NetworkHelper(get()) }
    single { ApiClient.getPost() }
   }
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
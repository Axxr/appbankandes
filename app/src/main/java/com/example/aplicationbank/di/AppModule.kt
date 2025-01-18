package com.example.aplicationbank.di

import GetTransactionsUseCase
import com.example.aplicationbank.BuildConfig
import com.example.aplicationbank.data.local.TokenManager
import com.example.aplicationbank.data.remote.api.AuthApi
import com.example.aplicationbank.data.remote.api.ProductApi
import com.example.aplicationbank.data.repository.AuthRepositoryImpl
import com.example.aplicationbank.data.repository.ProductRepositoryImpl
import com.example.aplicationbank.domain.repository.AuthRepository
import com.example.aplicationbank.domain.repository.ProductRepository
import com.example.aplicationbank.domain.usecase.GetProductsUseCase
import com.example.aplicationbank.domain.usecase.LoginUseCase
import com.example.aplicationbank.domain.usecase.RefreshProductsUseCase
import com.example.aplicationbank.domain.usecase.ValidateSessionUseCase
import com.example.aplicationbank.presentation.detail.ProductDetailViewModel
import com.example.aplicationbank.presentation.home.HomeViewModel
import com.example.aplicationbank.presentation.login.LoginViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    single { Gson() }

    single {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    // API
    single { get<Retrofit>().create(AuthApi::class.java) }
    single { get<Retrofit>().create(ProductApi::class.java) }

    single { TokenManager(androidContext()) }

    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }

    single { LoginUseCase(get()) }
    single { ValidateSessionUseCase(get()) }

    // Use Cases
    single { GetProductsUseCase(get()) }
    single { RefreshProductsUseCase(get()) }
    single { GetTransactionsUseCase(get()) }

    // ViewModels
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { parameters ->
        ProductDetailViewModel(
            getProductsUseCase = get(),
            getTransactionsUseCase = get(),
            productId = parameters.get()
        )
    }
}

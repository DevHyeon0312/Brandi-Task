package com.devhyeon.kakaoimagesearch.di.network

import com.devhyeon.kakaoimagesearch.constants.KAKAO_BASE_URL
import com.devhyeon.kakaoimagesearch.network.kakao.KakaoAPI
import com.devhyeon.kakaoimagesearch.network.kakao.repository.KakaoRepository
import com.devhyeon.kakaoimagesearch.network.kakao.repository.KakaoRepositoryImp
import com.devhyeon.kakaoimagesearch.network.kakao.service.KakaoApiService
import org.koin.dsl.module
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Network 관련 모듈 */
val NetworkModule = module {
    /** Retrofit */
    single { createOkHttpClient() }
    single { createRetrofit(get(), KAKAO_BASE_URL) }

    /** KaKao API */
    single { createKakaoService(get()) }
    single { createKakaoRepository(get()) }
    single { KakaoAPI(get()) }
}

/** OkHttpClient 생성 */
fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

/** Retrofit 생성 */
fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

/** KaKao Service */
fun createKakaoService(retrofit: Retrofit): KakaoApiService {
    return retrofit.create(KakaoApiService::class.java)
}

/** KaKao Repository */
fun createKakaoRepository(apiService: KakaoApiService): KakaoRepository {
    return KakaoRepositoryImp(apiService)
}
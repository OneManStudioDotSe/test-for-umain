package se.onemanstudio.test.umain.di

import com.google.gson.GsonBuilder
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.onemanstudio.test.umain.BuildConfig
import se.onemanstudio.test.umain.network.FoodDeliveryService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val COMPLETE_TIMEOUT = 30L
private const val CONNECT_TIMEOUT = 15L
private const val GENERAL_TIMEOUT = 15L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(GENERAL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(GENERAL_TIMEOUT, TimeUnit.SECONDS)
            .callTimeout(COMPLETE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .disableHtmlEscaping()
                .create()
        )

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDeliveryService(retrofit: Retrofit): FoodDeliveryService =
        retrofit.create(FoodDeliveryService::class.java)
}

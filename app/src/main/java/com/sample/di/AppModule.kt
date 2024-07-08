package com.sample.di

import android.content.Context
import androidx.room.Room
import com.sample.data.repository.Repository
import com.sample.data.repository.RepositoryImpl
import com.sample.data.source.local.AppDatabase
import com.sample.data.source.local.ModelDao
import com.sample.data.source.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.yourservice.com/") // Replace with your base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "sample_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideModelDao(appDatabase: AppDatabase): ModelDao {
        return appDatabase.modelDao()
    }

    @Provides
    @Singleton
    fun provideMyRepository(
        apiService: ApiService,
        myDao: ModelDao
    ): Repository {
        return RepositoryImpl(apiService, myDao)
    }
}
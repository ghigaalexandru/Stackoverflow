/*
 * Copyright (c) 2021. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.ghalexandru.stackoverflow.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.ghalexandru.stackoverflow.R
import com.ghalexandru.stackoverflow.network.StackOverflowApi
import com.ghalexandru.stackoverflow.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    companion object {
        @Singleton
        @Provides
        fun provideRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideQuestionsApi(retrofit: Retrofit): StackOverflowApi {
            return retrofit.create(StackOverflowApi::class.java)
        }

        @Singleton
        @Provides
        fun provideRequestOptions(): RequestOptions {
            return RequestOptions
                .placeholderOf(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
        }

        @Singleton
        @Provides
        fun provideGlideInstance(
            @ApplicationContext context: Context,
            requestOptions: RequestOptions
        ): RequestManager {
            return Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
        }
    }
}
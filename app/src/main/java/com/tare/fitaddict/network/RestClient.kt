package com.tare.fitaddict.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class RestClient {
    companion object {
        fun create(): Services {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.readTimeout(10, TimeUnit.SECONDS)
            clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
            clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
            clientBuilder.addInterceptor(Interceptor { chain ->
                val request = chain.request()
                chain.proceed(request)
            })

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

            return retrofit.create(Services::class.java)
        }
    }
}
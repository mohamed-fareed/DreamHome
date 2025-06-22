package com.dreamhome.network

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val gson = get<Gson>()

        Retrofit.Builder()
            .baseUrl("https://dreamhome.free.beeceptor.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    single { get<Retrofit>().create(SearchService::class.java) }
}
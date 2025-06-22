package com.dreamhome.network

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        val gson = get<Gson>()

        Retrofit.Builder()
            .baseUrl("https://6c90f0a8-1a19-4139-9413-95b889ecfb19.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    single { get<Retrofit>().create(SearchService::class.java) }
}
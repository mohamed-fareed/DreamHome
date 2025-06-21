package com.dreamhome.data

import android.content.Context
import android.content.SharedPreferences
import com.dreamhome.data.search.SearchLocalDataSource
import com.dreamhome.data.search.SearchLocalDataSourceImpl
import com.dreamhome.data.search.SearchRemoteDataSource
import com.dreamhome.data.search.SearchRemoteDataSourceImpl
import com.dreamhome.data.search.SearchRepository
import com.dreamhome.data.search.SearchRepositoryImpl
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }

    single<SearchRemoteDataSource> { SearchRemoteDataSourceImpl(searchService = get()) }

    single<SearchLocalDataSource> {
        SearchLocalDataSourceImpl(
            sharedPreferences = get(),
            gson = get()
        )
    }
    single<Gson> { Gson() }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "home_dream_prefs",
            Context.MODE_PRIVATE
        )
    }
}
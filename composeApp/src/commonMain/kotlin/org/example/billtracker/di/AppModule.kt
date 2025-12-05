package org.example.billtracker.di

import org.example.billtracker.data.AppDatabase
import org.example.billtracker.data.BillRepository
import org.example.billtracker.data.BillRepositoryImpl
import org.koin.dsl.module

import org.example.billtracker.ui.MainViewModel

val appModule = module {
    single<BillRepository> { BillRepositoryImpl(get<AppDatabase>().billDao()) }
    single { MainViewModel(get()) }
}

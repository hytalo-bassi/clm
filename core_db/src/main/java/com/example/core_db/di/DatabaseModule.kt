package com.example.core_db.di

import android.content.Context
import com.example.core_db.data.AppDatabase
import com.example.core_db.data.ClienteDao
import com.example.core_db.data.ClienteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideClienteDao(db: AppDatabase) : ClienteDao {
        return db.clienteDao()
    }

    @Provides
    fun provideClienteRepository(clienteDao: ClienteDao) : ClienteRepository {
        return ClienteRepository.getInstance(clienteDao)
    }
}
package com.example.core_db.di

import android.content.Context
import com.example.core_db.data.AppDatabase
import com.example.core_db.data.ClienteDao
import com.example.core_db.data.ClienteRepository
import com.example.core_db.data.RepositorioCliente
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideDatabaseDispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

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

    @Provides
    fun provideRepoCliente(clienteDao: ClienteDao, dispatcher: CoroutineDispatcher) : RepositorioCliente {
        return RepositorioCliente.getInstance(clienteDao, dispatcher)
    }
}
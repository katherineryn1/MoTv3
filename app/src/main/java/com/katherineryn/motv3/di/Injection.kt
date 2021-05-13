package com.katherineryn.motv3.di

import android.content.Context
import com.katherineryn.motv3.data.MotvRepository
import com.katherineryn.motv3.data.source.local.LocalDataSource
import com.katherineryn.motv3.data.source.local.room.MotvDatabase
import com.katherineryn.motv3.data.source.remote.RemoteDataSource
import com.katherineryn.motv3.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MotvRepository {
        val database = MotvDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.motvDao())
        val appExecutors = AppExecutors()

        return MotvRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
package net.yuzumone.twltrus.tdr.di

import android.content.Context
import dagger.Module
import dagger.Provides
import net.yuzumone.twltrus.tdr.App

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }
}
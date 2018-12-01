package net.yuzumone.twltrus.tdr.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.yuzumone.twltrus.tdr.ui.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
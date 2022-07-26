package id.stefanusdany.favorite.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.stefanusdany.favorite.ui.FavoriteActivity
import id.stefanusdany.storyapp.di.FavoriteModuleDependencies

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

}
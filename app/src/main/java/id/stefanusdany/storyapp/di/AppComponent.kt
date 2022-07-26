package id.stefanusdany.storyapp.di

import dagger.Component
import id.stefanusdany.data.di.CoreComponent
import id.stefanusdany.storyapp.ui.addStory.AddStoryActivity
import id.stefanusdany.storyapp.ui.detail.DetailActivity
import id.stefanusdany.storyapp.ui.homepage.MainActivity
import id.stefanusdany.storyapp.ui.login.LoginActivity
import id.stefanusdany.storyapp.ui.maps.MapsActivity
import id.stefanusdany.storyapp.ui.register.RegisterActivity

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(registerActivity: RegisterActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(mapsActivity: MapsActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(detailActivity: DetailActivity)
    fun inject(addStoryActivity: AddStoryActivity)
}
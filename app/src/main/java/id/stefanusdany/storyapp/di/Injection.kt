package id.stefanusdany.storyapp.di


object Injection {

//    fun provideRepository(context: Context): IRepository {
//        val storyDatabase = StoryDatabase.getDatabase(context)
//        val dataStore = RxPreferenceDataStoreBuilder(context, "settings").build()
//        val userPreferences = UserPreference.getInstance(dataStore)
//        val localDataSource =
//            LocalDataSource.getInstance(storyDatabase.listStoryDao(), userPreferences)
//        val apiService = ApiConfig().getInstance()
//        val remoteDataSource = RemoteDataSource.getInstance(apiService)
//        val appExecutors = AppExecutors()
//        return Repository.getInstance(localDataSource, remoteDataSource, appExecutors)
//    }
//
//    fun provideAuthUseCase(context: Context): AuthUseCase =
//        AuthInteractor(provideRepository(context))
//
//    fun provideStoryUseCase(context: Context): StoryUseCase =
//        StoryInteractor(provideRepository(context))
}
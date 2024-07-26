import com.materip.core_datastore.test_datastore.TestDataStore
import com.materip.core_datastore.test_datastore.TestDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {
    @Singleton
    @Binds
    fun provideTestDataStore(testDataStore: TestDataStoreImpl): TestDataStore
}
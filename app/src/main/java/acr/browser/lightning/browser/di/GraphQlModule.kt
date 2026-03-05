package acr.browser.lightning.browser.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class GraphQlModule {

    @Provides
    @Singleton
    @GraphQlClient
    fun provideGraphQlOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    // TODO: Add auth headers when authentication is implemented
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideApolloClient(@GraphQlClient okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://api-data-browser.veonadtech.com/v1/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }
}

@Qualifier
@Retention(AnnotationRetention.SOURCE)
annotation class GraphQlClient

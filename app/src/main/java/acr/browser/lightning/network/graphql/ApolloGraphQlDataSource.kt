package acr.browser.lightning.network.graphql

import acr.browser.lightning.graphql.SendUserConsentMutation
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApolloGraphQlDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : GraphQlDataSource {

    override suspend fun sendUserConsent(
        userId: String,
        deviceId: String,
        consentGiven: Int
    ): SendUserConsentMutation.SaveUserConsent? {
        val response = apolloClient.mutation(
            SendUserConsentMutation(
                user_id = Optional.present(userId),
                device_id = Optional.present(deviceId),
                consent_given = Optional.present(consentGiven)
            )
        ).execute()
        return response.data?.saveUserConsent
    }
}

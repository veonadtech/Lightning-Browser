package acr.browser.lightning.network.graphql

import acr.browser.lightning.graphql.SendUserConsentMutation

interface GraphQlDataSource {

    suspend fun sendUserConsent(
        userId: String,
        deviceId: String,
        consentGiven: Int
    ): SendUserConsentMutation.SaveUserConsent?
}

package pl.bytebites.mobile.presentation.screen.profile.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.auth.UserAuthenticator as DomainUserAuthenticator
import pl.bytebites.mobile.domain.provider.UserProvider as DomainUserProvider
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser
import pl.bytebites.mobile.presentation.screen.profile.model.toProfileUser

class ProfileProviderInteractor(
    private val userProvider: DomainUserProvider,
    private val userAuthenticator: DomainUserAuthenticator
) : ProfileProvider {

    override fun getCurrentUser(): Flow<ProfileUser?> =
        userProvider.getCurrentUser().map { domainUser ->
            domainUser?.toProfileUser()
        }

    override suspend fun logout() = userAuthenticator.logout()

}

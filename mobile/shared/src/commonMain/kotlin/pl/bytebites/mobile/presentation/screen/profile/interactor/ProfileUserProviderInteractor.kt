package pl.bytebites.mobile.presentation.screen.profile.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.bytebites.mobile.domain.state.QueryState as DomainQueryState
import pl.bytebites.mobile.domain.user.CurrentUserProvider
import pl.bytebites.mobile.presentation.screen.profile.model.toProfileUser
import pl.bytebites.mobile.presentation.state.QueryState

class ProfileUserProviderInteractor(
    private val currentUserProvider: CurrentUserProvider
) : ProfileUserProvider {

    override fun provideCurrentUser(): Flow<CurrentUserProvidingState> =
        currentUserProvider.provide().map { state ->
            when (state) {
                is DomainQueryState.Value -> QueryState.Value(state.value?.toProfileUser())
                is DomainQueryState.Error -> QueryState.Error(state.throwable)
            }
        }

}

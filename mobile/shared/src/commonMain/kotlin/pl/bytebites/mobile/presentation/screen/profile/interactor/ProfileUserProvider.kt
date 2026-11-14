package pl.bytebites.mobile.presentation.screen.profile.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser
import pl.bytebites.mobile.presentation.state.QueryState

typealias CurrentUserProvidingState = QueryState<ProfileUser?>

interface ProfileUserProvider {

    fun provideCurrentUser(): Flow<CurrentUserProvidingState>

}

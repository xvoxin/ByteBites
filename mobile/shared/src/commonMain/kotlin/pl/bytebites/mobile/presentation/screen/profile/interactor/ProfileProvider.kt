package pl.bytebites.mobile.presentation.screen.profile.interactor

import kotlinx.coroutines.flow.Flow
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser

interface ProfileProvider {

    fun getCurrentUser(): Flow<ProfileUser?>

    suspend fun logout(): Result<Unit>

}

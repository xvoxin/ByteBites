package pl.bytebites.mobile.presentation.screen.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bytebites.shared.generated.resources.Res
import bytebites.shared.generated.resources.arrow_back
import bytebites.shared.generated.resources.person
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileEffect
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileIntent
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileState
import pl.bytebites.mobile.presentation.screen.profile.model.ProfileUser
import pl.bytebites.mobile.presentation.screen.profile.viewmodel.ProfileViewModel
import pl.bytebites.mobile.presentation.theme.CookbookTheme
import pl.bytebites.mobile.presentation.theme.Dimen

private val AVATAR_SIZE = 64.dp

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit = {},
    onNavigateToAuth: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ProfileEffect.NavigateToAuth -> onNavigateToAuth()
            }
        }
    }

    ProfileContent(
        state = state,
        onIntent = viewModel::onIntent,
        onBackClick = onNavigateBack,
        modifier = modifier
    )
}

@Composable
fun ProfileContent(
    state: ProfileState,
    onIntent: (ProfileIntent) -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ProfileTopBar(onBackClick = onBackClick)

            when {
                state.isLoading -> LoadingProfileState()
                state.user != null -> UserProfileCard(
                    user = state.user,
                    onLogoutClick = { onIntent(ProfileIntent.LogoutClicked) }
                )
                else -> GuestProfileCard(
                    onLoginClick = { onIntent(ProfileIntent.LoginClicked) }
                )
            }
        }
    }
}

@Composable
private fun ProfileTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.Margin_1, vertical = Dimen.Margin_05),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(Res.drawable.arrow_back),
                contentDescription = "Wróć",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Mój Profil",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Composable
private fun LoadingProfileState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimen.Margin_3),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun UserProfileCard(
    user: ProfileUser,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.Margin_2_5),
        shape = RoundedCornerShape(Dimen.RadiusXLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.ElevationMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Margin_3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(AVATAR_SIZE)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.person),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimen.IconSizeLarge)
                )
            }

            Spacer(modifier = Modifier.height(Dimen.Margin_1_5))

            Text(
                text = user.displayName,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(Dimen.Margin_05))

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(Dimen.Margin_2_5))

            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Wyloguj się")
            }
        }
    }
}

@Composable
private fun GuestProfileCard(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.Margin_2_5),
        shape = RoundedCornerShape(Dimen.RadiusXLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.ElevationMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Margin_3),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(AVATAR_SIZE)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.person),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimen.IconSizeLarge)
                )
            }

            Spacer(modifier = Modifier.height(Dimen.Margin_1_5))

            Text(
                text = "Witaj, Gościu!",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(Dimen.Margin_05))

            Text(
                text = "Zaloguj się, aby uzyskać dostęp do pełnych funkcji konta",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(Dimen.Margin_2_5))

            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zaloguj się")
            }
        }
    }
}

// --- COMPOSE PREVIEWS ---

@Composable
@Preview
fun ProfileContentGuestPreview() {
    CookbookTheme {
        ProfileContent(
            state = ProfileState(user = null),
            onIntent = {}
        )
    }
}

@Composable
@Preview
fun ProfileContentUserPreview() {
    val sampleUser = ProfileUser(
        id = "1",
        displayName = "Ania Kowalska",
        username = "@ania_cooks",
        email = "ania@bytebites.pl"
    )

    CookbookTheme {
        ProfileContent(
            state = ProfileState(user = sampleUser),
            onIntent = {}
        )
    }
}

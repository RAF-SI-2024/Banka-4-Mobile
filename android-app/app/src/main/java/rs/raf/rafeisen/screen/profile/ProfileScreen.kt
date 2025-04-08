package rs.raf.rafeisen.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.raf.rafeisen.core.ui.ErrorScreen
import rs.raf.rafeisen.core.ui.LoadingSpinner
import rs.raf.rafeisen.core.ui.NavigationScaffold
import rs.raf.rafeisen.drawer.DrawerScreenDestination
import rs.raf.rafeisen.screen.home.ui.BottomNavigationDestination
import rs.raf.rafeisen.screen.profile.ProfileContract.UiEvent
import rs.raf.rafeisen.screen.profile.ProfileContract.UiState
import rs.raf.rafeisen.screen.profile.model.ProfileUIModel
import rs.raf.rafeisen.screen.profile.ui.ColorGreenYes
import rs.raf.rafeisen.screen.profile.ui.ColorRedNo
import rs.raf.rafeisen.screen.profile.ui.InitialsAvatar
import rs.raf.rafeisen.screen.profile.ui.ProfileInfoItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomBarDestinationClick: (BottomNavigationDestination) -> Unit,
) {
    val state = viewModel.state.collectAsState()

    ProfileScreen(
        state = state.value,
        eventPublisher = viewModel::setEvent,
        onDrawerScreenDestinationClick = onDrawerScreenDestinationClick,
        onBottomBarDestinationClick = onBottomBarDestinationClick,
    )
}

@Composable
fun ProfileScreen(
    state: UiState,
    eventPublisher: (UiEvent) -> Unit,
    onDrawerScreenDestinationClick: (DrawerScreenDestination) -> Unit,
    onBottomBarDestinationClick: (BottomNavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavigationScaffold(
        drawerState = drawerState,
        onDrawerDestinationClick = onDrawerScreenDestinationClick,
        onBottomBarDestinationClick = onBottomBarDestinationClick,
        topBarTitleText = "Profile",
        selectedBottomBarDestination = BottomNavigationDestination.Profile,
    ) { paddingValues ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            when {
                state.isLoading -> LoadingSpinner(modifier = Modifier.fillMaxSize())

                state.error != null -> ErrorScreen(
                    message = "Failed to load profile.",
                    onRetry = { eventPublisher(UiEvent.LoadProfileData) },
                )

                state.profile != null -> {
                    val profile = state.profile

                    ProfileContent(profile = profile)
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(profile: ProfileUIModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        InitialsAvatar(
            initials = profile.fullName
                .split(" ")
                .take(2)
                .joinToString("") { it.first().uppercase() },
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "Personal Information",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            ProfileInfoItem(
                icon = Icons.Default.Person,
                label = "Name",
                value = profile.fullName,
            )
            ProfileInfoItem(
                icon = Icons.Default.Email,
                label = "Email",
                value = profile.email,
            )
            ProfileInfoItem(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = profile.phone,
            )
            ProfileInfoItem(
                icon = Icons.Default.LocationOn,
                label = "Address",
                value = profile.address,
            )
            ProfileInfoItem(
                icon = Icons.Default.CalendarToday,
                label = "Date of Birth",
                value = profile.dateOfBirth.toString(),
            )
            ProfileInfoItem(
                icon = Icons.Default.Transgender,
                label = "Gender",
                value = profile.gender.name,
            )

            Text(
                text = "Security Settings",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp),
            )
            ProfileInfoItem(
                icon = Icons.Default.Shield,
                label = "2FA Enabled",
                coloredValue = if (profile.has2FA) {
                    "Yes" to ColorGreenYes
                } else {
                    "No" to ColorRedNo
                },
            )
        }
    }
}

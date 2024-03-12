package com.example.pastry.ui.contents

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import com.example.pastry.ui.navigation.PastryDestination

@Composable
 fun BottomNavigation(allScreens: List<PastryDestination>,
                      onItemSelected: (PastryDestination) -> Unit,
                      currentScreen: PastryDestination
) {
    NavigationBar() {
        allScreens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon,contentDescription = "") },
                label = { },//Text(screen.route) },
                selected = currentScreen == screen,
                onClick = {onItemSelected(screen)
                }
            )
        }
    }
}


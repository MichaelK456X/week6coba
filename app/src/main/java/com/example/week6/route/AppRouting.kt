package com.example.week6.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.week6.view.*
import com.example.week6.viewmodel.*

enum class AppView(val title: String, val icon: ImageVector? = null) {
    Dashboard("Dashboard", Icons.Filled.Home),
    Communities("Communities", Icons.Filled.List),
    ClubProjects("Club Projects")
}

data class BottomNavItem(val view: AppView, val label: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentView: AppView?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = currentView?.title ?: AppView.Dashboard.title) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}

@Composable
fun MyBottomNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    items: List<BottomNavItem>
) {
    if (items.any { it.view.name == currentDestination?.route }) {
        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(imageVector = item.view.icon!!, contentDescription = item.label) },
                    label = { Text(text = item.label) },
                    selected = currentDestination?.hierarchy?.any { it.route == item.view.name } == true,
                    onClick = {
                        navController.navigate(item.view.name) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppRouting() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val currentView = AppView.entries.find { it.name == currentRoute?.split("/")?.get(0) }

    val sharedViewModel: CampusSharedViewModel = viewModel()
    val dialogFormViewModel: FormViewModel = viewModel()

    val bottomNavItems = listOf(
        BottomNavItem(view = AppView.Dashboard, label = "Dashboard"),
        BottomNavItem(view = AppView.Communities, label = "Communities")
    )

    Scaffold(
        topBar = {
            MyTopAppBar(
                currentView = currentView,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            MyBottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination,
                items = bottomNavItems
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AppView.Dashboard.name
        ) {
            composable(route = AppView.Dashboard.name) {
                ProfileDashboardView(sharedViewModel = sharedViewModel)
            }
            composable(route = AppView.Communities.name) {
                CommunitiesDirectoryView(
                    sharedViewModel = sharedViewModel,
                    onNavigateToClubDetail = { clubId ->
                        navController.navigate("${AppView.ClubProjects.name}/$clubId")
                    }
                )
            }
            composable(route = "${AppView.ClubProjects.name}/{clubId}") { backStackEntry ->
                val clubId = backStackEntry.arguments?.getString("clubId") ?: ""
                ClubProjectsDetailView(
                    communityId = clubId,
                    sharedViewModel = sharedViewModel,
                    dialogViewModel = dialogFormViewModel
                )
            }
        }
    }
}

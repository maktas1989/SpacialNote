package com.firsatbilisim.spacialnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.firsatbilisim.spacialnote.ui.screen.categoryscreen.AddCategoryScreen
import com.firsatbilisim.spacialnote.ui.screen.categoryscreen.CategoryDetailScreen
import com.firsatbilisim.spacialnote.ui.screen.categoryscreen.CategoryMainScreen
import com.firsatbilisim.spacialnote.ui.screen.notescreen.AddNoteScreen
import com.firsatbilisim.spacialnote.ui.screen.notescreen.NoteDetailScreen
import com.firsatbilisim.spacialnote.ui.screen.notescreen.NoteMainScreen
import com.firsatbilisim.spacialnote.ui.theme.SpacialNoteTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAds.initialize(this)
            SpacialNoteTheme {
                PageTransitions()
            }
        }
    }
}

@Composable
fun PageTransitions() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "category_main_screen") {
        composable("category_main_screen") {
            CategoryMainScreen(navController = navController)
        }
        composable("add_category_screen") {
            AddCategoryScreen(navController = navController)
        }
        composable("category_detail_screen/{categoryId}", arguments = listOf(
            navArgument("categoryId") { type = NavType.IntType }
        )) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0 // Default değer 0
            CategoryDetailScreen(categoryId, navController = navController)
        }
        composable("note_main_screen/{categoryId}", arguments = listOf(
            navArgument("categoryId") { type = NavType.IntType }
        )) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0 // Default değer 0
            NoteMainScreen(categoryId, navController = navController)
        }
        composable("add_note_screen/{categoryId}", arguments = listOf(
            navArgument("categoryId") { type = NavType.IntType }
        )) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0 // Default değer 0
            AddNoteScreen(categoryId, navController = navController)
        }
        composable("note_detail_screen/{noteId}", arguments = listOf(
            navArgument("noteId") { type = NavType.IntType }
        )) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
            NoteDetailScreen(noteId, navController = navController)
        }
    }
}

@Composable
fun StatusBar() {
    val systemUiController = rememberSystemUiController()
    val statusBarChange = colorResource(R.color.status_bar)

    systemUiController.setStatusBarColor(
        color = statusBarChange,
        darkIcons = false
    )
}

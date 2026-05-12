package ru.loginov.calendarlessons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.loginov.calendarlessons.Navigation.DrumNavigation
import ru.loginov.calendarlessons.ui.theme.CalendarLessonsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalendarLessonsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Start(Modifier)
                }
            }
        }
    }
}

@Composable
fun Start(modifier: Modifier = Modifier) {
    DrumNavigation()
}


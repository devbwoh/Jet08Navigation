package kr.ac.kumoh.ce.prof01.jet08navigation

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kr.ac.kumoh.ce.prof01.jet08navigation.ui.theme.Jet08NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

enum class Screen {
    Welcome,
    Lecture,
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val argLecture = "lecture"

    Jet08NavigationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Welcome.name,
            ) {
                composable(Screen.Welcome.name) {
                    WelcomeScreen {
                        navController.navigate("${Screen.Lecture.name}/$it")
                    }
                }
                composable(
                    route = "${Screen.Lecture.name}/{$argLecture}",
                    arguments = listOf(
                        navArgument(argLecture) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    LectureScreen(it.arguments?.getString(argLecture))
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onNavigateToLecture: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color(0xFFfefae0)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "\uD83C\uDFBC\nJetpack Compose를 사용한 안드로이드 프로그래밍",
            modifier = Modifier.padding(8.dp),
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFFbc6c25),
            lineHeight = 60.sp,
        )
        Button(
            onClick = {
                onNavigateToLecture("Jet04Modifier")
            }
        ) {
            Text("Modifier 강의 자료", fontSize = 30.sp)
        }
        Button(
            onClick = {
                onNavigateToLecture("Jet05Layout")
            }
        ) {
            Text("Layout 강의 자료", fontSize = 30.sp)
        }
    }
}

@Composable
fun LectureScreen(lecture: String?) {
    val lectureUrl = "https://github.com/devbwoh/$lecture"

    AndroidView(
        factory =  {
            WebView(it).apply {
                webViewClient = WebViewClient()
                // NOTE: 보안 주의
                settings.javaScriptEnabled = true
                loadUrl(lectureUrl)
            }
        }
    )
}
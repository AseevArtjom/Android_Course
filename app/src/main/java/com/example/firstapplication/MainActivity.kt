package com.example.firstapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstapplication.ui.theme.FirstApplicationTheme
import android.content.Context
import android.content.res.Configuration
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstApplicationTheme {
                changeLanguage(this,"uk")
                MyApp()
            }
        }
    }
}

fun changeLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}


@Composable
fun MyApp() {
    val configuration = LocalConfiguration.current
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                ButtonRow(orientation = "portrait")
            } else {
                ButtonRow(orientation = "landscape")
            }
        }
    }
}

@Composable
fun ButtonRow(orientation: String) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (orientation == "portrait") {
            Button(onClick = { showMessage(context, "message_1") }) {
                Text(text = stringResource(id = R.string.button_1))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showMessage(context, "message_2") }) {
                Text(text = stringResource(id = R.string.button_2))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showMessage(context, "message_3") }) {
                Text(text = stringResource(id = R.string.button_3))
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { showMessage(context, "message_1") }) {
                    Text(text = stringResource(id = R.string.button_1))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { showMessage(context, "message_2") }) {
                    Text(text = stringResource(id = R.string.button_2))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { showMessage(context, "message_3") }) {
                    Text(text = stringResource(id = R.string.button_3))
                }
            }
        }
    }
}

fun showMessage(context: android.content.Context, messageKey: String) {
    val message = when (messageKey) {
        "message_1" -> context.getString(R.string.message_1)
        "message_2" -> context.getString(R.string.message_2)
        "message_3" -> context.getString(R.string.message_3)
        else -> "Unknown message"
    }
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

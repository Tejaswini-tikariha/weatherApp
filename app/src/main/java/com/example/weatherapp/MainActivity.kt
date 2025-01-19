package com.example.weatherapp

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.EditableText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        weatherViewModel.getData("bangalore")  // city name
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherFun(
                        weatherViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherFun( viewModel: WeatherViewModel, modifier: Modifier = Modifier) {

    val weatherResult by viewModel.weatherResult.observeAsState()
    var text by rememberSaveable { mutableStateOf("Bangalore") }
    // viewModel.getData(text)  // city name


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(20.dp, 30.dp)
    ) {

       TextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.getData(it)
            },
            label = { Text("Enter City Name: ") }
        )


        when (val result = weatherResult) {
            is NetworkResponse.Success -> {
                Column (
                    Modifier.padding(20.dp, 30.dp)
                ) {

                    Text(
                        text = "Weather Details-",
                        color = Color.Blue,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${result.data.current.temp_c} *C",
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Location: ${result.data.location.name}",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Time: ${result.data.location.localtime}",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }


            }

            is NetworkResponse.Error -> {
               //  Text(text = result.message)

                Text(
                    text = "Please Enter City Name...",
                    color = Color.Red,
                    modifier = Modifier.padding(20.dp, 30.dp)
                )

            }

            NetworkResponse.Loading -> {


            }

            null -> {}
            else -> {}
        }



    }


}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WeatherFunPreview() {
    WeatherAppTheme {
        WeatherFun(WeatherViewModel() )
    }
}
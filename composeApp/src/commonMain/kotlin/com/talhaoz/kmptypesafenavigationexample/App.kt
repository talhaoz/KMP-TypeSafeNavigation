package com.talhaoz.kmptypesafenavigationexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object ScreenA

@Serializable
object ScreenB

@Serializable
data class ScreenC(
    val name : String,
    val age: Int
)

@Composable
@Preview
fun App() {
    MaterialTheme {

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = ScreenA
        ){
            composable<ScreenA> {
                ScreenA {
                    navController.navigate(ScreenB){
                        // TODO: enable to prevent the creation of multiple backstack entries for the same destination screen
                        //launchSingleTop = true

                    // TODO: enable to clear backstack
                        /*popUpTo<ScreenA>{
                            inclusive = true
                        }*/
                    }
                }
            }

            composable<ScreenB> {
                ScreenB(
                    onNavigateToCButtonClicked = { arguments ->
                        navController.navigate(arguments)
                    },
                    onNavigateToAButtonClicked = {
                        navController.navigate(ScreenA)
                    }
                )
            }

            composable<ScreenC> {
                val args = it.toRoute<ScreenC>()
                ScreenC(args.name,args.age) {
                    navController.navigateUp()
                }
            }
        }

    }
}

@Composable
fun ScreenA(
    onButtonClicked: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Screen A")
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onButtonClicked() }
        ){
            Text(text = "Go To B")
        }
    }
}

@Composable
fun ScreenB(
    onNavigateToCButtonClicked: (ScreenC) -> Unit,
    onNavigateToAButtonClicked: () -> Unit
){
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Screen B")
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = name,
            label = { Text(text = "Name") },
            onValueChange = { text ->
                name = text
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = age,
            label = { Text(text = "Age") },
            onValueChange = { text ->
                age = text
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onNavigateToCButtonClicked(
                ScreenC(
                    name = name,
                    age = age.toIntOrNull()?: 0
                )
            ) }
        ){
            Text(text = "Go To C")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { onNavigateToAButtonClicked() }
        ){
            Text(text = "Go Back To A")
        }
    }
}

@Composable
fun ScreenC(
    name: String,
    age: Int,
    onButtonClicked: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hello my name is $name!")
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "My age is $age, quite young hah!"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onButtonClicked() }
        ){
            Text(text = "Go Back")
        }
    }
}
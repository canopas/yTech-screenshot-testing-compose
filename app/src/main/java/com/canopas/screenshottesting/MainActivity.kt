package com.canopas.screenshottesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.screenshottesting.ui.theme.ScreenshotTestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenshotTestingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserListView(users)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewUserList() {
    ScreenshotTestingTheme {
        UserListView(users)
    }
}


@Composable
fun UserListView(users: List<User>) {
    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        }, title = {
            Text(text = "Home")
        })
    }, backgroundColor = Color.LightGray) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentPadding = PaddingValues(10.dp)
        ) {
            items(items = users) { user ->
                UserItem(user = user)
            }
        }
    }

}

@Composable
fun UserItem(user: User) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(10.dp)

    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.name.substring(0, 1),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Column(
            modifier = Modifier.padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = user.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(
                text = user.email,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }
}

val users = listOf(
    User("Jhone", "jhoneme@gmail.com"),
    User("Tommy", "jhoneme@gmail.com"),
    User("Harry", "jhoneme@gmail.com"),
    User("Jimmy", "jhoneme@gmail.com"),
    User("Elye", "jhoneme@gmail.com"),
    User("Robert", "jhoneme@gmail.com"),
    User("Jennie", "jhoneme@gmail.com"),
)
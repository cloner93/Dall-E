package com.milad.dall_e.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen() {
    Scaffold() { SearchScreenContent() }
}

@Composable
private fun SearchScreenContent(modifier: Modifier = Modifier) {
    Column(modifier.padding(8.dp)) {
        SearchBar()
    }
}

@Composable
private fun SearchBar() {
    var text by rememberSaveable { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        OutlinedTextField(
            modifier = Modifier
                .requiredSizeIn(minWidth = 80.dp, minHeight = 100.dp)
                .fillMaxWidth()
                .weight(2.5f),
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = { Text("Enter what is in your mind.", fontSize = 14.sp) },
            maxLines = 4,
            trailingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = null) }
        )
        Button(
            {}, modifier = Modifier
                .requiredSizeIn(minWidth = 80.dp,maxWidth = 120.dp)
                .weight(1f)
        ) { Text("Generate", fontSize = 15.sp) }
    }
}


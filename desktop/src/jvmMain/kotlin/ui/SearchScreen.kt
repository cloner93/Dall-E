package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.milad.dall_e.ImageSize
import com.milad.dall_e.SearchState
import com.milad.dall_e.SearchViewModel
import com.milad.dall_e.network.DallEApiImpl
import com.milad.dall_e.network.model.Data
import com.milad.dall_e.network.model.RequestBody
import utils.AsyncImage
import utils.loadImageBitmap

@Composable
fun SearchScreen() {
    val viewModel = SearchViewModel(DallEApiImpl())

    val state by produceState<SearchState>(initialValue = SearchState.Loading){
        viewModel.generatedImages.collect{
            value = it
        }
    }

    Scaffold {
        SearchScreenContent(
            onclick = {
                val body = RequestBody(5, it, ImageSize.X256.value) // TODO:user can change another value

                viewModel.generateImage(body)
            },
            state = state
        )
    }
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    onclick: (String) -> Unit,
    state: SearchState
) {
    Column(modifier.padding(8.dp)) {
        SearchBar(onClick = onclick)
        when(state){
            is SearchState.Loading->{
                // TODO:
            }
            is SearchState.Error->{
                // TODO:
            }
            is SearchState.Success->{
                ImageList(list = state.data)
            }
        }
    }
}

@Composable
private fun SearchBar(onClick: (String) -> Unit) {
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
            { onClick.invoke(text) },
            modifier = Modifier
                .requiredSizeIn(minWidth = 80.dp, maxWidth = 120.dp)
                .weight(1f)
        ) { Text("Generate", fontSize = 15.sp) }
    }
}

@Composable
private fun ImageList(list: List<Data> = listOf()) {
    Box {
        LazyColumn {
            items(list) { item ->
                ListItem(item)
            }
        }
    }
}
/*
* TODO
* implement ui in android platform
* create viewModels
* networks
*/

@Composable
private fun ListItem(item: Data) {
    Card(modifier = Modifier.fillMaxWidth().size(200.dp)) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            load = { loadImageBitmap(item.url) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "Cast",
            contentScale = ContentScale.Crop
        )
    }
}


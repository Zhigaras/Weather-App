package com.example.weatherapp.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.presentation.MainViewModel
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun SearchScreen(
    onSearch: () -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel()
    val lazyHistoryItems = viewModel.historyItemsFlow.collectAsState(initial = emptyList())
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    fun onItemClick(item: String) {
        textState.value = TextFieldValue(item)
        viewModel.searchWeather(item)
        onSearch()
    }
    
    Column {
        CitySearchView(textState, onSearchButtonClick = { viewModel.searchWeather(it) })
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Search history",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            ClickableText(
                text = AnnotatedString("clear"),
                onClick = { viewModel.clearRequestHistory() })
        }
        ItemList(
            state = textState,
            items = lazyHistoryItems,
            onItemClick = { onItemClick(it) },
            onDeleteClick = { viewModel.deleteRequest(it) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySearchView(
    state: MutableState<TextFieldValue>,
    onSearchButtonClick: (String) -> Unit
) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp),
        leadingIcon = {
            IconButton(
                content = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp),
                onClick = { onSearchButtonClick(state.value.text) }
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun ItemList(
    state: MutableState<TextFieldValue>,
    items: State<List<String>>,
    onItemClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    
    var filteredItems: List<String>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text
        filteredItems = if (searchedText.isEmpty()) {
            items.value
        } else {
            val resultList = ArrayList<String>()
            for (item in items.value) {
                if (item.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(item)
                }
            }
            resultList
        }
        items(filteredItems) { filteredItem ->
            ItemListItem(
                ItemText = filteredItem,
                onItemClick = { onItemClick(it) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun ItemListItem(
    ItemText: String,
    onItemClick: (String) -> Unit = {},
    onDeleteClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(ItemText) })
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 8.dp))
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = ItemText,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
        IconButton(
            onClick = { onDeleteClick(ItemText) }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete request"
            )
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    ItemListItem("Example")
}

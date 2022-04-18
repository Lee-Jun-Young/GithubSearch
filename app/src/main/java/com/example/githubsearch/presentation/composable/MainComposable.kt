package com.example.githubsearch.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.example.domain.model.User
import com.example.githubsearch.R
import com.example.githubsearch.presentation.intent.MainIntent
import com.example.githubsearch.presentation.state.MainState
import com.example.githubsearch.presentation.viewmodel.MainViewModel
import com.google.accompanist.appcompattheme.AppCompatTheme
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun MainView(viewModel: MainViewModel, onClick: (User) -> Unit) {
    val userState = viewModel.state.observeAsState().value
    userState?.let {
        with(it) {
            when (it) {
                is MainState.SearchUser -> {
                    val data = it.searchUser.collectAsLazyPagingItems()
                    data.apply {
                        when (loadState.refresh) {
                            is LoadState.Loading -> {
                                showProgress()
                            }
                            is LoadState.NotLoading -> {
                                if (itemCount == 0) MainEmptyView()
                            }
                        }
                    }
                    LazyColumn {
                        Modifier.fillMaxWidth()
                        items(data.itemCount) { index ->
                            data[index]?.let { user ->
                                RvItem(user = user, onClick = onClick)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun showProgress() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = Color.Red,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

@Composable
fun MainEmptyView() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column {
            val image: Painter =
                painterResource(id = com.example.githubsearch.R.drawable.ic_empty_github)
            Image(
                painter = image,
                contentDescription = "",
                Modifier
                    .size(65.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
            )
            Text(text = "검색결과가 없습니다.", Modifier.align(Alignment.CenterHorizontally))
            Text(text = "다른 ID를 입력해 주세요.", Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun RvItem(user: User, onClick: (User) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick(user)
        }) {
        Surface(
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp),
            shape = CircleShape
        ) {
            Image(
                painter = rememberImagePainter(
                    data = user.avatarUrl
                ),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(user.login)
        }
    }
}

@Composable
fun BookMarkView(viewModel: MainViewModel, onClick: (User) -> Unit) {
    val userState = viewModel.state.observeAsState().value
    userState?.let {
        with(it) {
            when (it) {
                is MainState.BookMarkUser -> {
                    LazyColumn {
                        Modifier.fillMaxWidth()
                        items(it.bookmarkUser) { user ->
                            RvItem(user = user, onClick = onClick)
                        }
                    }
                }
                is MainState.IsBookMarkEmpty -> {
                    if (it.isBookMarkEmpty) BookMarkEmptyView()
                }
            }
        }
    }
}

@Composable
fun BookMarkEmptyView() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column {
            val image: Painter = painterResource(id = R.drawable.ic_empty)
            Image(
                painter = image,
                contentDescription = "",
                Modifier
                    .size(65.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp),
            )
            Text(text = "즐겨찾기된 계정이 없습니다.")
        }
    }
}

@Composable
fun SearchText(mainViewModel: MainViewModel) {

    val white = Color(0xFFFFFFFF)
    var textState by remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    TextField(
        value = textState,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                scope.launch {
                    mainViewModel.userIntent.send(MainIntent.SearchUser(textState.text))
                }
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onValueChange = { textState = it },
        placeholder = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "검색할 Github ID를 입력해주세요."
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = white
        )
    )
}

@Composable
fun Scaffold(
    mainViewModel: MainViewModel,
    onClick: (User) -> Unit
) {
    val black = Color(0xFF000000)
    val white = Color(0xFFFFFFFF)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "GithubSearch", color = white)
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                if (isClosed) {
                                    mainViewModel.userIntent.send(MainIntent.BookMarkUser)
                                    open()
                                } else close()
                            }
                        }
                    }) {
                        val image: Painter = painterResource(id = R.drawable.list)
                        Image(
                            painter = image,
                            contentDescription = "",
                            Modifier
                                .size(24.dp)
                        )
                    }
                }, backgroundColor = black
            )
        },
        drawerContent = { BookMarkView(viewModel = mainViewModel, onClick = onClick) },
        content = {
            Column {
                SearchText(mainViewModel = mainViewModel)
                MainView(viewModel = mainViewModel, onClick = onClick)
            }
        }
    )
}

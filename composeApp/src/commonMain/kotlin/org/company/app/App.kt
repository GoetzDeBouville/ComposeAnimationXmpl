package org.company.app

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.company.app.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() = AppTheme {
    val modifier = Modifier
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Мой заголовок")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                println("Clicked")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(modifier = modifier
            .padding(innerPadding)
            .verticalScroll(scrollState)
        ) {
            Text(
                modifier = modifier.padding(16.dp),
                text = "Привет, мир!"
            )
            ColorAnimationExample(modifier)
            TimedColorChangeAnimation(modifier)
            SizeAnimationExample(modifier)
            VisibilityAnimationExample(modifier)
            AlphaAnimationExample(modifier)
        }
    }
}

@Composable
fun ColorAnimationExample(modifier: Modifier) {
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked) Color.Red else Color.Blue,
        animationSpec = tween(durationMillis = 2000)
    )

    Box(
        modifier = modifier
            .padding(start = 16.dp)
            .size(100.dp)
            .background(backgroundColor)
            .clickable { isClicked = !isClicked }
    )
}


@Composable
fun TimedColorChangeAnimation(modifier: Modifier) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    var currentColorIndex by remember { mutableStateOf(0) }

    val backgroundColor = remember { Animatable(colors[currentColorIndex]) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            currentColorIndex = (currentColorIndex + 1) % colors.size
            backgroundColor.animateTo(
                targetValue = colors[currentColorIndex],
                animationSpec = tween(durationMillis = 1000)
            )
        }
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
            .size(100.dp)
            .background(backgroundColor.value)
    )
}


@Composable
fun SizeAnimationExample(modifier: Modifier) {
    var isClicked by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (isClicked) 400.dp else 100.dp,
        animationSpec = tween(durationMillis = 2000)
    )

    val rotation by animateFloatAsState(
        targetValue = if (isClicked) 360f else 90f,
        animationSpec = tween(durationMillis = 2000)
    )

    Box(
        modifier = modifier
            .padding(16.dp)
            .size(size)
            .rotate(rotation)
            .background(Color.Green)
            .clickable { isClicked = !isClicked }
    )
}

@Composable
fun VisibilityAnimationExample(modifier: Modifier) {
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 2000)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 2000)
        ),
    ) {
        Box(
            modifier = modifier
                .padding(16.dp)
                .size(100.dp)
                .background(Color.Yellow)
                .clickable { isVisible = false }
        )
    }
}

@Composable
fun AlphaAnimationExample(modifier: Modifier) {
    var isClicked by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isClicked) 0f else 1f,
        animationSpec = tween(durationMillis = 2000)
    )

    Box(
        modifier = modifier
            .padding(16.dp)
            .size(100.dp)
            .background(Color.Red.copy(alpha = alpha))
            .clickable { isClicked = !isClicked }
    )
}
package com.materip.matetrip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.materip.core_designsystem.R
import com.materip.matetrip.icon.Icons
import com.materip.matetrip.theme.MatetripColor

@Composable
fun CircleImageView(
    size: Dp,
    imageUrl: String
){
    AsyncImage(
        modifier = Modifier
            .size(size)
            .background(color = MatetripColor.Blue_04, shape = CircleShape)
            .clip(CircleShape),
        model = imageUrl,
        contentDescription = "Circle Image View",
        contentScale = ContentScale.Fit,
        placeholder = painterResource(R.drawable.ic_app_logo_loading),
        error = painterResource(R.drawable.ic_app_logo_loading)
    )
}

@Composable
fun ImageLoadView(
    backgroundColor: Color,
    shape: Shape,
    size: Dp,
    imageUrl: String,
    isEnabledClose: Boolean = false,
    onCloseClick: () -> Unit,
){
    Box(
        modifier = Modifier
            .size(size)
            .background(color = backgroundColor, shape = shape)
            .clip(shape)
    ){
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape),
            model = imageUrl,
            contentDescription = "Circle Image View",
            contentScale = ContentScale.Fit,
            placeholder = painterResource(R.drawable.ic_app_logo_loading),
            error = painterResource(R.drawable.ic_app_logo_loading)
        )
        if(isEnabledClose){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 6.dp, end = 6.dp),
                contentAlignment = Alignment.TopEnd
            ){
                IconButton(
                    modifier = Modifier.size(12.dp),
                    onClick = onCloseClick
                ){
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(Icons.close_icon),
                        contentDescription = "Close Button"
                    )
                }
            }
        }
    }
}

@Composable
fun TravelPostItem(
    modifier: Modifier = Modifier,
    destination: String,
    period: String,
    title: String,
    startDate: String,
    endDate: String,
    postImage: String?,
    onClick: () -> Unit,
){
    Row(
        modifier = modifier
            .height(115.dp)
            .border(
                width = 1.dp,
                color = MatetripColor.Blue_03,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 15.5.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Row{
                    Text(
                        modifier = Modifier
                            .height(27.dp)
                            .background(
                                color = MatetripColor.Primary,
                                shape = RoundedCornerShape(size = 6.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        text = destination,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        modifier = Modifier
                            .height(27.dp)
                            .background(
                                color = MatetripColor.Blue_04,
                                shape = RoundedCornerShape(size = 6.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        text = period,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                    fontWeight = FontWeight(700)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "${startDate} ~ ${endDate}",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400)
                )
            }
            AsyncImage(
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(size = 10.dp)),
                contentScale = ContentScale.Crop,
                model = postImage,
                contentDescription = "Post Image",
            )
        }
    }
}

@Composable
fun QuizItem(
    indexNumber: Int,
    title: String,
    onUpdateTitle: (String) -> Unit,
    answer: String,
    onUpdateAnswer: (String) -> Unit,
){
    val keyboardController = LocalSoftwareKeyboardController.current
    var isClicked by remember{mutableStateOf(false)}
    var titleText by remember{mutableStateOf(title)}
    var answerText by remember{mutableStateOf(answer)}
    var readOnly by remember{mutableStateOf(title.isNotEmpty())}

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .height(62.dp)
                .border(
                    width = 1.dp,
                    color = MatetripColor.Blue_03,
                    shape = if (isClicked) RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp
                    ) else RoundedCornerShape(10.dp)
                )
                .clickable { isClicked = !isClicked }
                .padding(horizontal = 12.dp, vertical = 19.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = MatetripColor.Blue_03, shape = CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "${indexNumber}",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = MatetripColor.Gray_10
                )
            }
            Spacer(Modifier.width(8.dp))
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                readOnly = readOnly,
                value = titleText,
                onValueChange = {
                    titleText = it
                    onUpdateTitle(it)
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = MatetripColor.Gray_07,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        readOnly = true
                    }
                )
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if(titleText.isNotEmpty()){
                        it()
                    } else {
                        Text(
                            text = "제목을 작성해 주세요.",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(400),
                            color = MatetripColor.Gray_07
                        )
                    }
                }
            }
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(if(isClicked) Icons.fold_icon else Icons.navigate_next_icon),
                contentDescription = "Fold Down Icon"
            )
        }
        if(isClicked){
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        color = MatetripColor.Blue_04,
                        shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
                    ),
                value = answerText,
                onValueChange = {
                    answerText = it
                    onUpdateAnswer(it)
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = MatetripColor.Gray_07,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400)
                )
            ){
                Row(
                    modifier = Modifier.padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = " · ",
                        color = MatetripColor.Gray_07,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400)
                    )
                    if(answerText.isNotEmpty()){
                        it()
                    } else {
                        Text(
                            text = "내용을 입력해주세요",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                            fontWeight = FontWeight(400),
                            color = MatetripColor.Gray_07
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizEditItem(
    indexNumber: Int,
    title: String,
    isClicked: Boolean,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .border(width = 1.dp, color = MatetripColor.Blue_03, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 19.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = MatetripColor.Blue_03, shape = CircleShape),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "${indexNumber}",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
                fontWeight = FontWeight(500),
                color = MatetripColor.Gray_10
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.noto_sans_kr)),
            fontWeight = FontWeight(500)
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(20.dp),
            onClick = onClick
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(if(isClicked) Icons.selected_radio_btn else Icons.not_selected_radio_btn),
                contentDescription = "Radio Button"
            )
        }
    }
}
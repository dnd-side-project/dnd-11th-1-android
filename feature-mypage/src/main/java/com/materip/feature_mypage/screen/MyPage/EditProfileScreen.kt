package com.materip.feature_mypage.screen.MyPage

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.materip.core_common.ErrorState
import com.materip.core_common.checkPermission
import com.materip.core_common.toDisplayString
import com.materip.core_designsystem.component.CustomClickableTag
import com.materip.core_designsystem.component.ImageLoadView
import com.materip.core_designsystem.component.NormalTopBar
import com.materip.core_designsystem.component.SelectableDialog
import com.materip.core_designsystem.component.UnderlinedTextField
import com.materip.core_designsystem.icon.Badges
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.core_model.ui_model.FoodPreference
import com.materip.core_model.ui_model.Gender
import com.materip.core_model.ui_model.TravelInterest
import com.materip.core_model.ui_model.TravelStyle
import com.materip.feature_mypage.view_models.MyPage.EditProfileUiState
import com.materip.feature_mypage.view_models.MyPage.EditProfileViewModel
import com.materip.matetrip.toast.CommonToastView
import com.materip.matetrip.toast.ErrorView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EditProfileRoute(
    navBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val errState = viewModel.errorState.collectAsStateWithLifecycle()
    val images = viewModel.images
    val isDone = viewModel.isDone.collectAsStateWithLifecycle()
    var showToast by remember{mutableStateOf(false)}
    EditProfileScreen(
        uiState = uiState.value,
        errState = errState.value,
        images = images,
        navBack = {
            if (errState.value !is ErrorState.AuthError || !(errState.value as ErrorState.AuthError).isInvalid()) {
                navBack()
            }
        },
        onEditClick = {profileImg, nickname, description, birthYear, gender, travelPreferences, travelStyles, foodPreferences, snsLink, images ->
            viewModel.updateProfile(profileImg, nickname, description, birthYear, gender, travelPreferences,
                travelStyles, foodPreferences, snsLink, images)
        },
        onUploadImage = {
            Log.d("TAG TEST", "on upload image : ${it}")
            it.map{
                viewModel.saveImageToS3(
                    context = context,
                    path = it
                )
            }
        },
        onDeleteImage = {viewModel.deleteImage(it)},
        onUpdateProfileImg = { viewModel.updateProfileImg(context, it) }
    )

    if(showToast){
        CommonToastView(message = "프로필 수정 완료")
        showToast = false
    }
    LaunchedEffect(isDone.value){
        if(isDone.value){
            showToast = true
            navBack()
        }
    }
}

@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    errState: ErrorState,
    images: SnapshotStateList<String>,
    onEditClick: (profileImg: String, nickname: String, description: String, birthYear: Int, gender: String,
                  travelPreferences: SnapshotStateList<String>, travelStyles: SnapshotStateList<String>,
                  foodPreference: SnapshotStateList<String>, snsLink: String?, images: SnapshotStateList<String>) -> Unit,
    onUploadImage: (List<Uri>) -> Unit,
    onDeleteImage: (String) -> Unit,
    onUpdateProfileImg: suspend (Uri) -> String,
    navBack: () -> Unit
){
    when(uiState){
        EditProfileUiState.Loading -> {
            CircularProgressIndicator()
        }
        is EditProfileUiState.Success -> {
            EditProfileMainContent(
                initProfileImg = uiState.profileImg,
                initNickname = uiState.nickname,
                initDescription = uiState.description,
                initBirthYear = uiState.birthYear,
                initGender = uiState.gender,
                initTravelPreferences = uiState.travelPreferences,
                initTravelStyles = uiState.travelStyles,
                initFoodPreferences = uiState.foodPreferences,
                initSnsLink = uiState.snsLink,
                images = images,
                onEditClick = onEditClick,
                onUploadImage = onUploadImage,
                onDeleteImage = onDeleteImage,
                onUpdateProfileImg = onUpdateProfileImg,
                navBack = navBack
            )
        }
        EditProfileUiState.Error -> {
            ErrorView(
                errState = errState,
                navBack = navBack
            )
        }
    }
}

@Composable
private fun EditProfileMainContent(
    initProfileImg: String,
    initNickname: String,
    initDescription: String?,
    initBirthYear: Int,
    initGender: Gender,
    initTravelPreferences: List<String>,
    initTravelStyles: List<String>,
    initFoodPreferences: List<String>,
    initSnsLink: String?,
    images: SnapshotStateList<String>,
    onEditClick: (profileImg: String, nickname: String, description: String, birthYear: Int, gender: String,
                  travelPreferences: SnapshotStateList<String>, travelStyles: SnapshotStateList<String>,
                  foodPreferences: SnapshotStateList<String>, snsLink: String?, images: SnapshotStateList<String>) -> Unit,
    onUploadImage: (List<Uri>) -> Unit,
    onDeleteImage: (String) -> Unit,
    onUpdateProfileImg: suspend (Uri) -> String,
    navBack: () -> Unit,
){
    val scrollState = rememberScrollState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    var selected by remember{mutableStateOf("")}
    var profileImg by remember{mutableStateOf("")}
    var nickname by remember{mutableStateOf("")}
    var introduction by remember{mutableStateOf("")}
    var birthYear by remember{mutableStateOf(0)}
    var gender by remember{mutableStateOf(Gender.FEMALE)}
    val travelPreferences = remember{ mutableStateListOf(*initTravelPreferences.toTypedArray())}
    val travelStyles = remember{ mutableStateListOf(*initTravelStyles.toTypedArray()) }
    val foodPreferences = remember{ mutableStateListOf(*initFoodPreferences.toTypedArray()) }
    var snsLink by remember{mutableStateOf("")}
    LaunchedEffect(Unit){
        nickname = initNickname
        profileImg = initProfileImg
        introduction = initDescription ?: ""
        snsLink = initSnsLink ?: ""
        birthYear = initBirthYear
        gender = initGender
    }
    val myImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
            if(images.size + it.size <= 4){
                onUploadImage(it)
            } else {
                Toast.makeText(context, "사진은 4장까지 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )
    val profileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            coroutine.launch{
                if (uri != null){
                    val path = onUpdateProfileImg(uri)
                    profileImg = path
                }
            }
        }
    )
    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {isGranted ->
        if (isGranted){
            if (selected == "profile"){
                profileLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                myImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        } else {
            Toast.makeText(context, "권한이 거절되어 갤러리에 연동이 불가능합니다", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp)
    ){
        NormalTopBar(
            title = "프로필 수정",
            onBackClick = navBack,
            onClick = {
                onEditClick(profileImg,nickname,introduction,birthYear,gender.name,travelPreferences,travelStyles,foodPreferences,snsLink,images)
            },
            menuText = "확인"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ){
            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(color = MateTripColors.Blue_04, shape = CircleShape)
                        .clickable {
                            selected = "profile"
                            if (checkPermission(context, "camera")) {
                                profileLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            } else {
                                requestPermissionLauncher.launch(
                                    if (Build.VERSION.SDK_INT >= 33) {
                                        Manifest.permission.READ_MEDIA_IMAGES
                                    } else {
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    }
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    ImageLoadView(
                        backgroundColor = MateTripColors.Blue_04,
                        shape = CircleShape,
                        size = 80.dp,
                        imageUrl = profileImg,
                        onCloseClick = {/* 미사용 */}
                    )
                    Icon(
                        painter = painterResource(Icons.camera_icon),
                        contentDescription = "Camera Icon"
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
            NicknameEdit(
                nickname = nickname,
                onNicknameUpdate = {if(it.length <= 6){nickname = it}}
            )
            Spacer(Modifier.height(40.dp))
            MyIntroductionEdit(
                introduction = introduction,
                onIntroductionUpdate = {introduction = it}
            )
            Spacer(Modifier.height(40.dp))
            BirthAndGenderEdit(
                birth = birthYear.toString(),
                onBirthUpdate = {birthYear = it.toInt()},
                gender = gender,
                onGenderUpdate = {gender = it}
            )
            Spacer(Modifier.height(40.dp))
            TravelPreferencesEdit(travelPreferences = travelPreferences)
            Spacer(Modifier.height(40.dp))
            TravelStyleEdit(travelStyles = travelStyles)
            Spacer(Modifier.height(40.dp))
            FoodPreferenceEdit(foodPreferences = foodPreferences)
            Spacer(Modifier.height(40.dp))
            SNSEdit(
                snsLink = snsLink,
                onUpdateSnsLink = {snsLink = it}
            )
            Spacer(Modifier.height(40.dp))
            MyImages(
                pictures = images,
                onCameraClick = {
                    selected = "gallery"
                    if(checkPermission(context, "camera")){
                        myImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    } else {
                        requestPermissionLauncher.launch(
                            if (Build.VERSION.SDK_INT >= 33){
                                Manifest.permission.READ_MEDIA_IMAGES
                            } else {
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            }
                        )
                    }
                },
                onDeleteImage = {
                    images.remove(it)
                    onDeleteImage(it)
                }
            )
        }
    }
}

@Composable
private fun NicknameEdit(
    nickname: String,
    onNicknameUpdate: (String) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "닉네임",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(12.dp))
        UnderlinedTextField(
            value = nickname,
            onValueChange = onNicknameUpdate,
            placeholder = null,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            textColor = MateTripColors.Gray_10,
            underlineColor = MateTripColors.Gray_10
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "닉네임은 6글자 이내만 가능합니다.",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(400),
            color = MateTripColors.Gray_06
        )
    }
}

@Composable
private fun MyIntroductionEdit(
    introduction: String,
    onIntroductionUpdate: (String) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "내 소개",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(12.dp))
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(102.dp)
                .background(
                    color = MateTripColors.Blue_04,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(12.dp),
            value = introduction,
            onValueChange = onIntroductionUpdate,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(400),
                color = MateTripColors.Gray_11
            ),
        ){
            if(introduction.isEmpty()){
                Text(
                    text = "나는 어떤 사람인지, 어떤 여행을 좋아하는 지에 대해서 작성해주세요.(최대 50자)",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    color = MateTripColors.Gray_07
                )
            } else {
                it()
            }
        }
    }
}

@Composable
private fun BirthAndGenderEdit(
    birth: String,
    onBirthUpdate: (String) -> Unit,
    gender: Gender,
    onGenderUpdate: (Gender) -> Unit,
){
    val birthRange = (1950..2024).toMutableList().map{it.toString()}.toMutableList().apply{
        this.add(0, "출생연도 선택")
    }
    val genderRange = listOf("성별", "남성", "여성")
    var isBirthDialogOpen by remember{mutableStateOf(false)}
    var isGenderDialogOpen by remember{mutableStateOf(false)}
    if(isBirthDialogOpen){
        SelectableDialog(
            isUsedScroll = true,
            value = birth,
            onValueChange = onBirthUpdate,
            options = birthRange,
            onDismissRequest = {isBirthDialogOpen = false}
        )
    } else if (isGenderDialogOpen){
        SelectableDialog(
            value = gender.toDisplayString(),
            onValueChange = {
                val newGender = if(it == "남성") Gender.MALE else Gender.FEMALE
                onGenderUpdate(newGender)
            },
            options = genderRange,
            onDismissRequest = {isGenderDialogOpen = false}
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = "출생연도",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clickable { isBirthDialogOpen = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = birth,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_10
                )
                Icon(
                    painter = painterResource(Icons.fold_icon),
                    contentDescription = "Fold Icon"
                )
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MateTripColors.Gray_11)
        }
        Spacer(Modifier.width(40.dp))
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = "성별",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .clickable { isGenderDialogOpen = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = gender.toDisplayString(),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = MateTripColors.Gray_10
                )
                Icon(
                    painter = painterResource(Icons.fold_icon),
                    contentDescription = "Fold Icon"
                )
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MateTripColors.Gray_11)
        }
    }
}

@Composable
private fun TravelPreferencesEdit(travelPreferences: SnapshotStateList<String>){
    var isOpen by remember{mutableStateOf(false)}
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable{isOpen = !isOpen},
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "여행 성향",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                fontWeight = FontWeight(700),
                color = MateTripColors.Gray_11
            )
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(if(isOpen) Icons.fold_icon else Icons.enter_16_icon),
                contentDescription = "Navigation Icon"
            )
        }
        if (isOpen){
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ){
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "계획적",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.PLANNED.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.planned_badge,
                    onClick = {
                        if(TravelInterest.PLANNED.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.UNPLANNED.name)
                            travelPreferences.add(TravelInterest.PLANNED.name)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "무계획",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.UNPLANNED.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.unplanned_badge,
                    onClick = {
                        if(TravelInterest.UNPLANNED.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.PLANNED.name)
                            travelPreferences.add(TravelInterest.UNPLANNED.name)
                        }
                    }
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ){
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "공금",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.PUBLIC_MONEY.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.public_funds_badge,
                    onClick = {
                        if(TravelInterest.PUBLIC_MONEY.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.DUTCH_PAY.name)
                            travelPreferences.add(TravelInterest.PUBLIC_MONEY.name)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "더치페이",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.DUTCH_PAY.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.dutch_pay_badge,
                    onClick = {
                        if(TravelInterest.DUTCH_PAY.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.PUBLIC_MONEY.name)
                            travelPreferences.add(TravelInterest.DUTCH_PAY.name)
                        }
                    }
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ){
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "찾아본 곳",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.LOOKING_FOR.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.place_badge,
                    onClick = {
                        if(TravelInterest.LOOKING_FOR.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.DRAWN_TO.name)
                            travelPreferences.add(TravelInterest.LOOKING_FOR.name)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "끌리는 곳",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.DRAWN_TO.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.unplanned_badge,
                    onClick = {
                        if(TravelInterest.DRAWN_TO.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.LOOKING_FOR.name)
                            travelPreferences.add(TravelInterest.DRAWN_TO.name)
                        }
                    }
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ){
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "빨리빨리",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.QUICKLY.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.rush_badge,
                    onClick = {
                        if(TravelInterest.QUICKLY.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.LEISURELY.name)
                            travelPreferences.add(TravelInterest.QUICKLY.name)
                        }
                    }
                )
                Spacer(Modifier.width(10.dp))
                CustomClickableTag(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(7.dp),
                    tagName = "느긋하게",
                    fontSize = 14.sp,
                    selectedTextColor = Color.White,
                    notSelectedTextColor = MateTripColors.Blue_01,
                    selectedIconTint = Color.White,
                    notSelectedIconTint = MateTripColors.Blue_01,
                    isSelected = TravelInterest.LEISURELY.name in travelPreferences,
                    selectedColor = MateTripColors.Primary,
                    notSelectedColor = MateTripColors.Blue_04,
                    trailingIcon = Badges.leisurely_badge,
                    onClick = {
                        if(TravelInterest.LEISURELY.name !in travelPreferences){
                            travelPreferences.remove(TravelInterest.QUICKLY.name)
                            travelPreferences.add(TravelInterest.LEISURELY.name)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TravelStyleEdit(travelStyles: SnapshotStateList<String>){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "여행 스타일",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "맛집탐방",
                shape = RoundedCornerShape(size = 7.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.RESTAURANT_TOUR.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor =MateTripColors.Blue_04,
                trailingIcon = Badges.restaurant_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.RESTAURANT_TOUR.name in travelStyles){
                        travelStyles.remove(TravelStyle.RESTAURANT_TOUR.name)
                    } else {
                        travelStyles.add(TravelStyle.RESTAURANT_TOUR.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "인생샷",
                shape = RoundedCornerShape(size = 7.dp),
                fontSize = 14.sp,
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.LIFE_SHOT.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.image_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.LIFE_SHOT.name in travelStyles){
                        travelStyles.remove(TravelStyle.LIFE_SHOT.name)
                    } else {
                        travelStyles.add(TravelStyle.LIFE_SHOT.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "액티비티",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.ACTIVITY.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.activity_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.ACTIVITY.name in travelStyles){
                        travelStyles.remove(TravelStyle.ACTIVITY.name)
                    } else {
                        travelStyles.add(TravelStyle.ACTIVITY.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "드라이브",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.DRIVE.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.drive_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.DRIVE.name in travelStyles){
                        travelStyles.remove(TravelStyle.DRIVE.name)
                    } else {
                        travelStyles.add(TravelStyle.DRIVE.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "카페투어",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.CAFE_TOUR.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.cafe_tour_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.CAFE_TOUR.name in travelStyles){
                        travelStyles.remove(TravelStyle.CAFE_TOUR.name)
                    } else {
                        travelStyles.add(TravelStyle.CAFE_TOUR.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "힐링",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.HEALING.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.healing_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.HEALING.name in travelStyles){
                        travelStyles.remove(TravelStyle.HEALING.name)
                    } else {
                        travelStyles.add(TravelStyle.HEALING.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "문화예술",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.CULTURE_AND_ARTS.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.art_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.CULTURE_AND_ARTS.name in travelStyles){
                        travelStyles.remove(TravelStyle.CULTURE_AND_ARTS.name)
                    } else {
                        travelStyles.add(TravelStyle.CULTURE_AND_ARTS.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "패키지 여행",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = TravelStyle.PACKAGE_TOUR.name in travelStyles,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.package_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(TravelStyle.PACKAGE_TOUR.name in travelStyles){
                        travelStyles.remove(TravelStyle.PACKAGE_TOUR.name)
                    } else {
                        travelStyles.add(TravelStyle.PACKAGE_TOUR.name)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FoodPreferenceEdit(foodPreferences: SnapshotStateList<String>){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "음식 취향",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
        )
        Spacer(Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            CustomClickableTag(
                tagName = "육류",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.MEAT.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.meat_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.MEAT.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.MEAT.name)
                    } else {
                        foodPreferences.add(FoodPreference.MEAT.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "밥류",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.RICE.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.rice_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.RICE.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.RICE.name)
                    } else {
                        foodPreferences.add(FoodPreference.RICE.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "커피",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.COFFEE.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.coffee_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.COFFEE.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.COFFEE.name)
                    } else {
                        foodPreferences.add(FoodPreference.COFFEE.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "패스트 푸드",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.FAST_FOOD.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.fast_food_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.FAST_FOOD.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.FAST_FOOD.name)
                    } else {
                        foodPreferences.add(FoodPreference.FAST_FOOD.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "해산물",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.SEAFOOD.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.seafood_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.SEAFOOD.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.SEAFOOD.name)
                    } else {
                        foodPreferences.add(FoodPreference.SEAFOOD.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "채소",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.VEGETABLES.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.vegetarian_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.VEGETABLES.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.VEGETABLES.name)
                    } else {
                        foodPreferences.add(FoodPreference.VEGETABLES.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "디저트",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.DESSERT.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.dessert_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.DESSERT.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.DESSERT.name)
                    } else {
                        foodPreferences.add(FoodPreference.DESSERT.name)
                    }
                }
            )
            CustomClickableTag(
                tagName = "스트릿 푸드",
                fontSize = 14.sp,
                shape = RoundedCornerShape(size = 7.dp),
                selectedTextColor = Color.White,
                notSelectedTextColor = MateTripColors.Blue_01,
                isSelected = FoodPreference.STREET_FOOD.name in foodPreferences,
                selectedColor = MateTripColors.Primary,
                notSelectedColor = MateTripColors.Blue_04,
                trailingIcon = Badges.street_food_badge,
                selectedIconTint = Color.White,
                notSelectedIconTint = MateTripColors.Blue_01,
                onClick = {
                    if(FoodPreference.STREET_FOOD.name in foodPreferences){
                        foodPreferences.remove(FoodPreference.STREET_FOOD.name)
                    } else {
                        foodPreferences.add(FoodPreference.STREET_FOOD.name)
                    }
                }
            )
        }
    }
}

@Composable
private fun SNSEdit(
    snsLink: String,
    onUpdateSnsLink: (String) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "SNS",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(7.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .border(width = 1.dp, color = MateTripColors.Blue_03, shape = CircleShape),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Badges.instagram_outlined_badge),
                    contentDescription = "Instagram Badge",
                    tint = MateTripColors.Blue_01
                )
            }
            Spacer(Modifier.width(10.dp))
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                    .border(
                        width = 1.dp,
                        color = MateTripColors.Blue_03,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 13.dp),
                value = snsLink,
                onValueChange = onUpdateSnsLink,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Justify,
                    color = Color.Black,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                maxLines = 1,
            ){
                if(snsLink.isEmpty()){
                    Text(
                        text = "www.instagram.com/계정입력",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.Gray_06
                    )
                } else {
                    it()
                }
            }
        }
    }
}

@Composable
private fun MyImages(
    pictures: List<String>,
    onCameraClick :() -> Unit,
    onDeleteImage: (String) -> Unit,
){
    Log.d("TAG TEST", "My Images Component : ${pictures.toList()}")
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "나를 표현하는 이미지",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
            fontWeight = FontWeight(700),
            color = MateTripColors.Gray_11
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            item{
                Column(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = MateTripColors.Blue_04,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .clickable {
                            onCameraClick()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Icons.camera_icon),
                        contentDescription = "Camera Icon"
                    )
                    Text(
                        text = "${pictures.size}/4",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                        fontWeight = FontWeight(400),
                        color = MateTripColors.icon_color
                    )
                }
            }
            items(pictures){picture ->
                ImageLoadView(
                    shape = RoundedCornerShape(size = 10.dp),
                    size = 60.dp,
                    imageUrl = picture,
                    backgroundColor = MateTripColors.icon_loading_color,
                    isEnabledClose = true,
                    onCloseClick = { onDeleteImage(picture) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileUITest(){
    EditProfileScreen(
        navBack = {  },
//        uiState = EditProfileUiState.Success(
//            "",
//            "찬란한바닷가",
//            "",
//            2024,
//            "FEMALE",
//            listOf("PLANNED", "DUTCH_PAY", "LOOKING_FOR", "QUICKLY"),
//            listOf(TravelStyle.RESTAURANT_TOUR.name, TravelStyle.ACTIVITY.name, TravelStyle.DRIVE.name, TravelStyle.CAFE_TOUR.name, TravelStyle.HEALING.name),
//            listOf(FoodPreference.MEAT.name, FoodPreference.RICE.name, FoodPreference.COFFEE.name),
//            snsLink = null,
//            images = listOf("")
//        ),
        uiState = EditProfileUiState.Error,
        errState = ErrorState.AuthError(
            invalidTokenError = true,
            notFoundTokenError = false,
            generalError = Pair(false, "")
        ),
        images = remember{ mutableStateListOf("") },
        onEditClick = {a,b,c,d,e,f,g,h,i,j ->

        },
        onUploadImage = {

        },
        onDeleteImage = {},
        onUpdateProfileImg = {
            ""
        }
    )
}
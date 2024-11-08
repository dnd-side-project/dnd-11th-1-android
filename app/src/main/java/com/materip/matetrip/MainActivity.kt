package com.materip.matetrip

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.materip.core_designsystem.component.BackButtonTopAppBar
import com.materip.core_designsystem.component.BackButtonWithTitleTopAppBar
import com.materip.core_designsystem.component.MateTripBottomBar
import com.materip.core_designsystem.component.MateTripTopAppBar
import com.materip.core_model.navigation.LoginRoute
import com.materip.core_model.navigation.MyPageRoute
import com.materip.core_model.navigation.OnboardingRoute
import com.materip.core_model.navigation.SettingRoute
import com.materip.feature_home3.intent.PostBoardIntent
import com.materip.feature_home3.ui.component.FabButton
import com.materip.feature_home3.viewModel.PostBoardViewModel
import com.materip.feature_mypage.navigation.navigateToMyPageGraph
import com.materip.feature_mypage.navigation.navigateToSettingGraph
import com.materip.matetrip.navigation.Screen
import com.materip.matetrip.navigation.SetUpNavGraph
import com.materip.matetrip.navigation.navigateToBack
import com.materip.matetrip.ui.theme.MatetripTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val postBoardViewModel: PostBoardViewModel by viewModels()
    private val viewModel: AppViewModel by viewModels()
    private val requestMultiplePermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permission ->
        permission.entries.forEach{entry ->
            val permissionName = entry.key
            val isGranted = entry.value
            if(!isGranted){
                Toast.makeText(this, "$permissionName 권한이 거절되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        requestPermissions() //권한 요청 함수
        setContent {
            MatetripTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val startDestination = viewModel.getDestination()
                val navHome = remember<() -> Unit>{ { navController.navigate(Screen.Home.route) }}
                val onPostClick = remember<() -> Unit>{{navController.navigate(Screen.Post.route)}}
                Scaffold(
                    topBar = {
                        GetTopBar(
                            currentBackStack = currentBackStack,
                            navController = navController,
                            postBoardViewModel = postBoardViewModel,
                        )
                    },
                    floatingActionButton = {
                        FabButton(
                            currentBackStack = currentBackStack,
                            onPostClick = onPostClick,
                            modifier = Modifier.padding(end = 10.dp, bottom = 15.dp)
                        )
                    },
                    bottomBar = {
                        MateTripBottomBar(
                            currentBackStack = currentBackStack,
                            onHomeClick = navHome,
                            onMyPageClick = navController::navigateToMyPageGraph,
                            onSettingClick = navController::navigateToSettingGraph
                        )
                    },
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(Color.White)
                        ) {
                            SetUpNavGraph(
                                navController = navController,
                                startDestination = startDestination,
                                postBoardViewModel = postBoardViewModel
                            )
                        }
                    }
                )
            }
        }
    }
    private fun requestPermissions(){
        val permissionArray = if(Build.VERSION.SDK_INT >= 33){
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        val permissionsToRequest = permissionArray.filter{permission ->
            ContextCompat.checkSelfPermission(this@MainActivity, permission) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()){
            requestMultiplePermissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

@Composable
fun GetTopBar(
    currentBackStack: NavBackStackEntry?,
    navController: NavHostController,
    postBoardViewModel: PostBoardViewModel
) {
    val notHaveTopBar = listOf(
        LoginRoute.LoginRoute.name,
        OnboardingRoute.InputUserInfoRoute.name,
        OnboardingRoute.SelectTripStyleRoute.name,
        OnboardingRoute.SelectTripInterestRoute.name,
        OnboardingRoute.SelectFoodPreferenceRoute.name,
        MyPageRoute.MyPageRoute.name,
        MyPageRoute.EditProfileRoute.name,
        MyPageRoute.ProfileDescriptionRoute.name,
        MyPageRoute.Quiz100Route.name,
        MyPageRoute.PreviewRoute.name,
        MyPageRoute.ReviewEvaluationRoute.name,
        MyPageRoute.ReviewListRoute.name,
        MyPageRoute.ReviewDescriptionRoute.name,
        MyPageRoute.SendApplicationRoute.name,
        MyPageRoute.WriteReviewRoute.name,
        SettingRoute.SettingRoute.name,
        SettingRoute.AlarmSettingRoute.name,
        SettingRoute.AccountInfoRoute.name,
        SettingRoute.SMSVerificationRoute.name,
        SettingRoute.GetAuthCodeRoute.name,
        SettingRoute.DeleteAccountRoute.name,
        SettingRoute.DeleteAccountDoneRoute.name
    )

    val currentRoute = currentBackStack?.destination?.route

    when {
        // currentRoute가 null이 아니고, notHaveTopBar에 포함된 경로로 시작하는 경우
        currentRoute != null && notHaveTopBar.any { currentRoute.startsWith(it) } -> {
            // 상단바를 렌더링하지 않음
        }

        currentRoute == Screen.Home.route -> {
            MateTripTopAppBar(
                onNotificationClick = { navController.navigate(Screen.Notification.route) }
            )
        }

        // 타이틀, action이 필요한 상단바
        currentRoute == Screen.Post.route -> {
            val isFormValid by postBoardViewModel.isFormValid.collectAsState()
            BackButtonWithTitleTopAppBar(
                screenTitle = "동행 모집하기",
                onNavigateUp = { navController.navigateUp() },
                onPostClick = {
                    postBoardViewModel.handleIntent(PostBoardIntent.CreatePost)
                    navController.navigate(Screen.Home.route)
                },
                isPostButtonEnabled = isFormValid
            )

            LaunchedEffect(postBoardViewModel.createdBoardIds) {
                postBoardViewModel.createdBoardIds.collect { boardIds ->
                    val newBoardId = boardIds.lastOrNull()
                    newBoardId?.let {
                        navController.navigate(Screen.NavigateToPost.route + "/${it}")
                    }
                }
            }
        }

        // 타이틀 제목이 필요한 뒤로가기 상단바
        currentRoute?.startsWith(Screen.Form.route) == true -> {
            BackButtonTopAppBar(
                screenTitle = "동행 신청서 작성",
                onNavigateUp = navController::navigateToBack
            )
        }

        currentRoute == Screen.Notification.route -> {
            BackButtonTopAppBar(
                screenTitle = "알림",
                onNavigateUp = navController::navigateToBack
            )
        }

        // 타이틀 제목이 동행글을 올린 유저의 닉네임인 뒤로가기 상단바
        currentRoute?.startsWith(Screen.Profile.route) == true -> {
            // 상단바를 렌더링하지 않음, 화면에서 상단바 처리
        }

        currentRoute?.startsWith(Screen.NavigateToPost.route) == true -> {
            // 상단바를 렌더링하지 않음, 화면에서 상단바 처리
        }

        // 뒤로가기만 있는 상단바
        else -> {
            BackButtonTopAppBar(
                screenTitle = "",
                onNavigateUp = navController::navigateToBack
            )
        }
    }
}
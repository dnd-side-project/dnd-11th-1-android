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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.materip.core_designsystem.component.BackButtonTopAppBar
import com.materip.core_designsystem.component.BackButtonWithTitleTopAppBar
import com.materip.core_designsystem.component.BoardDetailTopAppBar
import com.materip.core_designsystem.component.MateTripBottomBar
import com.materip.core_designsystem.component.MateTripTopAppBar
import com.materip.feature_home3.ui.FabButton
import com.materip.feature_home3.viewModel.HomeViewModel
import com.materip.feature_home3.viewModel.PostBoardViewModel
import com.materip.feature_home3.viewModel.ProfileViewModel
import com.materip.feature_login.navigation.LoginRoute
import com.materip.feature_mypage.navigation.MyPageRoute
import com.materip.feature_mypage.navigation.SettingRoute
import com.materip.feature_mypage.navigation.navigateToMyPageGraph
import com.materip.feature_mypage.navigation.navigateToSettingGraph
import com.materip.feature_onboarding.navigation.OnboardingRoute
import com.materip.matetrip.navigation.Screen
import com.materip.matetrip.navigation.SetUpNavGraph
import com.materip.matetrip.navigation.navigateToBack
import com.materip.matetrip.ui.theme.MatetripTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: AppViewModel by viewModels()
    private val useBottomNavScreen = listOf(Screen.Home.route, MyPageRoute.MyPageRoute.name, SettingRoute.SettingRoute.name)
    private val requestMultiplePermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permission ->
        permission.entries.forEach{entry ->
            val permissionName = entry.key
            val isGranted = entry.value
            if(!isGranted){
                Toast.makeText(this, "${permissionName} 권한이 거절되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val useBottomNavScreen =
        listOf(Screen.Home.route, MyPageRoute.MyPageRoute.name, SettingRoute.SettingRoute.name)

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
                val currentRoute = currentBackStack?.destination?.route
                val startDestination = viewModel.getDestination()
                Scaffold(
                    topBar = {
                        GetTopBar(
                            currentRoute = currentRoute,
                            navController = navController
                        )
                    },
                    floatingActionButton = {
                        if (currentRoute == Screen.Home.route) {
                            FabButton(
                                onPostClick = { navController.navigate(Screen.Post.route) },
                                modifier = Modifier.padding(end = 20.dp, bottom = 35.dp)
                            )
                        }
                    },
                    bottomBar = {
                        if (currentRoute in useBottomNavScreen) {
                            MateTripBottomBar(
                                currentRoute = currentRoute ?: "home",
                                onHomeClick = { navController.navigate(Screen.Home.route) },
                                onMyPageClick = navController::navigateToMyPageGraph,
                                onSettingClick = navController::navigateToSettingGraph
                            )
                        }
                    },
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        SetUpNavGraph(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }
                }
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
    currentRoute: String?,
    navController: NavHostController
) {
    val viewModel: PostBoardViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()

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
        SettingRoute.GetAuthCodeRoute.name
    )

    when (currentRoute) {
        Screen.Home.route -> {
            MateTripTopAppBar(
                onNotificationClick = { navController.navigate(Screen.Notification.route) }
            )
        }

        // 타이틀, action이 필요한 상단바
        Screen.Post.route -> {
            BackButtonWithTitleTopAppBar(
                screenTitle = "동행 모집하기",
                onNavigateUp = { navController.navigateUp() },
                onPostClick = { viewModel.createPost(viewModel.toBoardRequestDto()) }
            )

            LaunchedEffect(viewModel.createdBoardIds) {
                viewModel.createdBoardIds.collect { boardIds ->
                    val newBoardId = boardIds.lastOrNull()
                    newBoardId?.let {
                        navController.navigate(Screen.NavigateToPost.route + "/${it}")
                    }
                }
            }
        }

        // 타이틀 제목이 필요한 뒤로가기 상단바
        Screen.Form.route -> {
            BackButtonTopAppBar(
                screenTitle = "동행 신청서 작성",
                onNavigateUp = navController::navigateToBack
            )
        }

        // 타이틀 제목이 동행글을 올린 유저의 닉네임인 뒤로가기 상단바
        Screen.Profile.route -> {
            val boardId = currentRoute.substringAfterLast("/").toIntOrNull() ?: 0
            val userId = profileViewModel.getUserId(boardId)
            val userNickname = profileViewModel.getNickname(userId)
            BackButtonTopAppBar(
                screenTitle = userNickname,
                onNavigateUp = navController::navigateToBack
            )
        }

        //별개 top bar 보유
        in notHaveTopBar -> {

        }

        // 뒤로가기, 액션이 필요한 상단바
        Screen.NavigateToPost.route -> {
            BoardDetailTopAppBar(
                onNavigateUp = navController::navigateToBack,
                showDialogState = homeViewModel.showDialogState,
            )
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
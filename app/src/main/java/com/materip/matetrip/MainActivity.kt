package com.materip.matetrip

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.materip.feature_home3.ui.FabButton
import com.materip.feature_home3.viewModel.HomeViewModel
import com.materip.feature_mypage.navigation.MyPageRoute
import com.materip.feature_mypage.navigation.SettingRoute
import com.materip.core_designsystem.component.BackButtonTopAppBar
import com.materip.core_designsystem.component.BackButtonWithTitleTopAppBar
import com.materip.core_designsystem.component.MateTripBottomBar
import com.materip.core_designsystem.component.MateTripTopAppBar
import com.materip.feature_login.navigation.LoginRoute
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
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
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
                        if(currentRoute in useBottomNavScreen){
                            MateTripBottomBar(
                                onHomeClick = { navController.navigate(Screen.Home.route) },
                                onMyPageClick = { navController.navigate(Screen.MyPage.route) },
                                onSettingClick = { navController.navigate(Screen.Setting.route) }
                            )
                        }
                    },
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ){
                        SetUpNavGraph(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GetTopBar(
    currentRoute: String?,
    navController: NavHostController
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val notHaveTopBar = listOf(
        LoginRoute.LoginRoute.name,
        OnboardingRoute.InputUserInfoRoute.name,
        OnboardingRoute.SelectTripStyleRoute.name,
        OnboardingRoute.SelectTripInterestRoute.name,
        OnboardingRoute.SelectFoodPreferenceRoute.name,
        MyPageRoute.MyPageRoute.name,
        MyPageRoute.EditProfileRoute.name,
        MyPageRoute.ReviewRoute.name,
        MyPageRoute.ReviewListRoute.name,
        MyPageRoute.ReviewDescriptionRoute.name,
        MyPageRoute.PreviewRoute.name,
        MyPageRoute.SendApplicationRoute.name,
        MyPageRoute.Quiz100Route.name,
        MyPageRoute.ProfileDescriptionRoute.name,
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
                onPostClick = {
                    val boardIdDto = viewModel.createPost(viewModel.toBoardRequestDto())
                    navController.navigate(Screen.NavigateToPost.route + "/${boardIdDto.boardId}")
                }
            )
        }

        // 타이틀 제목이 필요한 뒤로가기 상단바
        Screen.Form.route -> {
            BackButtonTopAppBar(
                screenTitle = "동행 신청서 작성",
                onNavigateUp = navController::navigateToBack
            )
        }

        //별개 top bar 보유
        in notHaveTopBar ->{

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
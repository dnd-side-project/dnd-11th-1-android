package com.materip.feature_mypage.screen.Setting

import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.materip.core_designsystem.icon.Icons
import com.materip.core_designsystem.theme.MateTripColors
import com.materip.feature_mypage.BuildConfig

@Composable
fun SettingRoute(
    navAccountInfo: () -> Unit,
    navAlarmSetting: () -> Unit,
){
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){}
    val navLicenses = {
        launcher.launch(Intent(context, OssLicensesMenuActivity::class.java))
        OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스")
    }
    SettingScreen(
        navAccountInfo = navAccountInfo,
        navAlarmSetting = navAlarmSetting,
        navLicenses = navLicenses
    )
}

@Composable
fun SettingScreen(
    navAccountInfo: () -> Unit,
    navAlarmSetting: () -> Unit,
    navLicenses: () -> Unit
){
    var showWebView by remember{mutableStateOf(false)}
    BackHandler(
        enabled = true,
        onBack = {if (showWebView) showWebView = false}
    )
    if (showWebView){
        CustomWebView(url = BuildConfig.PRIVACY_URL)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 20.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "설정",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(900)
                )
            }
            Spacer(Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { navAccountInfo() },
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Icons.my_page_icon),
                    contentDescription = "Account Info IC",
                    tint = MateTripColors.Primary
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "계정 정보",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500)
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(Icons.navigate_next_icon),
                    contentDescription = "Next Navigation"
                )
            }
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { navAlarmSetting() },
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Icons.notification_icon),
                    contentDescription = "Alarm Setting IC",
                    tint = MateTripColors.Primary
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "알림 설정",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    fontWeight = FontWeight(500)
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(Icons.navigate_next_icon),
                    contentDescription = "Next Navigation"
                )
            }
            Spacer(Modifier.height(20.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MateTripColors.Gray_03)
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable{showWebView = true},
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = "개인정보처리방침",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    color = MateTripColors.Gray_11
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(com.materip.core_designsystem.R.drawable.navigate_next_24px),
                    contentDescription = "Navigation Button"
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { navLicenses() },
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = "오픈소스 라이센스",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(com.materip.core_designsystem.R.font.noto_sans_kr)),
                    color = MateTripColors.Gray_11
                )
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(com.materip.core_designsystem.R.drawable.navigate_next_24px),
                    contentDescription = "Navigation Button"
                )
            }
        }
    }
}

@Composable
private fun CustomWebView(url: String) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WebView(context).apply{
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = object: WebViewClient(){

                }

                loadUrl(url)
            }
        }
    )
}
@Preview
@Composable
private fun SettingUITest(){
    SettingScreen(
        navAlarmSetting = {},
        navAccountInfo = {},
        navLicenses = {}
    )
}
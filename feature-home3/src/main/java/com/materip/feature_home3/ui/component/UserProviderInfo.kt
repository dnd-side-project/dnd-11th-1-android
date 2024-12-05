package com.materip.feature_home3.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.materip.core_designsystem.icon.Badges

@Composable
fun UserProviderInfo() {
    /** message 연결 */
    /** kakao 연결 */
    Row(
        modifier = Modifier
            .width(60.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        /** kakao 연결 */
        IconButton(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape),
            onClick = { /** kakao 연결 */ },
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Badges.kakaotalk_badge),
                contentDescription = "Kakao Badge"
            )
        }
        Spacer(Modifier.width(4.dp))
        /** message 연결 */
        /** message 연결 */
        /** message 연결 */
        IconButton(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape),
            onClick = { /** message 연결 */ },
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Badges.sms_badge),
                contentDescription = "Message Badge"
            )
        }
    }
}
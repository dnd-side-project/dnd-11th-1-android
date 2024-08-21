package com.materip.feature_home3.intent

sealed class NotificationIntent {
    data class FetchNotifications(val userId: Int) : NotificationIntent()
}
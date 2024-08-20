package com.materip.feature_home.intent

sealed class NotificationIntent {
    data class FetchNotifications(val userId: Int) : NotificationIntent()
}
package com.materip.feature_home3.intent

sealed class NotificationIntent {
    data object FetchNotifications : NotificationIntent()
}
package com.materip.feature_home.intent

sealed class NotificationIntent {
    data object LoadNotifications : NotificationIntent()
}
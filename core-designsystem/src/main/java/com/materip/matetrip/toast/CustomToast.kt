package com.materip.matetrip.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class CustomToast(context: Context): Toast(context) {
    @Composable
    fun MakeErrorText(
        message: String,
        icon: Int,
        duration: Int = LENGTH_SHORT
    ){
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent{
            CustomToastUtil.SetErrorView(
                message = message,
                resourceIcon = icon
            )
        }

        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)

        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)

        this.duration = duration
        this.view = views
        this.show()
    }

    @Composable
    fun MakeCommonToastView(
        message: String,
        icon: Int,
        duration: Int = LENGTH_SHORT
    ){
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent{
            CustomToastUtil.SetCommonToastView(
                message = message,
                resourceIcon = icon
            )
        }

        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)

        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)

        this.duration = duration
        this.view = views
        this.show()
    }
}
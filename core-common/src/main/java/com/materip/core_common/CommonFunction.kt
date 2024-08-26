package com.materip.core_common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

//uri를 file로 변환하는 함수
fun transformToFile(
    context: Context,
    uri: Uri
): File? {
    val contentResolver = context.contentResolver

    val filePath = (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
    val file = File(filePath)

    try{
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val outputStream = FileOutputStream(file)

        val buf = ByteArray(1024)
        var len: Int
        while(inputStream.read(buf).also{len = it} > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()
    } catch (ignore: Exception){
        return null
    }
    return file
}

//권한 확인 함수
fun checkPermission(context: Context, type: String): Boolean{
    val permission =
        if (type == "notification" && Build.VERSION.SDK_INT >= 33) Manifest.permission.POST_NOTIFICATIONS
        else if (type == "gallery" && Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_IMAGES
        else if (type == "gallery" && Build.VERSION.SDK_INT < 33) Manifest.permission.READ_EXTERNAL_STORAGE
        else return true
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}
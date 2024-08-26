package com.materip.core_common

import android.content.Context
import android.net.Uri
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


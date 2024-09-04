package com.materip.core_common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.Gender
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region
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

fun PreferredGender.toDisplayString(): String {
    return when (this) {
        PreferredGender.SAME -> "동일 성별"
        PreferredGender.ANY -> "상관없음"
    }
}

fun Gender.toDisplayString(): String {
    return when (this) {
        Gender.FEMALE -> "여성"
        Gender.MALE -> "남성"
    }
}

fun PreferredAge.toDisplayString(): String {
    return when (this) {
        PreferredAge.SAME -> "동일 나이대"
        PreferredAge.ANY -> "상관없음"
    }
}

fun Region.toDisplayString(): String {
    return when (this) {
        Region.SEOUL -> "서울"
        Region.GYEONGGI_INCHEON -> "경기/인천"
        Region.CHUNGCHEONG_DAEJON_SEJONG -> "충청/대전/세종"
        Region.GANGWON -> "강원"
        Region.JEOLLA_GWANGJU -> "전라/광주"
        Region.GYEONGSANG_DAEGU_ULSAN -> "경상/대구/울산"
        Region.BUSAN -> "부산"
        Region.JEJU -> "제주"
    }
}

fun Category.toDisplayString(): String {
    return when (this) {
        Category.FULL -> "전체 동행"
        Category.PART -> "부분 동행"
        Category.LODGING -> "숙박 동행"
        Category.TOUR -> "투어 동행"
        Category.MEAL -> "식사 공유"
    }
}
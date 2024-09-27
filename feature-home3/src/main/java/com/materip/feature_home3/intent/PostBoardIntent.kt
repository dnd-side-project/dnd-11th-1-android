package com.materip.feature_home3.intent

import android.content.Context
import android.net.Uri
import com.materip.core_model.ui_model.BoardStatus
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region
import java.time.LocalDateTime

sealed class PostBoardIntent {
    data class UpdateTitle(val title: String) : PostBoardIntent()
    data class UpdateContent(val content: String) : PostBoardIntent()
    data class UpdateTagNames(val tagNames: List<String>) : PostBoardIntent()
    data class UpdateRegion(val region: Region) : PostBoardIntent()
    data class UpdateStartDate(val startDate: LocalDateTime) : PostBoardIntent()
    data class UpdateEndDate(val endDate: LocalDateTime) : PostBoardIntent()
    data class UpdateCategory(val category: List<Category>) : PostBoardIntent()
    data class UpdateAge(val preferredAge: PreferredAge) : PostBoardIntent()
    data class UpdateGender(val preferredGender: PreferredGender) : PostBoardIntent()
    data class UpdateCapacity(val capacity: Int) : PostBoardIntent()
    data class UpdateBoardStatus(val boardStatus: BoardStatus) : PostBoardIntent()
    data class UpdateImageUris(val imageUris: List<String>) : PostBoardIntent()
    data object CreatePost : PostBoardIntent()
    data class UploadImage(val context: Context, val uri: Uri?) : PostBoardIntent()
    data class DeleteImage(val imagePath: String) : PostBoardIntent()
    data class TransformToFile(val context: Context, val uri: Uri) : PostBoardIntent()
}
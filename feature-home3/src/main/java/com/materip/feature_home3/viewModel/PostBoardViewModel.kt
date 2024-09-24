package com.materip.feature_home3.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.ui_model.BoardStatus
import com.materip.core_model.ui_model.Category
import com.materip.core_model.ui_model.PreferredAge
import com.materip.core_model.ui_model.PreferredGender
import com.materip.core_model.ui_model.Region
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.core_repository.repository.image_repository.ImageRepository
import com.materip.feature_home3.intent.PostBoardIntent
import com.materip.feature_home3.state.ImageUploadState
import com.materip.feature_home3.state.PostBoardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class PostBoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostBoardUiState>(PostBoardUiState.Initial)
    val uiState: StateFlow<PostBoardUiState> = _uiState

    private val _createdBoardIds = MutableStateFlow<List<Int>>(emptyList())
    val createdBoardIds: StateFlow<List<Int>> = _createdBoardIds

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState: StateFlow<ImageUploadState> = _imageUploadState

    private val _generalError = MutableStateFlow<Pair<Boolean, String>>(Pair(false, ""))
    val generalError: StateFlow<Pair<Boolean, String>> = _generalError

    val title = MutableStateFlow("")
    val content = MutableStateFlow("")
    val tagNames = MutableStateFlow(listOf<String>())
    val preferredGender = MutableStateFlow<PreferredGender?>(null)
    val preferredAge = MutableStateFlow<PreferredAge?>(null)
    val category = MutableStateFlow<List<Category>>(emptyList())
    val region = MutableStateFlow<Region?>(null)
    val startDate = MutableStateFlow("")
    val endDate = MutableStateFlow("")
    val capacity = MutableStateFlow(2)
    val imageUris = MutableStateFlow(listOf<String>())
    private val boardStatus = MutableStateFlow(BoardStatus.RECRUITING)

    fun handleIntent(intent: PostBoardIntent) {
        when (intent) {
            is PostBoardIntent.UpdateTitle -> updateTitle(intent.title)
            is PostBoardIntent.UpdateContent -> updateContent(intent.content)
            is PostBoardIntent.UpdateTagNames -> updateTags(intent.tagNames)
            is PostBoardIntent.UpdateRegion -> updateRegion(intent.region)
            is PostBoardIntent.UpdateStartDate -> updateStartDate(intent.startDate)
            is PostBoardIntent.UpdateEndDate -> updateEndDate(intent.endDate)
            is PostBoardIntent.UpdateCategory -> updateCategory(intent.category)
            is PostBoardIntent.UpdateAge -> updateAge(intent.preferredAge)
            is PostBoardIntent.UpdateGender -> updateGender(intent.preferredGender)
            is PostBoardIntent.UpdateCapacity -> updateCapacity(intent.capacity)
            is PostBoardIntent.UpdateImageUris -> updateImageUris(intent.imageUris)
            is PostBoardIntent.UpdateBoardStatus -> updateBoardStatus(intent.boardStatus)
            is PostBoardIntent.CreatePost -> createPost(toBoardRequestDto())
            is PostBoardIntent.UploadImage -> uploadImage(intent.context, intent.uri)
            is PostBoardIntent.DeleteImage -> deleteImage(intent.imagePath)
            is PostBoardIntent.TransformToFile -> transformToFile(intent.context, intent.uri)
        }
    }

    private fun validateFields(): Boolean {
        return title.value.isNotEmpty() &&
                content.value.isNotEmpty() &&
                tagNames.value.isNotEmpty() &&
                region.value != null &&
                startDate.value.isNotEmpty() &&
                endDate.value.isNotEmpty() &&
                preferredAge.value != null &&
                preferredGender.value != null &&
                category.value.isNotEmpty()
    }

    private fun uploadImage(context: Context, uri: Uri?) {
        viewModelScope.launch {
            _imageUploadState.value = ImageUploadState.Loading
            if (uri == null) {
                _generalError.value = Pair(true, "파일 경로를 찾을 수 없습니다.")
                _imageUploadState.value = ImageUploadState.Error("파일 경로를 찾을 수 없습니다.")
                return@launch
            }
            val file = transformToFile(context, uri)
            if (file == null) {
                _generalError.value = Pair(true, "파일 경로를 찾을 수 없습니다.")
                _imageUploadState.value = ImageUploadState.Error("파일 경로를 찾을 수 없습니다.")
                return@launch
            }
            val result = imageRepository.postImage(file)
            val error = result.error
            if (error != null) {
                when (error.status) {
                    401 -> _imageUploadState.value = ImageUploadState.Error("인증 오류")
                    404 -> _imageUploadState.value = ImageUploadState.Error("리소스를 찾을 수 없음")
                    else -> _imageUploadState.value = ImageUploadState.Error(error.message)
                }
                _generalError.value = Pair(true, error.message)
            } else {
                val imagePath = result.data?.path ?: ""
                imageUris.value += imagePath
                _imageUploadState.value = ImageUploadState.Success(imagePath)
            }
        }
    }

    private fun transformToFile(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val filePath =
            (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
        val file = File(filePath)

        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (ignore: Exception) {
            return null
        }
        return file
    }

    private fun deleteImage(path: String) {
        imageUris.value = imageUris.value.filter { it != path }
    }

    private fun updateTitle(newTitle: String) {
        title.value = newTitle
    }

    private fun updateContent(newContent: String) {
        content.value = newContent
    }

    private fun updateTags(newTags: List<String>) {
        tagNames.value = newTags
    }

    private fun updateGender(newGender: PreferredGender) {
        preferredGender.value = newGender
    }

    private fun updateAge(newAge: PreferredAge) {
        preferredAge.value = newAge
    }

    private fun updateCategory(newCategory: List<Category>) {
        category.value = newCategory
    }

    private fun updateRegion(newRegion: Region) {
        region.value = newRegion
    }

    private fun updateStartDate(newStartDate: String) {
        startDate.value = newStartDate
    }

    private fun updateEndDate(newEndDate: String) {
        endDate.value = newEndDate
    }

    private fun updateCapacity(newCapacity: Int) {
        capacity.value = newCapacity
    }

    private fun updateImageUris(newImageUris: List<String>) {
        imageUris.value = newImageUris
    }

    private fun updateBoardStatus(newBoardStatus: BoardStatus) {
        boardStatus.value = newBoardStatus
    }

    private fun createPost(boardRequestDto: BoardRequestDto) {
        viewModelScope.launch {
            _uiState.value = PostBoardUiState.Loading

            Log.d("PostBoardViewModel", "Creating post with data: $boardRequestDto")

            try {
                val result = boardRepository.postBoard(boardRequestDto)
                Log.d("PostBoardViewModel", "API response: $result")

                when {
                    result.data != null -> {
                        val boardIdDto = result.data
                        _createdBoardIds.value += boardIdDto!!.boardId
                        _uiState.value = PostBoardUiState.Success(boardIdDto)
                        Log.d(
                            "PostBoardViewModel",
                            "Post created successfully with ID: ${boardIdDto.boardId}"
                        )
                    }

                    result.error != null -> {
                        val errorMessage = result.error!!.message
                        _uiState.value = PostBoardUiState.Error(errorMessage)
                        Log.e("PostBoardViewModel", "Error creating post: $errorMessage")
                    }

                    else -> {
                        _uiState.value = PostBoardUiState.Error("알 수 없는 오류가 발생했습니다.")
                        Log.e("PostBoardViewModel", "Unknown error occurred while creating post")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = PostBoardUiState.Error("네트워크 오류: ${e.message}")
                Log.e("PostBoardViewModel", "Exception while creating post", e)
            }
        }
    }


    fun toBoardRequestDto(): BoardRequestDto {
        return BoardRequestDto(
            title = title.value,
            content = content.value,
            region = region.value ?: Region.SEOUL,
            startDate = startDate.value,
            endDate = endDate.value,
            capacity = capacity.value,
            boardStatus = boardStatus.value,
            categories = category.value,
            preferredAge = preferredAge.value ?: PreferredAge.ANY,
            preferredGender = preferredGender.value ?: PreferredGender.ANY,
            imageUrls = imageUris.value,
            tagNames = tagNames.value
        )
    }
}
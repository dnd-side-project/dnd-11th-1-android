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

    private val _generalError = MutableStateFlow(Pair(false, ""))
    val generalError: StateFlow<Pair<Boolean, String>> = _generalError

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    private val _tagNames = MutableStateFlow<List<String>>(emptyList())
    val tagNames: StateFlow<List<String>> = _tagNames

    private val _preferredGender = MutableStateFlow<PreferredGender?>(null)
    val preferredGender: StateFlow<PreferredGender?> = _preferredGender

    private val _preferredAge = MutableStateFlow<PreferredAge?>(null)
    val preferredAge: StateFlow<PreferredAge?> = _preferredAge

    private val _category = MutableStateFlow<List<Category>>(emptyList())
    val category: StateFlow<List<Category>> = _category

    private val _region = MutableStateFlow<Region?>(null)
    val region: StateFlow<Region?> = _region

    private val _startDate = MutableStateFlow("")
    val startDate: StateFlow<String> = _startDate

    private val _endDate = MutableStateFlow("")
    val endDate: StateFlow<String> = _endDate

    private val _capacity = MutableStateFlow(2)
    val capacity: StateFlow<Int> = _capacity

    private val _imageUris = MutableStateFlow<List<String>>(emptyList())
    val imageUris: StateFlow<List<String>> = _imageUris

    private val _boardStatus = MutableStateFlow(BoardStatus.RECRUITING)

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
            is PostBoardIntent.CreatePost -> createPost()
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
                _imageUris.value += imagePath
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
        _imageUris.value = _imageUris.value.filter { it != path }
    }

    private fun updateTitle(newTitle: String) {
        _title.value = newTitle
        Log.d("PostBoardViewModel", "Title updated: ${_title.value}")
    }

    private fun updateContent(newContent: String) {
        _content.value = newContent
        Log.d("PostBoardViewModel", "Content updated: ${_content.value}")
    }

    private fun updateTags(newTags: List<String>) {
        _tagNames.value = newTags
        Log.d("PostBoardViewModel", "Tags updated: ${_tagNames.value}")
    }

    private fun updateGender(newGender: PreferredGender) {
        _preferredGender.value = newGender
        Log.d("PostBoardViewModel", "Gender updated: ${_preferredGender.value}")
    }

    private fun updateAge(newAge: PreferredAge) {
        _preferredAge.value = newAge
        Log.d("PostBoardViewModel", "Age updated: ${_preferredAge.value}")
    }

    private fun updateCategory(newCategory: List<Category>) {
        _category.value = newCategory
        Log.d("PostBoardViewModel", "Category updated: ${_category.value}")
    }

    private fun updateRegion(newRegion: Region) {
        _region.value = newRegion
        Log.d("PostBoardViewModel", "Region updated: ${_region.value}")
    }

    private fun updateStartDate(newStartDate: String) {
        _startDate.value = newStartDate
        Log.d("PostBoardViewModel", "Start date updated: ${_startDate.value}")
    }

    private fun updateEndDate(newEndDate: String) {
        _endDate.value = newEndDate
        Log.d("PostBoardViewModel", "End date updated: ${_endDate.value}")
    }

    private fun updateCapacity(newCapacity: Int) {
        _capacity.value = newCapacity
        Log.d("PostBoardViewModel", "Capacity updated: ${_capacity.value}")
    }

    private fun updateImageUris(newImageUris: List<String>) {
        _imageUris.value = newImageUris
        Log.d("PostBoardViewModel", "Image URIs updated: ${_imageUris.value}")
    }

    private fun updateBoardStatus(newBoardStatus: BoardStatus) {
        _boardStatus.value = newBoardStatus
        Log.d("PostBoardViewModel", "Board status updated: ${_boardStatus.value}")
    }

    private fun createPost() {
        viewModelScope.launch {
            _uiState.value = PostBoardUiState.Loading

            Log.d("PostBoardViewModel", "Creating post...")

            val boardRequestDto = toBoardRequestDto()

            Log.d("PostBoardViewModel", "BoardRequestDto created: $boardRequestDto")

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


    private fun toBoardRequestDto(): BoardRequestDto {
        return BoardRequestDto(
            title = _title.value,
            content = _content.value,
            region = _region.value ?: Region.SEOUL,
            startDate = _startDate.value,
            endDate = _endDate.value,
            capacity = _capacity.value,
            boardStatus = _boardStatus.value,
            categories = _category.value,
            preferredAge = _preferredAge.value ?: PreferredAge.ANY,
            preferredGender = _preferredGender.value ?: PreferredGender.ANY,
            imageUrls = _imageUris.value,
            tagNames = _tagNames.value
        ).also {
            Log.d("PostBoardViewModel", "BoardRequestDto created: $it")
        }
    }
}
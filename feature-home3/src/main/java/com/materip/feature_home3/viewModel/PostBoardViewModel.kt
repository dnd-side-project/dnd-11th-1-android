package com.materip.feature_home3.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.accompany_board.create.BoardStatus
import com.materip.core_model.accompany_board.create.Category
import com.materip.core_model.accompany_board.create.PreferredAge
import com.materip.core_model.accompany_board.create.PreferredGender
import com.materip.core_model.accompany_board.create.Region
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

    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _tagNames = MutableStateFlow(listOf<String>())
    private val _preferredGender = MutableStateFlow(PreferredGender.ANY)
    private val _preferredAge = MutableStateFlow(PreferredAge.ANY)
    private val _category = MutableStateFlow<List<Category>>(emptyList())
    private val _region = MutableStateFlow(Region.SEOUL)
    private val _startDate = MutableStateFlow("")
    private val _endDate = MutableStateFlow("")
    private val _capacity = MutableStateFlow(2)
    private val _imageUris = MutableStateFlow(listOf<String>())
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
        is PostBoardIntent.CreatePost -> createPost(toBoardRequestDto())
        is PostBoardIntent.UploadImage -> uploadImage(intent.context, intent.uri)
        is PostBoardIntent.DeleteImage -> deleteImage(intent.imagePath)
        is PostBoardIntent.TransformToFile -> transformToFile(intent.context, intent.uri)
    }
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
        val filePath = (context.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
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
    }

    private fun updateContent(newContent: String) {
        _content.value = newContent
    }

    private fun updateTags(newTags: List<String>) {
        _tagNames.value = newTags
    }

    private fun updateGender(newGender: PreferredGender) {
        _preferredGender.value = newGender
    }

    private fun updateAge(newAge: PreferredAge) {
        _preferredAge.value = newAge
    }

    private fun updateCategory(newCategory: List<Category>) {
        _category.value = newCategory
    }

    private fun updateRegion(newRegion: Region) {
        _region.value = newRegion
    }

    private fun updateStartDate(newStartDate: String) {
        _startDate.value = newStartDate
    }

    private fun updateEndDate(newEndDate: String) {
        _endDate.value = newEndDate
    }

    private fun updateCapacity(newCapacity: Int) {
        _capacity.value = newCapacity
    }

    private fun updateImageUris(newImageUris: List<String>) {
        _imageUris.value = newImageUris
    }

    private fun updateBoardStatus(newBoardStatus: BoardStatus) {
        _boardStatus.value = newBoardStatus
    }

    fun createPost(boardRequestDto: BoardRequestDto) {
        viewModelScope.launch {
            _uiState.value = PostBoardUiState.Loading

            val result = boardRepository.postBoard(boardRequestDto)
            val boardIdDto = result.data

            _uiState.value = if (boardIdDto != null) {
                _createdBoardIds.value += boardIdDto.boardId
                PostBoardUiState.Success
            } else {
                PostBoardUiState.Error(result.error?.message ?: "게시글 작성에 실패했습니다.")
            }
        }
    }

    fun toBoardRequestDto(): BoardRequestDto {
        return BoardRequestDto(
            title = _title.value,
            content = _content.value,
            region = _region.value,
            startDate = _startDate.value,
            endDate = _endDate.value,
            capacity = _capacity.value,
            boardStatus = _boardStatus.value,
            categories = _category.value,
            preferredAge = _preferredAge.value,
            preferredGender = _preferredGender.value,
            imageUrls = _imageUris.value,
            tagNames = _tagNames.value
        )
    }
}
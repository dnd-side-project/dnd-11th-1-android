package com.materip.feature_home3.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.materip.core_model.accompany_board.create.BoardFormState
import com.materip.core_model.accompany_board.create.BoardRequestDto
import com.materip.core_model.ui_model.Region
import com.materip.core_repository.repository.home_repository.BoardRepository
import com.materip.core_repository.repository.image_repository.ImageRepository
import com.materip.feature_home3.intent.PostBoardIntent
import com.materip.feature_home3.state.ImageUploadState
import com.materip.feature_home3.state.PostBoardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    private val _formState = MutableStateFlow(BoardFormState())
    val formState: StateFlow<BoardFormState> = _formState

    private val _createdBoardIds = MutableStateFlow<List<Int>>(emptyList())
    val createdBoardIds: StateFlow<List<Int>> = _createdBoardIds

    private val _imageUploadState = MutableStateFlow<ImageUploadState>(ImageUploadState.Idle)
    val imageUploadState: StateFlow<ImageUploadState> = _imageUploadState

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

    private val _generalError = MutableStateFlow(Pair(false, ""))
    val generalError: StateFlow<Pair<Boolean, String>> = _generalError

    private var boardRequest: BoardRequestDto? = null

    private val _serverImageUrls = MutableStateFlow<List<String>>(emptyList())
    val serverImageUrls: StateFlow<List<String>> = _serverImageUrls

    fun handleIntent(intent: PostBoardIntent) {
        when (intent) {
            is PostBoardIntent.UpdateTitle ->  updateField { it.copy(title = intent.title) }
            is PostBoardIntent.UpdateContent -> updateField { it.copy(content = intent.content) }
            is PostBoardIntent.UpdateGender -> updateField { it.copy(preferredGender = intent.preferredGender) }
            is PostBoardIntent.UpdateAge -> updateField { it.copy(preferredAge = intent.preferredAge) }
            is PostBoardIntent.UpdateTagNames -> updateField { it.copy(tagNames = intent.tagNames) }
            is PostBoardIntent.UpdateCategory -> updateField { it.copy(category = intent.category) }
            is PostBoardIntent.UpdateRegion -> updateField { it.copy(region = intent.region) }
            is PostBoardIntent.UpdateStartDate -> updateField { it.copy(startDate = intent.startDate) }
            is PostBoardIntent.UpdateEndDate -> updateField { it.copy(endDate = intent.endDate) }
            is PostBoardIntent.UpdateCapacity -> updateField { it.copy(capacity = intent.capacity) }
            is PostBoardIntent.UpdateImageUris -> updateField { it.copy(imageUris = intent.imageUris) }
            is PostBoardIntent.UpdateBoardStatus -> updateField { it.copy(boardStatus = intent.boardStatus) }
            is PostBoardIntent.CreatePost -> createPost()
            is PostBoardIntent.UploadImage -> uploadImage(intent.context, intent.uri)
            is PostBoardIntent.DeleteImage -> deleteImage(intent.imagePath)
            is PostBoardIntent.TransformToFile -> transformToFile(intent.context, intent.uri)
            is PostBoardIntent.ResetGeneralError -> resetGeneralError()
        }
    }

    private fun resetGeneralError() { _generalError.value = Pair(false, "") }

    private fun updateField(update: (BoardFormState) -> BoardFormState) {
        _formState.update(update)

        val currentFormState = formState.value
        boardRequest = BoardRequestDto(
            title = currentFormState.title,
            content = currentFormState.content,
            region = currentFormState.region ?: Region.SEOUL,
            startDate = currentFormState.startDate.toString(),
            endDate = currentFormState.endDate.toString(),
            capacity = currentFormState.capacity,
            boardStatus = currentFormState.boardStatus,
            categories = currentFormState.category.map { it },
            preferredAge = currentFormState.preferredAge,
            preferredGender = currentFormState.preferredGender,
            imageUrls = _serverImageUrls.value,
            tagNames = currentFormState.tagNames
        )
        Log.d("PostBoardViewModel", "updateField DTO created: $boardRequest")
        _isFormValid.value = isFormValid()
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
                val imagePath = result.data!!.path
                _serverImageUrls.value += imagePath
                _imageUploadState.value = ImageUploadState.Success(imagePath)
                Log.d("PostBoardViewModel", "Image uploaded successfully: $imagePath")
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
        _serverImageUrls.value = _serverImageUrls.value.filter { it != path }
        updateField { it.copy(imageUris = _serverImageUrls.value) }
        Log.d("PostBoardViewModel", "Image deleted: $path")
    }

    private fun isFormValid(): Boolean {
        val form = _formState.value
        return form.title.isNotBlank() &&
                form.content.isNotBlank() &&
                form.tagNames.isNotEmpty() &&
                form.region != null &&
                form.startDate != null &&
                form.endDate != null &&
                form.category.isNotEmpty()
    }

    private fun createPost() {
        viewModelScope.launch {
            _uiState.value = PostBoardUiState.Loading

            if (boardRequest == null) {
                _uiState.value = PostBoardUiState.Error("게시글을 작성하기 위한 값이 입력되지 않았습니다.")
                Log.d("PostBoardViewModel", "createPost 호출 시 boardRequest가 null입니다.")
                return@launch
            }

            boardRequest?.let {
                try {
                    val result = boardRepository.postBoard(it)
                    when {
                        result.data != null -> {
                            _createdBoardIds.value += result.data!!.boardId
                            _uiState.value = PostBoardUiState.Success(result.data!!)
                            Log.d("PostBoardViewModel", "PostBoardUiState.Success: ${result.data}")
                        }
                        result.error != null -> {
                            _uiState.value = PostBoardUiState.Error(result.error!!.message)
                            Log.d("PostBoardViewModel", "PostBoardUiState.Error: ${result.error}")
                        }
                        else -> {
                            _uiState.value = PostBoardUiState.Error("알 수 없는 오류가 발생했습니다.")
                            Log.d("PostBoardViewModel", "PostBoardUiState.Error: 알 수 없는 오류가 발생했습니다.")
                        }
                    }
                } catch (e: Exception) {
                    _uiState.value = PostBoardUiState.Error("네트워크 오류: ${e.message}")
                    Log.d("PostBoardViewModel", "PostBoardUiState.Error: 네트워크 오류: ${e.message}")
                }
            } ?: run {
                _uiState.value = PostBoardUiState.Error("BoardRequestDto가 생성되지 않았습니다.")
                Log.d("PostBoardViewModel", "PostBoardUiState.Error: BoardRequestDto가 생성되지 않았습니다.")
            }
        }
    }

    fun resetState() {
        _uiState.value = PostBoardUiState.Initial
        _formState.value = BoardFormState()
        _createdBoardIds.value = emptyList()
        _imageUploadState.value = ImageUploadState.Idle
        _isFormValid.value = false
        _generalError.value = Pair(false, "")
        boardRequest = null
        _serverImageUrls.value = emptyList()
    }
}
package com.materip.feature_home3.viewModel

import androidx.lifecycle.ViewModel
import com.materip.core_repository.repository.review_repository.ReviewRepository
import com.materip.feature_home3.intent.ReviewIntent
import com.materip.feature_home3.state.ReviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReviewUiState>(ReviewUiState.Initial)
    val uiState: StateFlow<ReviewUiState> = _uiState

//    fun handleIntent(intent: ReviewIntent) {
//        when (intent) {
//            // Add your code here
//        }
//    }

    /***
     * 동행후기에서 선택한 태그에 대해서 카운트를 늘리기
     * (ui에서 카운트가 0이라면 안보이게 하고, 0 이상이면 보이게 하기)
     * 최대 3개까지 보이고 그 이상이면 더보기 버튼을 눌러 모든 평가가 보이게 하기
     */

    /***
     * 받은 동행 후기 목록을 가져오기
     * 마찬가지로 더보기 버튼이 있고, 모든 후기가 보임
     * (Ui에서 온클릭 시 동행후기 상세화면으로 이동)
     */

}

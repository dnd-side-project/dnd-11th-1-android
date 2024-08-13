package com.materip.feature_home.viewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 J\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u0007H\u0002J\u0010\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\tH\u0002J\u0010\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\u0007H\u0002J\u0010\u0010\'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020\u0007H\u0002J\u0010\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0007H\u0002J\u0016\u0010+\u001a\u00020\u001b2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0002J\u0010\u0010-\u001a\u00020\u001b2\u0006\u0010.\u001a\u00020\u0007H\u0002J\u0010\u0010/\u001a\u00020\u001b2\u0006\u00100\u001a\u00020\u0007H\u0002J\u0016\u00101\u001a\u00020\u001b2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0002J\u0010\u00103\u001a\u00020\u001b2\u0006\u00104\u001a\u00020\u0007H\u0002J\u0010\u00105\u001a\u00020\u001b2\u0006\u00106\u001a\u00020\u0007H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u00067"}, d2 = {"Lcom/materip/feature_home/viewModel/HomeHiltViewModel;", "Landroidx/lifecycle/ViewModel;", "boardRepository", "Lcom/materip/core_repository/repository/BoardRepository;", "(Lcom/materip/core_repository/repository/BoardRepository;)V", "_age", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_capacity", "", "_content", "_endDate", "_gender", "_imageUris", "", "_region", "_startDate", "_tags", "_title", "_type", "_uiState", "Lcom/materip/feature_home/state/HomeUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "createPost", "", "boardRequestDto", "Lcom/materip/core_model/BoardRequestDto;", "onIntent", "intent", "Lcom/materip/feature_home/intent/HomeIntent;", "updateAge", "newAge", "updateCapacity", "newCapacity", "updateContent", "newContent", "updateEndDate", "newEndDate", "updateGender", "newGender", "updateImageUris", "newImageUris", "updateRegion", "newRegion", "updateStartDate", "newStartDate", "updateTags", "newTags", "updateTitle", "newTitle", "updateType", "newType", "feature-home3_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class HomeHiltViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.materip.core_repository.repository.BoardRepository boardRepository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.materip.feature_home.state.HomeUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.materip.feature_home.state.HomeUiState> uiState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _title = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _content = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _tags = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _gender = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _age = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _type = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _region = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _startDate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _endDate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _capacity = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.String>> _imageUris = null;
    
    @javax.inject.Inject
    public HomeHiltViewModel(@org.jetbrains.annotations.NotNull
    com.materip.core_repository.repository.BoardRepository boardRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.materip.feature_home.state.HomeUiState> getUiState() {
        return null;
    }
    
    public final void onIntent(@org.jetbrains.annotations.NotNull
    com.materip.feature_home.intent.HomeIntent intent) {
    }
    
    private final void updateTitle(java.lang.String newTitle) {
    }
    
    private final void updateContent(java.lang.String newContent) {
    }
    
    private final void updateTags(java.util.List<java.lang.String> newTags) {
    }
    
    private final void updateGender(java.lang.String newGender) {
    }
    
    private final void updateAge(java.lang.String newAge) {
    }
    
    private final void updateType(java.lang.String newType) {
    }
    
    private final void updateRegion(java.lang.String newRegion) {
    }
    
    private final void updateStartDate(java.lang.String newStartDate) {
    }
    
    private final void updateEndDate(java.lang.String newEndDate) {
    }
    
    private final void updateCapacity(int newCapacity) {
    }
    
    private final void updateImageUris(java.util.List<java.lang.String> newImageUris) {
    }
    
    public final void createPost(@org.jetbrains.annotations.NotNull
    com.materip.core_model.BoardRequestDto boardRequestDto) {
    }
}
package com.materip.feature_home.viewModel;

import com.materip.core_repository.repository.BoardRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class HomeHiltViewModel_Factory implements Factory<HomeHiltViewModel> {
  private final Provider<BoardRepository> boardRepositoryProvider;

  public HomeHiltViewModel_Factory(Provider<BoardRepository> boardRepositoryProvider) {
    this.boardRepositoryProvider = boardRepositoryProvider;
  }

  @Override
  public HomeHiltViewModel get() {
    return newInstance(boardRepositoryProvider.get());
  }

  public static HomeHiltViewModel_Factory create(
      Provider<BoardRepository> boardRepositoryProvider) {
    return new HomeHiltViewModel_Factory(boardRepositoryProvider);
  }

  public static HomeHiltViewModel newInstance(BoardRepository boardRepository) {
    return new HomeHiltViewModel(boardRepository);
  }
}

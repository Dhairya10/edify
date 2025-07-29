package com.edify.learning.presentation.home;

import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.UserProfileRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<LearningRepository> repositoryProvider;

  private final Provider<UserProfileRepository> userProfileRepositoryProvider;

  public HomeViewModel_Factory(Provider<LearningRepository> repositoryProvider,
      Provider<UserProfileRepository> userProfileRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.userProfileRepositoryProvider = userProfileRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(repositoryProvider.get(), userProfileRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<LearningRepository> repositoryProvider,
      Provider<UserProfileRepository> userProfileRepositoryProvider) {
    return new HomeViewModel_Factory(repositoryProvider, userProfileRepositoryProvider);
  }

  public static HomeViewModel newInstance(LearningRepository repository,
      UserProfileRepository userProfileRepository) {
    return new HomeViewModel(repository, userProfileRepository);
  }
}

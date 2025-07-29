package com.edify.learning.presentation.profile;

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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<UserProfileRepository> userProfileRepositoryProvider;

  private final Provider<LearningRepository> learningRepositoryProvider;

  public ProfileViewModel_Factory(Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<LearningRepository> learningRepositoryProvider) {
    this.userProfileRepositoryProvider = userProfileRepositoryProvider;
    this.learningRepositoryProvider = learningRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(userProfileRepositoryProvider.get(), learningRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(
      Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<LearningRepository> learningRepositoryProvider) {
    return new ProfileViewModel_Factory(userProfileRepositoryProvider, learningRepositoryProvider);
  }

  public static ProfileViewModel newInstance(UserProfileRepository userProfileRepository,
      LearningRepository learningRepository) {
    return new ProfileViewModel(userProfileRepository, learningRepository);
  }
}

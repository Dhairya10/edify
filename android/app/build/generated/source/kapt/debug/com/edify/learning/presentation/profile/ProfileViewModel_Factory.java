package com.edify.learning.presentation.profile;

import androidx.work.WorkManager;
import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.UserProfileRepository;
import com.edify.learning.data.service.QuestGenerationService;
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

  private final Provider<QuestGenerationService> questGenerationServiceProvider;

  private final Provider<WorkManager> workManagerProvider;

  public ProfileViewModel_Factory(Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<LearningRepository> learningRepositoryProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider,
      Provider<WorkManager> workManagerProvider) {
    this.userProfileRepositoryProvider = userProfileRepositoryProvider;
    this.learningRepositoryProvider = learningRepositoryProvider;
    this.questGenerationServiceProvider = questGenerationServiceProvider;
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(userProfileRepositoryProvider.get(), learningRepositoryProvider.get(), questGenerationServiceProvider.get(), workManagerProvider.get());
  }

  public static ProfileViewModel_Factory create(
      Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<LearningRepository> learningRepositoryProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider,
      Provider<WorkManager> workManagerProvider) {
    return new ProfileViewModel_Factory(userProfileRepositoryProvider, learningRepositoryProvider, questGenerationServiceProvider, workManagerProvider);
  }

  public static ProfileViewModel newInstance(UserProfileRepository userProfileRepository,
      LearningRepository learningRepository, QuestGenerationService questGenerationService,
      WorkManager workManager) {
    return new ProfileViewModel(userProfileRepository, learningRepository, questGenerationService, workManager);
  }
}

package com.edify.learning.presentation.chapter;

import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.QuestRepository;
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
public final class ChapterViewModel_Factory implements Factory<ChapterViewModel> {
  private final Provider<LearningRepository> repositoryProvider;

  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<UserProfileRepository> userProfileRepositoryProvider;

  public ChapterViewModel_Factory(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<UserProfileRepository> userProfileRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.questRepositoryProvider = questRepositoryProvider;
    this.userProfileRepositoryProvider = userProfileRepositoryProvider;
  }

  @Override
  public ChapterViewModel get() {
    return newInstance(repositoryProvider.get(), questRepositoryProvider.get(), userProfileRepositoryProvider.get());
  }

  public static ChapterViewModel_Factory create(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<UserProfileRepository> userProfileRepositoryProvider) {
    return new ChapterViewModel_Factory(repositoryProvider, questRepositoryProvider, userProfileRepositoryProvider);
  }

  public static ChapterViewModel newInstance(LearningRepository repository,
      QuestRepository questRepository, UserProfileRepository userProfileRepository) {
    return new ChapterViewModel(repository, questRepository, userProfileRepository);
  }
}

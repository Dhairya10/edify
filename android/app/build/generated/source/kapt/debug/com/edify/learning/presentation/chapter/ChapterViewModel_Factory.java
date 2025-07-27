package com.edify.learning.presentation.chapter;

import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.QuestRepository;
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

  public ChapterViewModel_Factory(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.questRepositoryProvider = questRepositoryProvider;
  }

  @Override
  public ChapterViewModel get() {
    return newInstance(repositoryProvider.get(), questRepositoryProvider.get());
  }

  public static ChapterViewModel_Factory create(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider) {
    return new ChapterViewModel_Factory(repositoryProvider, questRepositoryProvider);
  }

  public static ChapterViewModel newInstance(LearningRepository repository,
      QuestRepository questRepository) {
    return new ChapterViewModel(repository, questRepository);
  }
}

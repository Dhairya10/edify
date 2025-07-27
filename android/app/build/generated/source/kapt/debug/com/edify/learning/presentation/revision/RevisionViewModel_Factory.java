package com.edify.learning.presentation.revision;

import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.QuestRepository;
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
public final class RevisionViewModel_Factory implements Factory<RevisionViewModel> {
  private final Provider<LearningRepository> repositoryProvider;

  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<QuestGenerationService> questGenerationServiceProvider;

  public RevisionViewModel_Factory(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    this.repositoryProvider = repositoryProvider;
    this.questRepositoryProvider = questRepositoryProvider;
    this.questGenerationServiceProvider = questGenerationServiceProvider;
  }

  @Override
  public RevisionViewModel get() {
    return newInstance(repositoryProvider.get(), questRepositoryProvider.get(), questGenerationServiceProvider.get());
  }

  public static RevisionViewModel_Factory create(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    return new RevisionViewModel_Factory(repositoryProvider, questRepositoryProvider, questGenerationServiceProvider);
  }

  public static RevisionViewModel newInstance(LearningRepository repository,
      QuestRepository questRepository, QuestGenerationService questGenerationService) {
    return new RevisionViewModel(repository, questRepository, questGenerationService);
  }
}

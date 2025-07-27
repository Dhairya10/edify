package com.edify.learning.presentation.quest;

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
public final class QuestViewModel_Factory implements Factory<QuestViewModel> {
  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<QuestGenerationService> questGenerationServiceProvider;

  public QuestViewModel_Factory(Provider<QuestRepository> questRepositoryProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    this.questRepositoryProvider = questRepositoryProvider;
    this.questGenerationServiceProvider = questGenerationServiceProvider;
  }

  @Override
  public QuestViewModel get() {
    return newInstance(questRepositoryProvider.get(), questGenerationServiceProvider.get());
  }

  public static QuestViewModel_Factory create(Provider<QuestRepository> questRepositoryProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    return new QuestViewModel_Factory(questRepositoryProvider, questGenerationServiceProvider);
  }

  public static QuestViewModel newInstance(QuestRepository questRepository,
      QuestGenerationService questGenerationService) {
    return new QuestViewModel(questRepository, questGenerationService);
  }
}

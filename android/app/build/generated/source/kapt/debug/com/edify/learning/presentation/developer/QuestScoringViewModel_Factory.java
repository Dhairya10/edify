package com.edify.learning.presentation.developer;

import com.edify.learning.domain.service.QuestScoringService;
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
public final class QuestScoringViewModel_Factory implements Factory<QuestScoringViewModel> {
  private final Provider<QuestScoringService> questScoringServiceProvider;

  public QuestScoringViewModel_Factory(Provider<QuestScoringService> questScoringServiceProvider) {
    this.questScoringServiceProvider = questScoringServiceProvider;
  }

  @Override
  public QuestScoringViewModel get() {
    return newInstance(questScoringServiceProvider.get());
  }

  public static QuestScoringViewModel_Factory create(
      Provider<QuestScoringService> questScoringServiceProvider) {
    return new QuestScoringViewModel_Factory(questScoringServiceProvider);
  }

  public static QuestScoringViewModel newInstance(QuestScoringService questScoringService) {
    return new QuestScoringViewModel(questScoringService);
  }
}

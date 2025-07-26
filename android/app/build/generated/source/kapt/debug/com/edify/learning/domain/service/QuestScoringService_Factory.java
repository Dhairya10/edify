package com.edify.learning.domain.service;

import com.edify.learning.data.dao.ChapterStatsDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class QuestScoringService_Factory implements Factory<QuestScoringService> {
  private final Provider<ChapterStatsDao> chapterStatsDaoProvider;

  public QuestScoringService_Factory(Provider<ChapterStatsDao> chapterStatsDaoProvider) {
    this.chapterStatsDaoProvider = chapterStatsDaoProvider;
  }

  @Override
  public QuestScoringService get() {
    return newInstance(chapterStatsDaoProvider.get());
  }

  public static QuestScoringService_Factory create(
      Provider<ChapterStatsDao> chapterStatsDaoProvider) {
    return new QuestScoringService_Factory(chapterStatsDaoProvider);
  }

  public static QuestScoringService newInstance(ChapterStatsDao chapterStatsDao) {
    return new QuestScoringService(chapterStatsDao);
  }
}

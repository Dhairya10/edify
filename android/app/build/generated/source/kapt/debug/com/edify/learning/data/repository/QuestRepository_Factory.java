package com.edify.learning.data.repository;

import com.edify.learning.data.dao.ChapterDao;
import com.edify.learning.data.dao.ChapterStatsDao;
import com.edify.learning.data.dao.GeneratedQuestDao;
import com.edify.learning.data.dao.SubjectDao;
import com.edify.learning.data.service.QuestGenerationService;
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
public final class QuestRepository_Factory implements Factory<QuestRepository> {
  private final Provider<GeneratedQuestDao> generatedQuestDaoProvider;

  private final Provider<ChapterStatsDao> chapterStatsDaoProvider;

  private final Provider<ChapterDao> chapterDaoProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<QuestGenerationService> questGenerationServiceProvider;

  public QuestRepository_Factory(Provider<GeneratedQuestDao> generatedQuestDaoProvider,
      Provider<ChapterStatsDao> chapterStatsDaoProvider, Provider<ChapterDao> chapterDaoProvider,
      Provider<SubjectDao> subjectDaoProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    this.generatedQuestDaoProvider = generatedQuestDaoProvider;
    this.chapterStatsDaoProvider = chapterStatsDaoProvider;
    this.chapterDaoProvider = chapterDaoProvider;
    this.subjectDaoProvider = subjectDaoProvider;
    this.questGenerationServiceProvider = questGenerationServiceProvider;
  }

  @Override
  public QuestRepository get() {
    return newInstance(generatedQuestDaoProvider.get(), chapterStatsDaoProvider.get(), chapterDaoProvider.get(), subjectDaoProvider.get(), questGenerationServiceProvider.get());
  }

  public static QuestRepository_Factory create(
      Provider<GeneratedQuestDao> generatedQuestDaoProvider,
      Provider<ChapterStatsDao> chapterStatsDaoProvider, Provider<ChapterDao> chapterDaoProvider,
      Provider<SubjectDao> subjectDaoProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    return new QuestRepository_Factory(generatedQuestDaoProvider, chapterStatsDaoProvider, chapterDaoProvider, subjectDaoProvider, questGenerationServiceProvider);
  }

  public static QuestRepository newInstance(GeneratedQuestDao generatedQuestDao,
      ChapterStatsDao chapterStatsDao, ChapterDao chapterDao, SubjectDao subjectDao,
      QuestGenerationService questGenerationService) {
    return new QuestRepository(generatedQuestDao, chapterStatsDao, chapterDao, subjectDao, questGenerationService);
  }
}

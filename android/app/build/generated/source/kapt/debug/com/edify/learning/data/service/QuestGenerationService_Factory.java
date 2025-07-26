package com.edify.learning.data.service;

import com.edify.learning.data.dao.ChapterDao;
import com.edify.learning.data.dao.ChapterStatsDao;
import com.edify.learning.data.dao.ChatDao;
import com.edify.learning.data.dao.GeneratedQuestDao;
import com.edify.learning.data.dao.NoteDao;
import com.edify.learning.data.dao.SubjectDao;
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
public final class QuestGenerationService_Factory implements Factory<QuestGenerationService> {
  private final Provider<ChapterStatsDao> chapterStatsDaoProvider;

  private final Provider<GeneratedQuestDao> generatedQuestDaoProvider;

  private final Provider<ChapterDao> chapterDaoProvider;

  private final Provider<NoteDao> noteDaoProvider;

  private final Provider<ChatDao> chatDaoProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<GemmaService> gemmaServiceProvider;

  public QuestGenerationService_Factory(Provider<ChapterStatsDao> chapterStatsDaoProvider,
      Provider<GeneratedQuestDao> generatedQuestDaoProvider,
      Provider<ChapterDao> chapterDaoProvider, Provider<NoteDao> noteDaoProvider,
      Provider<ChatDao> chatDaoProvider, Provider<SubjectDao> subjectDaoProvider,
      Provider<GemmaService> gemmaServiceProvider) {
    this.chapterStatsDaoProvider = chapterStatsDaoProvider;
    this.generatedQuestDaoProvider = generatedQuestDaoProvider;
    this.chapterDaoProvider = chapterDaoProvider;
    this.noteDaoProvider = noteDaoProvider;
    this.chatDaoProvider = chatDaoProvider;
    this.subjectDaoProvider = subjectDaoProvider;
    this.gemmaServiceProvider = gemmaServiceProvider;
  }

  @Override
  public QuestGenerationService get() {
    return newInstance(chapterStatsDaoProvider.get(), generatedQuestDaoProvider.get(), chapterDaoProvider.get(), noteDaoProvider.get(), chatDaoProvider.get(), subjectDaoProvider.get(), gemmaServiceProvider.get());
  }

  public static QuestGenerationService_Factory create(
      Provider<ChapterStatsDao> chapterStatsDaoProvider,
      Provider<GeneratedQuestDao> generatedQuestDaoProvider,
      Provider<ChapterDao> chapterDaoProvider, Provider<NoteDao> noteDaoProvider,
      Provider<ChatDao> chatDaoProvider, Provider<SubjectDao> subjectDaoProvider,
      Provider<GemmaService> gemmaServiceProvider) {
    return new QuestGenerationService_Factory(chapterStatsDaoProvider, generatedQuestDaoProvider, chapterDaoProvider, noteDaoProvider, chatDaoProvider, subjectDaoProvider, gemmaServiceProvider);
  }

  public static QuestGenerationService newInstance(ChapterStatsDao chapterStatsDao,
      GeneratedQuestDao generatedQuestDao, ChapterDao chapterDao, NoteDao noteDao, ChatDao chatDao,
      SubjectDao subjectDao, GemmaService gemmaService) {
    return new QuestGenerationService(chapterStatsDao, generatedQuestDao, chapterDao, noteDao, chatDao, subjectDao, gemmaService);
  }
}

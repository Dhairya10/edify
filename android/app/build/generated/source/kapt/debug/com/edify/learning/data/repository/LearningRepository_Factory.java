package com.edify.learning.data.repository;

import android.content.Context;
import com.edify.learning.data.dao.ChapterDao;
import com.edify.learning.data.dao.ChapterStatsDao;
import com.edify.learning.data.dao.ChatDao;
import com.edify.learning.data.dao.NoteDao;
import com.edify.learning.data.dao.SubjectDao;
import com.edify.learning.data.dao.UserResponseDao;
import com.edify.learning.data.service.GemmaService;
import com.edify.learning.data.service.QuestGenerationService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class LearningRepository_Factory implements Factory<LearningRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<ChapterDao> chapterDaoProvider;

  private final Provider<NoteDao> noteDaoProvider;

  private final Provider<ChatDao> chatDaoProvider;

  private final Provider<UserResponseDao> userResponseDaoProvider;

  private final Provider<ChapterStatsDao> chapterStatsDaoProvider;

  private final Provider<GemmaService> gemmaServiceProvider;

  private final Provider<QuestGenerationService> questGenerationServiceProvider;

  public LearningRepository_Factory(Provider<Context> contextProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<ChapterDao> chapterDaoProvider,
      Provider<NoteDao> noteDaoProvider, Provider<ChatDao> chatDaoProvider,
      Provider<UserResponseDao> userResponseDaoProvider,
      Provider<ChapterStatsDao> chapterStatsDaoProvider,
      Provider<GemmaService> gemmaServiceProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    this.contextProvider = contextProvider;
    this.subjectDaoProvider = subjectDaoProvider;
    this.chapterDaoProvider = chapterDaoProvider;
    this.noteDaoProvider = noteDaoProvider;
    this.chatDaoProvider = chatDaoProvider;
    this.userResponseDaoProvider = userResponseDaoProvider;
    this.chapterStatsDaoProvider = chapterStatsDaoProvider;
    this.gemmaServiceProvider = gemmaServiceProvider;
    this.questGenerationServiceProvider = questGenerationServiceProvider;
  }

  @Override
  public LearningRepository get() {
    return newInstance(contextProvider.get(), subjectDaoProvider.get(), chapterDaoProvider.get(), noteDaoProvider.get(), chatDaoProvider.get(), userResponseDaoProvider.get(), chapterStatsDaoProvider.get(), gemmaServiceProvider.get(), questGenerationServiceProvider.get());
  }

  public static LearningRepository_Factory create(Provider<Context> contextProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<ChapterDao> chapterDaoProvider,
      Provider<NoteDao> noteDaoProvider, Provider<ChatDao> chatDaoProvider,
      Provider<UserResponseDao> userResponseDaoProvider,
      Provider<ChapterStatsDao> chapterStatsDaoProvider,
      Provider<GemmaService> gemmaServiceProvider,
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    return new LearningRepository_Factory(contextProvider, subjectDaoProvider, chapterDaoProvider, noteDaoProvider, chatDaoProvider, userResponseDaoProvider, chapterStatsDaoProvider, gemmaServiceProvider, questGenerationServiceProvider);
  }

  public static LearningRepository newInstance(Context context, SubjectDao subjectDao,
      ChapterDao chapterDao, NoteDao noteDao, ChatDao chatDao, UserResponseDao userResponseDao,
      ChapterStatsDao chapterStatsDao, GemmaService gemmaService,
      QuestGenerationService questGenerationService) {
    return new LearningRepository(context, subjectDao, chapterDao, noteDao, chatDao, userResponseDao, chapterStatsDao, gemmaService, questGenerationService);
  }
}

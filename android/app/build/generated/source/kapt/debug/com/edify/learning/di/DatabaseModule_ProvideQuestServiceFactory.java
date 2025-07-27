package com.edify.learning.di;

import com.edify.learning.data.dao.ChapterDao;
import com.edify.learning.data.service.QuestService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideQuestServiceFactory implements Factory<QuestService> {
  private final Provider<ChapterDao> chapterDaoProvider;

  public DatabaseModule_ProvideQuestServiceFactory(Provider<ChapterDao> chapterDaoProvider) {
    this.chapterDaoProvider = chapterDaoProvider;
  }

  @Override
  public QuestService get() {
    return provideQuestService(chapterDaoProvider.get());
  }

  public static DatabaseModule_ProvideQuestServiceFactory create(
      Provider<ChapterDao> chapterDaoProvider) {
    return new DatabaseModule_ProvideQuestServiceFactory(chapterDaoProvider);
  }

  public static QuestService provideQuestService(ChapterDao chapterDao) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideQuestService(chapterDao));
  }
}

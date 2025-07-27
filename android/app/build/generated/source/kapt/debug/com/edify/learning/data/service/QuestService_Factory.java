package com.edify.learning.data.service;

import com.edify.learning.data.dao.ChapterDao;
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
public final class QuestService_Factory implements Factory<QuestService> {
  private final Provider<ChapterDao> chapterDaoProvider;

  public QuestService_Factory(Provider<ChapterDao> chapterDaoProvider) {
    this.chapterDaoProvider = chapterDaoProvider;
  }

  @Override
  public QuestService get() {
    return newInstance(chapterDaoProvider.get());
  }

  public static QuestService_Factory create(Provider<ChapterDao> chapterDaoProvider) {
    return new QuestService_Factory(chapterDaoProvider);
  }

  public static QuestService newInstance(ChapterDao chapterDao) {
    return new QuestService(chapterDao);
  }
}

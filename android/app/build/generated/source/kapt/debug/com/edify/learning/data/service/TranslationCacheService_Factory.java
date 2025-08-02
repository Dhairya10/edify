package com.edify.learning.data.service;

import com.edify.learning.data.database.TranslatedChapterDao;
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
public final class TranslationCacheService_Factory implements Factory<TranslationCacheService> {
  private final Provider<TranslatedChapterDao> translatedChapterDaoProvider;

  public TranslationCacheService_Factory(
      Provider<TranslatedChapterDao> translatedChapterDaoProvider) {
    this.translatedChapterDaoProvider = translatedChapterDaoProvider;
  }

  @Override
  public TranslationCacheService get() {
    return newInstance(translatedChapterDaoProvider.get());
  }

  public static TranslationCacheService_Factory create(
      Provider<TranslatedChapterDao> translatedChapterDaoProvider) {
    return new TranslationCacheService_Factory(translatedChapterDaoProvider);
  }

  public static TranslationCacheService newInstance(TranslatedChapterDao translatedChapterDao) {
    return new TranslationCacheService(translatedChapterDao);
  }
}

package com.edify.learning.di;

import com.edify.learning.data.database.TranslatedChapterDao;
import com.edify.learning.data.service.TranslationCacheService;
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
public final class DatabaseModule_ProvideTranslationCacheServiceFactory implements Factory<TranslationCacheService> {
  private final Provider<TranslatedChapterDao> translatedChapterDaoProvider;

  public DatabaseModule_ProvideTranslationCacheServiceFactory(
      Provider<TranslatedChapterDao> translatedChapterDaoProvider) {
    this.translatedChapterDaoProvider = translatedChapterDaoProvider;
  }

  @Override
  public TranslationCacheService get() {
    return provideTranslationCacheService(translatedChapterDaoProvider.get());
  }

  public static DatabaseModule_ProvideTranslationCacheServiceFactory create(
      Provider<TranslatedChapterDao> translatedChapterDaoProvider) {
    return new DatabaseModule_ProvideTranslationCacheServiceFactory(translatedChapterDaoProvider);
  }

  public static TranslationCacheService provideTranslationCacheService(
      TranslatedChapterDao translatedChapterDao) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTranslationCacheService(translatedChapterDao));
  }
}

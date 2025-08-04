package com.edify.learning.di;

import com.edify.learning.data.database.EdifyDatabase;
import com.edify.learning.data.database.TranslatedChapterDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideTranslatedChapterDaoFactory implements Factory<TranslatedChapterDao> {
  private final Provider<EdifyDatabase> databaseProvider;

  public DatabaseModule_ProvideTranslatedChapterDaoFactory(
      Provider<EdifyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TranslatedChapterDao get() {
    return provideTranslatedChapterDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTranslatedChapterDaoFactory create(
      Provider<EdifyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTranslatedChapterDaoFactory(databaseProvider);
  }

  public static TranslatedChapterDao provideTranslatedChapterDao(EdifyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTranslatedChapterDao(database));
  }
}

package com.edify.learning.di;

import com.edify.learning.data.dao.ChapterDao;
import com.edify.learning.data.database.EdifyDatabase;
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
public final class DatabaseModule_ProvideChapterDaoFactory implements Factory<ChapterDao> {
  private final Provider<EdifyDatabase> databaseProvider;

  public DatabaseModule_ProvideChapterDaoFactory(Provider<EdifyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ChapterDao get() {
    return provideChapterDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideChapterDaoFactory create(
      Provider<EdifyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideChapterDaoFactory(databaseProvider);
  }

  public static ChapterDao provideChapterDao(EdifyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideChapterDao(database));
  }
}

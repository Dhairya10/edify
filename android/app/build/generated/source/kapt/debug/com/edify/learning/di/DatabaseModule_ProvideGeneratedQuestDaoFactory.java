package com.edify.learning.di;

import com.edify.learning.data.dao.GeneratedQuestDao;
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
public final class DatabaseModule_ProvideGeneratedQuestDaoFactory implements Factory<GeneratedQuestDao> {
  private final Provider<EdifyDatabase> databaseProvider;

  public DatabaseModule_ProvideGeneratedQuestDaoFactory(Provider<EdifyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public GeneratedQuestDao get() {
    return provideGeneratedQuestDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideGeneratedQuestDaoFactory create(
      Provider<EdifyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideGeneratedQuestDaoFactory(databaseProvider);
  }

  public static GeneratedQuestDao provideGeneratedQuestDao(EdifyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideGeneratedQuestDao(database));
  }
}

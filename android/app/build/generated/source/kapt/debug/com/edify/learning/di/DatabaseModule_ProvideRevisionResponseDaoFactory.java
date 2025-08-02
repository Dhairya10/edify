package com.edify.learning.di;

import com.edify.learning.data.dao.RevisionResponseDao;
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
public final class DatabaseModule_ProvideRevisionResponseDaoFactory implements Factory<RevisionResponseDao> {
  private final Provider<EdifyDatabase> databaseProvider;

  public DatabaseModule_ProvideRevisionResponseDaoFactory(
      Provider<EdifyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RevisionResponseDao get() {
    return provideRevisionResponseDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideRevisionResponseDaoFactory create(
      Provider<EdifyDatabase> databaseProvider) {
    return new DatabaseModule_ProvideRevisionResponseDaoFactory(databaseProvider);
  }

  public static RevisionResponseDao provideRevisionResponseDao(EdifyDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRevisionResponseDao(database));
  }
}

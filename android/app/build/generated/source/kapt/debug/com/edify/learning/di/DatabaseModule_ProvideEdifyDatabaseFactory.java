package com.edify.learning.di;

import android.content.Context;
import com.edify.learning.data.database.EdifyDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideEdifyDatabaseFactory implements Factory<EdifyDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideEdifyDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public EdifyDatabase get() {
    return provideEdifyDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideEdifyDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideEdifyDatabaseFactory(contextProvider);
  }

  public static EdifyDatabase provideEdifyDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideEdifyDatabase(context));
  }
}

package com.edify.learning.presentation.developer;

import com.edify.learning.data.database.EdifyDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DeveloperModeViewModel_Factory implements Factory<DeveloperModeViewModel> {
  private final Provider<EdifyDatabase> databaseProvider;

  public DeveloperModeViewModel_Factory(Provider<EdifyDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DeveloperModeViewModel get() {
    return newInstance(databaseProvider.get());
  }

  public static DeveloperModeViewModel_Factory create(Provider<EdifyDatabase> databaseProvider) {
    return new DeveloperModeViewModel_Factory(databaseProvider);
  }

  public static DeveloperModeViewModel newInstance(EdifyDatabase database) {
    return new DeveloperModeViewModel(database);
  }
}

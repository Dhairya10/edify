package com.edify.learning.di;

import android.content.Context;
import com.edify.learning.data.service.PromptService;
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
public final class DatabaseModule_ProvidePromptServiceFactory implements Factory<PromptService> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvidePromptServiceFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public PromptService get() {
    return providePromptService(contextProvider.get());
  }

  public static DatabaseModule_ProvidePromptServiceFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvidePromptServiceFactory(contextProvider);
  }

  public static PromptService providePromptService(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePromptService(context));
  }
}

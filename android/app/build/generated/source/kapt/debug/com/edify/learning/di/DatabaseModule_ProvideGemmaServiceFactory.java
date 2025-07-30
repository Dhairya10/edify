package com.edify.learning.di;

import android.content.Context;
import com.edify.learning.data.service.GemmaService;
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
public final class DatabaseModule_ProvideGemmaServiceFactory implements Factory<GemmaService> {
  private final Provider<Context> contextProvider;

  private final Provider<PromptService> promptServiceProvider;

  public DatabaseModule_ProvideGemmaServiceFactory(Provider<Context> contextProvider,
      Provider<PromptService> promptServiceProvider) {
    this.contextProvider = contextProvider;
    this.promptServiceProvider = promptServiceProvider;
  }

  @Override
  public GemmaService get() {
    return provideGemmaService(contextProvider.get(), promptServiceProvider.get());
  }

  public static DatabaseModule_ProvideGemmaServiceFactory create(Provider<Context> contextProvider,
      Provider<PromptService> promptServiceProvider) {
    return new DatabaseModule_ProvideGemmaServiceFactory(contextProvider, promptServiceProvider);
  }

  public static GemmaService provideGemmaService(Context context, PromptService promptService) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideGemmaService(context, promptService));
  }
}

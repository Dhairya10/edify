package com.edify.learning.data.service;

import android.content.Context;
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
public final class GemmaService_Factory implements Factory<GemmaService> {
  private final Provider<Context> contextProvider;

  private final Provider<PromptService> promptServiceProvider;

  public GemmaService_Factory(Provider<Context> contextProvider,
      Provider<PromptService> promptServiceProvider) {
    this.contextProvider = contextProvider;
    this.promptServiceProvider = promptServiceProvider;
  }

  @Override
  public GemmaService get() {
    return newInstance(contextProvider.get(), promptServiceProvider.get());
  }

  public static GemmaService_Factory create(Provider<Context> contextProvider,
      Provider<PromptService> promptServiceProvider) {
    return new GemmaService_Factory(contextProvider, promptServiceProvider);
  }

  public static GemmaService newInstance(Context context, PromptService promptService) {
    return new GemmaService(context, promptService);
  }
}

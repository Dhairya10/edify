package com.edify.learning.presentation.components;

import com.edify.learning.data.service.GemmaService;
import com.edify.learning.data.service.TranslationCacheService;
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
public final class TranslationViewModel_Factory implements Factory<TranslationViewModel> {
  private final Provider<GemmaService> gemmaServiceProvider;

  private final Provider<TranslationCacheService> translationCacheServiceProvider;

  public TranslationViewModel_Factory(Provider<GemmaService> gemmaServiceProvider,
      Provider<TranslationCacheService> translationCacheServiceProvider) {
    this.gemmaServiceProvider = gemmaServiceProvider;
    this.translationCacheServiceProvider = translationCacheServiceProvider;
  }

  @Override
  public TranslationViewModel get() {
    return newInstance(gemmaServiceProvider.get(), translationCacheServiceProvider.get());
  }

  public static TranslationViewModel_Factory create(Provider<GemmaService> gemmaServiceProvider,
      Provider<TranslationCacheService> translationCacheServiceProvider) {
    return new TranslationViewModel_Factory(gemmaServiceProvider, translationCacheServiceProvider);
  }

  public static TranslationViewModel newInstance(GemmaService gemmaService,
      TranslationCacheService translationCacheService) {
    return new TranslationViewModel(gemmaService, translationCacheService);
  }
}

package com.edify.learning.presentation.components;

import com.edify.learning.data.service.GemmaService;
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

  public TranslationViewModel_Factory(Provider<GemmaService> gemmaServiceProvider) {
    this.gemmaServiceProvider = gemmaServiceProvider;
  }

  @Override
  public TranslationViewModel get() {
    return newInstance(gemmaServiceProvider.get());
  }

  public static TranslationViewModel_Factory create(Provider<GemmaService> gemmaServiceProvider) {
    return new TranslationViewModel_Factory(gemmaServiceProvider);
  }

  public static TranslationViewModel newInstance(GemmaService gemmaService) {
    return new TranslationViewModel(gemmaService);
  }
}

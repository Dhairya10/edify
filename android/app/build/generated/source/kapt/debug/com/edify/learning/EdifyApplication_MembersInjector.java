package com.edify.learning;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class EdifyApplication_MembersInjector implements MembersInjector<EdifyApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public EdifyApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<EdifyApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new EdifyApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(EdifyApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.edify.learning.EdifyApplication.workerFactory")
  public static void injectWorkerFactory(EdifyApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}

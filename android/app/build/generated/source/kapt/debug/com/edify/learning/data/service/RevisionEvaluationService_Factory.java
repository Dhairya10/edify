package com.edify.learning.data.service;

import android.content.Context;
import com.edify.learning.data.dao.RevisionSubmissionDao;
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
public final class RevisionEvaluationService_Factory implements Factory<RevisionEvaluationService> {
  private final Provider<Context> contextProvider;

  private final Provider<GemmaService> gemmaServiceProvider;

  private final Provider<PromptService> promptServiceProvider;

  private final Provider<RevisionSubmissionDao> revisionSubmissionDaoProvider;

  public RevisionEvaluationService_Factory(Provider<Context> contextProvider,
      Provider<GemmaService> gemmaServiceProvider, Provider<PromptService> promptServiceProvider,
      Provider<RevisionSubmissionDao> revisionSubmissionDaoProvider) {
    this.contextProvider = contextProvider;
    this.gemmaServiceProvider = gemmaServiceProvider;
    this.promptServiceProvider = promptServiceProvider;
    this.revisionSubmissionDaoProvider = revisionSubmissionDaoProvider;
  }

  @Override
  public RevisionEvaluationService get() {
    return newInstance(contextProvider.get(), gemmaServiceProvider.get(), promptServiceProvider.get(), revisionSubmissionDaoProvider.get());
  }

  public static RevisionEvaluationService_Factory create(Provider<Context> contextProvider,
      Provider<GemmaService> gemmaServiceProvider, Provider<PromptService> promptServiceProvider,
      Provider<RevisionSubmissionDao> revisionSubmissionDaoProvider) {
    return new RevisionEvaluationService_Factory(contextProvider, gemmaServiceProvider, promptServiceProvider, revisionSubmissionDaoProvider);
  }

  public static RevisionEvaluationService newInstance(Context context, GemmaService gemmaService,
      PromptService promptService, RevisionSubmissionDao revisionSubmissionDao) {
    return new RevisionEvaluationService(context, gemmaService, promptService, revisionSubmissionDao);
  }
}

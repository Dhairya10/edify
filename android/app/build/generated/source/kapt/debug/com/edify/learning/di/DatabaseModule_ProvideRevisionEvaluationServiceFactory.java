package com.edify.learning.di;

import android.content.Context;
import com.edify.learning.data.dao.RevisionSubmissionDao;
import com.edify.learning.data.service.GemmaService;
import com.edify.learning.data.service.PromptService;
import com.edify.learning.data.service.RevisionEvaluationService;
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
public final class DatabaseModule_ProvideRevisionEvaluationServiceFactory implements Factory<RevisionEvaluationService> {
  private final Provider<Context> contextProvider;

  private final Provider<GemmaService> gemmaServiceProvider;

  private final Provider<PromptService> promptServiceProvider;

  private final Provider<RevisionSubmissionDao> revisionSubmissionDaoProvider;

  public DatabaseModule_ProvideRevisionEvaluationServiceFactory(Provider<Context> contextProvider,
      Provider<GemmaService> gemmaServiceProvider, Provider<PromptService> promptServiceProvider,
      Provider<RevisionSubmissionDao> revisionSubmissionDaoProvider) {
    this.contextProvider = contextProvider;
    this.gemmaServiceProvider = gemmaServiceProvider;
    this.promptServiceProvider = promptServiceProvider;
    this.revisionSubmissionDaoProvider = revisionSubmissionDaoProvider;
  }

  @Override
  public RevisionEvaluationService get() {
    return provideRevisionEvaluationService(contextProvider.get(), gemmaServiceProvider.get(), promptServiceProvider.get(), revisionSubmissionDaoProvider.get());
  }

  public static DatabaseModule_ProvideRevisionEvaluationServiceFactory create(
      Provider<Context> contextProvider, Provider<GemmaService> gemmaServiceProvider,
      Provider<PromptService> promptServiceProvider,
      Provider<RevisionSubmissionDao> revisionSubmissionDaoProvider) {
    return new DatabaseModule_ProvideRevisionEvaluationServiceFactory(contextProvider, gemmaServiceProvider, promptServiceProvider, revisionSubmissionDaoProvider);
  }

  public static RevisionEvaluationService provideRevisionEvaluationService(Context context,
      GemmaService gemmaService, PromptService promptService,
      RevisionSubmissionDao revisionSubmissionDao) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRevisionEvaluationService(context, gemmaService, promptService, revisionSubmissionDao));
  }
}

package com.edify.learning.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.edify.learning.data.service.QuestGenerationService;
import dagger.internal.DaggerGenerated;
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
public final class DailyQuestWorker_Factory {
  private final Provider<QuestGenerationService> questGenerationServiceProvider;

  public DailyQuestWorker_Factory(Provider<QuestGenerationService> questGenerationServiceProvider) {
    this.questGenerationServiceProvider = questGenerationServiceProvider;
  }

  public DailyQuestWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, questGenerationServiceProvider.get());
  }

  public static DailyQuestWorker_Factory create(
      Provider<QuestGenerationService> questGenerationServiceProvider) {
    return new DailyQuestWorker_Factory(questGenerationServiceProvider);
  }

  public static DailyQuestWorker newInstance(Context context, WorkerParameters params,
      QuestGenerationService questGenerationService) {
    return new DailyQuestWorker(context, params, questGenerationService);
  }
}

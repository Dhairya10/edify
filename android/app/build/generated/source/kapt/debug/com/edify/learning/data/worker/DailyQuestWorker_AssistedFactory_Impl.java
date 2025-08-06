package com.edify.learning.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DailyQuestWorker_AssistedFactory_Impl implements DailyQuestWorker_AssistedFactory {
  private final DailyQuestWorker_Factory delegateFactory;

  DailyQuestWorker_AssistedFactory_Impl(DailyQuestWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public DailyQuestWorker create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<DailyQuestWorker_AssistedFactory> create(
      DailyQuestWorker_Factory delegateFactory) {
    return InstanceFactory.create(new DailyQuestWorker_AssistedFactory_Impl(delegateFactory));
  }
}

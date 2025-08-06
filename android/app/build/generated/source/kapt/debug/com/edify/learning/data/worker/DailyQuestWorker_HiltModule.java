package com.edify.learning.data.worker;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = DailyQuestWorker.class
)
public interface DailyQuestWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.edify.learning.data.worker.DailyQuestWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(DailyQuestWorker_AssistedFactory factory);
}

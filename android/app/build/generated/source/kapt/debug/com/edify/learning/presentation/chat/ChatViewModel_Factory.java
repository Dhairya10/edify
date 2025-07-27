package com.edify.learning.presentation.chat;

import android.content.Context;
import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.QuestRepository;
import com.edify.learning.data.service.ChatResponseService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<LearningRepository> repositoryProvider;

  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<ChatResponseService> chatResponseServiceProvider;

  private final Provider<Context> contextProvider;

  public ChatViewModel_Factory(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<ChatResponseService> chatResponseServiceProvider,
      Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.questRepositoryProvider = questRepositoryProvider;
    this.chatResponseServiceProvider = chatResponseServiceProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(repositoryProvider.get(), questRepositoryProvider.get(), chatResponseServiceProvider.get(), contextProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider,
      Provider<ChatResponseService> chatResponseServiceProvider,
      Provider<Context> contextProvider) {
    return new ChatViewModel_Factory(repositoryProvider, questRepositoryProvider, chatResponseServiceProvider, contextProvider);
  }

  public static ChatViewModel newInstance(LearningRepository repository,
      QuestRepository questRepository, ChatResponseService chatResponseService, Context context) {
    return new ChatViewModel(repository, questRepository, chatResponseService, context);
  }
}

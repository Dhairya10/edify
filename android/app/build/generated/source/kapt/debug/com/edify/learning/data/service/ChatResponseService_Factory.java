package com.edify.learning.data.service;

import com.edify.learning.data.dao.ChatDao;
import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.QuestRepository;
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
public final class ChatResponseService_Factory implements Factory<ChatResponseService> {
  private final Provider<LearningRepository> repositoryProvider;

  private final Provider<QuestRepository> questRepositoryProvider;

  private final Provider<ChatDao> chatDaoProvider;

  private final Provider<GemmaService> gemmaServiceProvider;

  public ChatResponseService_Factory(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider, Provider<ChatDao> chatDaoProvider,
      Provider<GemmaService> gemmaServiceProvider) {
    this.repositoryProvider = repositoryProvider;
    this.questRepositoryProvider = questRepositoryProvider;
    this.chatDaoProvider = chatDaoProvider;
    this.gemmaServiceProvider = gemmaServiceProvider;
  }

  @Override
  public ChatResponseService get() {
    return newInstance(repositoryProvider.get(), questRepositoryProvider.get(), chatDaoProvider.get(), gemmaServiceProvider.get());
  }

  public static ChatResponseService_Factory create(Provider<LearningRepository> repositoryProvider,
      Provider<QuestRepository> questRepositoryProvider, Provider<ChatDao> chatDaoProvider,
      Provider<GemmaService> gemmaServiceProvider) {
    return new ChatResponseService_Factory(repositoryProvider, questRepositoryProvider, chatDaoProvider, gemmaServiceProvider);
  }

  public static ChatResponseService newInstance(LearningRepository repository,
      QuestRepository questRepository, ChatDao chatDao, GemmaService gemmaService) {
    return new ChatResponseService(repository, questRepository, chatDao, gemmaService);
  }
}

package com.edify.learning;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.edify.learning.data.dao.ChapterDao;
import com.edify.learning.data.dao.ChapterStatsDao;
import com.edify.learning.data.dao.ChatDao;
import com.edify.learning.data.dao.GeneratedQuestDao;
import com.edify.learning.data.dao.NoteDao;
import com.edify.learning.data.dao.SubjectDao;
import com.edify.learning.data.dao.UserProfileDao;
import com.edify.learning.data.dao.UserResponseDao;
import com.edify.learning.data.database.EdifyDatabase;
import com.edify.learning.data.repository.LearningRepository;
import com.edify.learning.data.repository.QuestRepository;
import com.edify.learning.data.repository.UserProfileRepository;
import com.edify.learning.data.service.ChatResponseService;
import com.edify.learning.data.service.GemmaService;
import com.edify.learning.data.service.PromptService;
import com.edify.learning.data.service.QuestGenerationService;
import com.edify.learning.di.DatabaseModule;
import com.edify.learning.di.DatabaseModule_ProvideChapterDaoFactory;
import com.edify.learning.di.DatabaseModule_ProvideChapterStatsDaoFactory;
import com.edify.learning.di.DatabaseModule_ProvideChatDaoFactory;
import com.edify.learning.di.DatabaseModule_ProvideEdifyDatabaseFactory;
import com.edify.learning.di.DatabaseModule_ProvideGemmaServiceFactory;
import com.edify.learning.di.DatabaseModule_ProvideGeneratedQuestDaoFactory;
import com.edify.learning.di.DatabaseModule_ProvideNoteDaoFactory;
import com.edify.learning.di.DatabaseModule_ProvidePromptServiceFactory;
import com.edify.learning.di.DatabaseModule_ProvideQuestGenerationServiceFactory;
import com.edify.learning.di.DatabaseModule_ProvideSubjectDaoFactory;
import com.edify.learning.di.DatabaseModule_ProvideUserProfileDaoFactory;
import com.edify.learning.di.DatabaseModule_ProvideUserResponseDaoFactory;
import com.edify.learning.domain.service.QuestScoringService;
import com.edify.learning.presentation.MainActivity;
import com.edify.learning.presentation.chapter.ChapterViewModel;
import com.edify.learning.presentation.chapter.ChapterViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.chat.ChatViewModel;
import com.edify.learning.presentation.chat.ChatViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.components.TranslationViewModel;
import com.edify.learning.presentation.components.TranslationViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.developer.DeveloperModeViewModel;
import com.edify.learning.presentation.developer.DeveloperModeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.developer.QuestScoringViewModel;
import com.edify.learning.presentation.developer.QuestScoringViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.home.HomeViewModel;
import com.edify.learning.presentation.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.notes.NotesViewModel;
import com.edify.learning.presentation.notes.NotesViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.onboarding.OnboardingViewModel;
import com.edify.learning.presentation.onboarding.OnboardingViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.profile.ProfileViewModel;
import com.edify.learning.presentation.profile.ProfileViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.quest.QuestViewModel;
import com.edify.learning.presentation.quest.QuestViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.revision.RevisionViewModel;
import com.edify.learning.presentation.revision.RevisionViewModel_HiltModules_KeyModule_ProvideFactory;
import com.edify.learning.presentation.subject.SubjectViewModel;
import com.edify.learning.presentation.subject.SubjectViewModel_HiltModules_KeyModule_ProvideFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Map;
import java.util.Set;
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
public final class DaggerEdifyApplication_HiltComponents_SingletonC {
  private DaggerEdifyApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder databaseModule(DatabaseModule databaseModule) {
      Preconditions.checkNotNull(databaseModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    public EdifyApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements EdifyApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public EdifyApplication_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements EdifyApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public EdifyApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements EdifyApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public EdifyApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements EdifyApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public EdifyApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements EdifyApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public EdifyApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements EdifyApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public EdifyApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements EdifyApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public EdifyApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends EdifyApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends EdifyApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends EdifyApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends EdifyApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(ChapterViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ChatViewModel_HiltModules_KeyModule_ProvideFactory.provide(), DeveloperModeViewModel_HiltModules_KeyModule_ProvideFactory.provide(), HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide(), NotesViewModel_HiltModules_KeyModule_ProvideFactory.provide(), OnboardingViewModel_HiltModules_KeyModule_ProvideFactory.provide(), ProfileViewModel_HiltModules_KeyModule_ProvideFactory.provide(), QuestScoringViewModel_HiltModules_KeyModule_ProvideFactory.provide(), QuestViewModel_HiltModules_KeyModule_ProvideFactory.provide(), RevisionViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SubjectViewModel_HiltModules_KeyModule_ProvideFactory.provide(), TranslationViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends EdifyApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ChapterViewModel> chapterViewModelProvider;

    private Provider<ChatViewModel> chatViewModelProvider;

    private Provider<DeveloperModeViewModel> developerModeViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<NotesViewModel> notesViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<QuestScoringViewModel> questScoringViewModelProvider;

    private Provider<QuestViewModel> questViewModelProvider;

    private Provider<RevisionViewModel> revisionViewModelProvider;

    private Provider<SubjectViewModel> subjectViewModelProvider;

    private Provider<TranslationViewModel> translationViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.chapterViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.chatViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.developerModeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.notesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.questScoringViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.questViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.revisionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.subjectViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.translationViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, Provider<ViewModel>>builderWithExpectedSize(12).put("com.edify.learning.presentation.chapter.ChapterViewModel", ((Provider) chapterViewModelProvider)).put("com.edify.learning.presentation.chat.ChatViewModel", ((Provider) chatViewModelProvider)).put("com.edify.learning.presentation.developer.DeveloperModeViewModel", ((Provider) developerModeViewModelProvider)).put("com.edify.learning.presentation.home.HomeViewModel", ((Provider) homeViewModelProvider)).put("com.edify.learning.presentation.notes.NotesViewModel", ((Provider) notesViewModelProvider)).put("com.edify.learning.presentation.onboarding.OnboardingViewModel", ((Provider) onboardingViewModelProvider)).put("com.edify.learning.presentation.profile.ProfileViewModel", ((Provider) profileViewModelProvider)).put("com.edify.learning.presentation.developer.QuestScoringViewModel", ((Provider) questScoringViewModelProvider)).put("com.edify.learning.presentation.quest.QuestViewModel", ((Provider) questViewModelProvider)).put("com.edify.learning.presentation.revision.RevisionViewModel", ((Provider) revisionViewModelProvider)).put("com.edify.learning.presentation.subject.SubjectViewModel", ((Provider) subjectViewModelProvider)).put("com.edify.learning.presentation.components.TranslationViewModel", ((Provider) translationViewModelProvider)).build();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.edify.learning.presentation.chapter.ChapterViewModel 
          return (T) new ChapterViewModel(singletonCImpl.learningRepositoryProvider.get(), singletonCImpl.questRepositoryProvider.get(), singletonCImpl.userProfileRepositoryProvider.get());

          case 1: // com.edify.learning.presentation.chat.ChatViewModel 
          return (T) new ChatViewModel(singletonCImpl.learningRepositoryProvider.get(), singletonCImpl.questRepositoryProvider.get(), singletonCImpl.userProfileRepositoryProvider.get(), singletonCImpl.chatResponseServiceProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.edify.learning.presentation.developer.DeveloperModeViewModel 
          return (T) new DeveloperModeViewModel(singletonCImpl.provideEdifyDatabaseProvider.get());

          case 3: // com.edify.learning.presentation.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.learningRepositoryProvider.get(), singletonCImpl.userProfileRepositoryProvider.get());

          case 4: // com.edify.learning.presentation.notes.NotesViewModel 
          return (T) new NotesViewModel(singletonCImpl.learningRepositoryProvider.get());

          case 5: // com.edify.learning.presentation.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.userProfileDao());

          case 6: // com.edify.learning.presentation.profile.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.userProfileRepositoryProvider.get(), singletonCImpl.learningRepositoryProvider.get());

          case 7: // com.edify.learning.presentation.developer.QuestScoringViewModel 
          return (T) new QuestScoringViewModel(singletonCImpl.questScoringServiceProvider.get());

          case 8: // com.edify.learning.presentation.quest.QuestViewModel 
          return (T) new QuestViewModel(singletonCImpl.questRepositoryProvider.get(), singletonCImpl.provideQuestGenerationServiceProvider.get());

          case 9: // com.edify.learning.presentation.revision.RevisionViewModel 
          return (T) new RevisionViewModel(singletonCImpl.learningRepositoryProvider.get(), singletonCImpl.questRepositoryProvider.get(), singletonCImpl.provideQuestGenerationServiceProvider.get());

          case 10: // com.edify.learning.presentation.subject.SubjectViewModel 
          return (T) new SubjectViewModel(singletonCImpl.learningRepositoryProvider.get());

          case 11: // com.edify.learning.presentation.components.TranslationViewModel 
          return (T) new TranslationViewModel(singletonCImpl.provideGemmaServiceProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends EdifyApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends EdifyApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends EdifyApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<EdifyDatabase> provideEdifyDatabaseProvider;

    private Provider<PromptService> providePromptServiceProvider;

    private Provider<GemmaService> provideGemmaServiceProvider;

    private Provider<QuestGenerationService> provideQuestGenerationServiceProvider;

    private Provider<LearningRepository> learningRepositoryProvider;

    private Provider<QuestRepository> questRepositoryProvider;

    private Provider<UserProfileRepository> userProfileRepositoryProvider;

    private Provider<ChatResponseService> chatResponseServiceProvider;

    private Provider<QuestScoringService> questScoringServiceProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private SubjectDao subjectDao() {
      return DatabaseModule_ProvideSubjectDaoFactory.provideSubjectDao(provideEdifyDatabaseProvider.get());
    }

    private ChapterDao chapterDao() {
      return DatabaseModule_ProvideChapterDaoFactory.provideChapterDao(provideEdifyDatabaseProvider.get());
    }

    private NoteDao noteDao() {
      return DatabaseModule_ProvideNoteDaoFactory.provideNoteDao(provideEdifyDatabaseProvider.get());
    }

    private ChatDao chatDao() {
      return DatabaseModule_ProvideChatDaoFactory.provideChatDao(provideEdifyDatabaseProvider.get());
    }

    private UserResponseDao userResponseDao() {
      return DatabaseModule_ProvideUserResponseDaoFactory.provideUserResponseDao(provideEdifyDatabaseProvider.get());
    }

    private ChapterStatsDao chapterStatsDao() {
      return DatabaseModule_ProvideChapterStatsDaoFactory.provideChapterStatsDao(provideEdifyDatabaseProvider.get());
    }

    private GeneratedQuestDao generatedQuestDao() {
      return DatabaseModule_ProvideGeneratedQuestDaoFactory.provideGeneratedQuestDao(provideEdifyDatabaseProvider.get());
    }

    private UserProfileDao userProfileDao() {
      return DatabaseModule_ProvideUserProfileDaoFactory.provideUserProfileDao(provideEdifyDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideEdifyDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<EdifyDatabase>(singletonCImpl, 1));
      this.providePromptServiceProvider = DoubleCheck.provider(new SwitchingProvider<PromptService>(singletonCImpl, 3));
      this.provideGemmaServiceProvider = DoubleCheck.provider(new SwitchingProvider<GemmaService>(singletonCImpl, 2));
      this.provideQuestGenerationServiceProvider = DoubleCheck.provider(new SwitchingProvider<QuestGenerationService>(singletonCImpl, 4));
      this.learningRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<LearningRepository>(singletonCImpl, 0));
      this.questRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<QuestRepository>(singletonCImpl, 5));
      this.userProfileRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UserProfileRepository>(singletonCImpl, 6));
      this.chatResponseServiceProvider = DoubleCheck.provider(new SwitchingProvider<ChatResponseService>(singletonCImpl, 7));
      this.questScoringServiceProvider = DoubleCheck.provider(new SwitchingProvider<QuestScoringService>(singletonCImpl, 8));
    }

    @Override
    public void injectEdifyApplication(EdifyApplication edifyApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.edify.learning.data.repository.LearningRepository 
          return (T) new LearningRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.subjectDao(), singletonCImpl.chapterDao(), singletonCImpl.noteDao(), singletonCImpl.chatDao(), singletonCImpl.userResponseDao(), singletonCImpl.chapterStatsDao(), singletonCImpl.generatedQuestDao(), singletonCImpl.provideGemmaServiceProvider.get(), singletonCImpl.provideQuestGenerationServiceProvider.get());

          case 1: // com.edify.learning.data.database.EdifyDatabase 
          return (T) DatabaseModule_ProvideEdifyDatabaseFactory.provideEdifyDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.edify.learning.data.service.GemmaService 
          return (T) DatabaseModule_ProvideGemmaServiceFactory.provideGemmaService(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.providePromptServiceProvider.get());

          case 3: // com.edify.learning.data.service.PromptService 
          return (T) DatabaseModule_ProvidePromptServiceFactory.providePromptService(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.edify.learning.data.service.QuestGenerationService 
          return (T) DatabaseModule_ProvideQuestGenerationServiceFactory.provideQuestGenerationService(singletonCImpl.chapterStatsDao(), singletonCImpl.generatedQuestDao(), singletonCImpl.chapterDao(), singletonCImpl.noteDao(), singletonCImpl.chatDao(), singletonCImpl.subjectDao(), singletonCImpl.provideGemmaServiceProvider.get(), singletonCImpl.providePromptServiceProvider.get());

          case 5: // com.edify.learning.data.repository.QuestRepository 
          return (T) new QuestRepository(singletonCImpl.generatedQuestDao(), singletonCImpl.chapterStatsDao(), singletonCImpl.chapterDao(), singletonCImpl.subjectDao(), singletonCImpl.provideQuestGenerationServiceProvider.get());

          case 6: // com.edify.learning.data.repository.UserProfileRepository 
          return (T) new UserProfileRepository(singletonCImpl.userProfileDao());

          case 7: // com.edify.learning.data.service.ChatResponseService 
          return (T) new ChatResponseService(singletonCImpl.learningRepositoryProvider.get(), singletonCImpl.questRepositoryProvider.get(), singletonCImpl.chatDao(), singletonCImpl.provideGemmaServiceProvider.get());

          case 8: // com.edify.learning.domain.service.QuestScoringService 
          return (T) new QuestScoringService(singletonCImpl.chapterStatsDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}

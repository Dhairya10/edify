package com.edify.learning.data.repository;

import com.edify.learning.data.dao.UserProfileDao;
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
public final class UserProfileRepository_Factory implements Factory<UserProfileRepository> {
  private final Provider<UserProfileDao> userProfileDaoProvider;

  public UserProfileRepository_Factory(Provider<UserProfileDao> userProfileDaoProvider) {
    this.userProfileDaoProvider = userProfileDaoProvider;
  }

  @Override
  public UserProfileRepository get() {
    return newInstance(userProfileDaoProvider.get());
  }

  public static UserProfileRepository_Factory create(
      Provider<UserProfileDao> userProfileDaoProvider) {
    return new UserProfileRepository_Factory(userProfileDaoProvider);
  }

  public static UserProfileRepository newInstance(UserProfileDao userProfileDao) {
    return new UserProfileRepository(userProfileDao);
  }
}

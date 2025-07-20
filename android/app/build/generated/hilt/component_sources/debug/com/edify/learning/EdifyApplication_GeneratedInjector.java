package com.edify.learning;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = EdifyApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface EdifyApplication_GeneratedInjector {
  void injectEdifyApplication(EdifyApplication edifyApplication);
}

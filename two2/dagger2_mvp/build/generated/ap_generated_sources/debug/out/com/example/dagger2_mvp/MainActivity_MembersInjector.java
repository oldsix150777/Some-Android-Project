// Generated by Dagger (https://dagger.dev).
package com.example.dagger2_mvp;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<MvpPresenter> mvpPresenterProvider;

  public MainActivity_MembersInjector(Provider<MvpPresenter> mvpPresenterProvider) {
    this.mvpPresenterProvider = mvpPresenterProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<MvpPresenter> mvpPresenterProvider) {
    return new MainActivity_MembersInjector(mvpPresenterProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectMvpPresenter(instance, mvpPresenterProvider.get());
  }

  @InjectedFieldSignature("com.example.dagger2_mvp.MainActivity.mvpPresenter")
  public static void injectMvpPresenter(MainActivity instance, MvpPresenter mvpPresenter) {
    instance.mvpPresenter = mvpPresenter;
  }
}

package com.lany192.box.demo.ui.main.index.girl;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.lifecycle.ViewModelProvider;
import com.github.lany192.arch.items.ItemsViewModel;
import com.github.lany192.arch.items.PageFragment;
import dagger.hilt.android.flags.FragmentGetContextFix;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.managers.FragmentComponentManager;
import dagger.hilt.internal.GeneratedComponentManagerHolder;
import dagger.hilt.internal.Preconditions;
import dagger.hilt.internal.UnsafeCasts;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;

/**
 * A generated base class to be extended by the @dagger.hilt.android.AndroidEntryPoint annotated class. If using the Gradle plugin, this is swapped as the base class via bytecode transformation.
 */
public abstract class Hilt_GirlFragment<VM extends ItemsViewModel> extends PageFragment<VM> implements GeneratedComponentManagerHolder {
  private ContextWrapper componentContext;

  private boolean disableGetContextFix;

  private volatile FragmentComponentManager componentManager;

  private final Object componentManagerLock = new Object();

  private boolean injected = false;

  @Override
  @CallSuper
  public void onAttach(Context context) {
    super.onAttach(context);
    initializeComponentContext();
    inject();
  }

  @Override
  @SuppressWarnings("deprecation")
  @CallSuper
  @MainThread
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Preconditions.checkState(componentContext == null || FragmentComponentManager.findActivity(componentContext) == activity, "onAttach called multiple times with different Context! Hilt Fragments should not be retained.");
    initializeComponentContext();
    inject();
  }

  private void initializeComponentContext() {
    if (componentContext == null) {
      // Note: The LayoutInflater provided by this componentContext may be different from super Fragment's because we getting it from base context instead of cloning from the super Fragment's LayoutInflater.
      componentContext = FragmentComponentManager.createContextWrapper(super.getContext(), this);
      disableGetContextFix = FragmentGetContextFix.isFragmentGetContextFixDisabled(super.getContext());
    }
  }

  @Override
  public Context getContext() {
    if (super.getContext() == null && !disableGetContextFix) {
      return null;
    }
    initializeComponentContext();
    return componentContext;
  }

  @Override
  public LayoutInflater onGetLayoutInflater(Bundle savedInstanceState) {
    LayoutInflater inflater = super.onGetLayoutInflater(savedInstanceState);
    return inflater.cloneInContext(FragmentComponentManager.createContextWrapper(inflater, this));
  }

  @Override
  public final Object generatedComponent() {
    return this.componentManager().generatedComponent();
  }

  protected FragmentComponentManager createComponentManager() {
    return new FragmentComponentManager(this);
  }

  @Override
  public final FragmentComponentManager componentManager() {
    if (componentManager == null) {
      synchronized (componentManagerLock) {
        if (componentManager == null) {
          componentManager = createComponentManager();
        }
      }
    }
    return componentManager;
  }

  protected void inject() {
    if (!injected) {
      injected = true;
      ((GirlFragment_GeneratedInjector) this.generatedComponent()).injectGirlFragment(UnsafeCasts.<GirlFragment>unsafeCast(this));
    }
  }

  @Override
  public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
    return DefaultViewModelFactories.getFragmentFactory(this, super.getDefaultViewModelProviderFactory());
  }
}

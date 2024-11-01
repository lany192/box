package com.lany192.box.demo.ui.main.menus;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2 = {"Lcom/lany192/box/demo/ui/main/menus/MenusViewModel;", "Lcom/github/lany192/arch/viewmodel/LifecycleViewModel;", "repository", "Lcom/lany192/box/network/repository/BoxRepository;", "(Lcom/lany192/box/network/repository/BoxRepository;)V", "getRepository", "()Lcom/lany192/box/network/repository/BoxRepository;", "onLazyLoad", "", "demo_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class MenusViewModel extends com.github.lany192.arch.viewmodel.LifecycleViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.lany192.box.network.repository.BoxRepository repository = null;
    
    @javax.inject.Inject
    public MenusViewModel(@org.jetbrains.annotations.NotNull
    com.lany192.box.network.repository.BoxRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.lany192.box.network.repository.BoxRepository getRepository() {
        return null;
    }
    
    @java.lang.Override
    protected void onLazyLoad() {
    }
}
package com.lany192.box.demo.ui.goods;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\r"}, d2 = {"Lcom/lany192/box/demo/ui/goods/GoodsViewModel;", "Lcom/github/lany192/arch/items/ItemsViewModel;", "service", "Lcom/lany192/box/network/data/api/ApiService;", "(Lcom/lany192/box/network/data/api/ApiService;)V", "getService", "()Lcom/lany192/box/network/data/api/ApiService;", "setService", "loadMoreEnable", "", "request", "", "refresh", "demo_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class GoodsViewModel extends com.github.lany192.arch.items.ItemsViewModel {
    @org.jetbrains.annotations.NotNull
    private com.lany192.box.network.data.api.ApiService service;
    
    @javax.inject.Inject
    public GoodsViewModel(@org.jetbrains.annotations.NotNull
    com.lany192.box.network.data.api.ApiService service) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.lany192.box.network.data.api.ApiService getService() {
        return null;
    }
    
    public final void setService(@org.jetbrains.annotations.NotNull
    com.lany192.box.network.data.api.ApiService p0) {
    }
    
    @java.lang.Override
    public boolean loadMoreEnable() {
        return false;
    }
    
    @java.lang.Override
    public void request(boolean refresh) {
    }
}
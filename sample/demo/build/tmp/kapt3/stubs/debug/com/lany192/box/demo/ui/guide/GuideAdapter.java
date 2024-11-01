package com.lany192.box.demo.ui.guide;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0007H\u0014J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0007R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0016"}, d2 = {"Lcom/lany192/box/demo/ui/guide/GuideAdapter;", "Lcom/github/lany192/arch/adapter/BindingAdapter;", "", "Lcom/lany192/box/demo/databinding/ItemGuideBinding;", "data", "", "height", "", "listener", "Lcom/lany192/box/demo/ui/guide/OnGuideListener;", "(Ljava/util/List;ILcom/lany192/box/demo/ui/guide/OnGuideListener;)V", "currentPosition", "getHeight", "()I", "getListener", "()Lcom/lany192/box/demo/ui/guide/OnGuideListener;", "convert", "", "binding", "item", "position", "setCurrentPosition", "demo_debug"})
public final class GuideAdapter extends com.github.lany192.arch.adapter.BindingAdapter<java.lang.String, com.lany192.box.demo.databinding.ItemGuideBinding> {
    private final int height = 0;
    @org.jetbrains.annotations.NotNull
    private final com.lany192.box.demo.ui.guide.OnGuideListener listener = null;
    private int currentPosition = 0;
    
    public GuideAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> data, int height, @org.jetbrains.annotations.NotNull
    com.lany192.box.demo.ui.guide.OnGuideListener listener) {
        super(null);
    }
    
    public final int getHeight() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.lany192.box.demo.ui.guide.OnGuideListener getListener() {
        return null;
    }
    
    @java.lang.Override
    protected void convert(@org.jetbrains.annotations.NotNull
    com.lany192.box.demo.databinding.ItemGuideBinding binding, @org.jetbrains.annotations.NotNull
    java.lang.String item, int position) {
    }
    
    public final void setCurrentPosition(int position) {
    }
}
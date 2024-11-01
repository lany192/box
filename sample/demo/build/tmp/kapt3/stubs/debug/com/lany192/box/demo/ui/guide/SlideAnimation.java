package com.lany192.box.demo.ui.guide;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/lany192/box/demo/ui/guide/SlideAnimation;", "", "animationUpdateListener", "Lcom/lany192/box/demo/ui/guide/AnimationUpdateListener;", "(Lcom/lany192/box/demo/ui/guide/AnimationUpdateListener;)V", "animateCoordinateX", "", "valueAnimator", "Landroid/animation/ValueAnimator;", "schedule", "", "offset", "", "updateValue", "start", "end", "Companion", "demo_debug"})
public final class SlideAnimation {
    private int animateCoordinateX = 0;
    @org.jetbrains.annotations.NotNull
    private final android.animation.ValueAnimator valueAnimator = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PN_ANIMATE_COORDINATE_X = "animate_coordinate_x";
    public static final int DEFAULT_DURATION = 3000;
    @org.jetbrains.annotations.NotNull
    public static final com.lany192.box.demo.ui.guide.SlideAnimation.Companion Companion = null;
    
    public SlideAnimation(@org.jetbrains.annotations.NotNull
    com.lany192.box.demo.ui.guide.AnimationUpdateListener animationUpdateListener) {
        super();
    }
    
    public final void schedule(float offset) {
    }
    
    public final void updateValue(int start, int end) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/lany192/box/demo/ui/guide/SlideAnimation$Companion;", "", "()V", "DEFAULT_DURATION", "", "PN_ANIMATE_COORDINATE_X", "", "demo_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}
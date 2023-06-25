package cn.smallplants.client.lifecycle;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.lany192.interfaces.SimpleActivityLifecycleCallbacks;
import com.github.lany192.log.XLog;
import com.umeng.analytics.MobclickAgent;

/**
 * Activity生命周期
 */
public final class ActivityLifecycle implements SimpleActivityLifecycleCallbacks {

    private final FragmentLifecycle fragmentLifecycle = new FragmentLifecycle();

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        XLog.tag(activity.getClass().getSimpleName()).i("onActivityCreated");
        //给Activity界面注入参数
        ARouter.getInstance().inject(activity);
//        PushAgent.getInstance(activity).onAppStart();
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, false);
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        MobclickAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        MobclickAgent.onPause(activity);
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        XLog.tag(activity.getClass().getSimpleName()).i("onActivityDestroyed");
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycle);
        }
    }
}

package com.lany.box.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.inputmethod.EditorInfo;

import com.hjq.toast.ToastUtils;
import com.lany.box.activity.DaggerActivity;
import com.lany.box.adapter.ViewPagerAdapter;
import com.lany.box.dialog.InputDialog;
import com.lany.box.entity.TabItem;
import com.lany.box.sample.filter.MoneyInputFilter;
import com.lany.box.utils.DeviceUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends DaggerActivity implements MainContract.View {
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;
    @Inject
    MainPresenter mPresenter;

    @Override
    protected boolean isStatusBarDarkFont() {
        return false;
    }

    @Override
    protected boolean hasBackBtn() {
        return false;
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_main;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem("item1", new HelloFragment()));
        items.add(new TabItem("item2", new HelloFragment()));
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), items));
//
//        findViewById(R.id.download_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.sayClick();
//
////                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "gradle-4.4-all.zip";
////                String fileUrl = "https://downloads.gradle.org/distributions/gradle-4.4-all.zip";
////                FileDownloader.getImpl().create(fileUrl)
////                        .setPath(path)
////                        .setListener(new SimpleFileDownloadListener() {
////
////                            @Override
////                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
////                                log.i("下载进度: " + (soFarBytes / (float) totalBytes * 100) + "%");
////                            }
////
////                            @Override
////                            protected void completed(BaseDownloadTask task) {
////                                log.i("completed: 下载完成" + task.getUrl() + " 目标文件路径：" + task.getTargetFilePath());
////                            }
////
////                            @Override
////                            protected void error(BaseDownloadTask task, Throwable e) {
////                                log.i("error: " + e.getMessage());
////                            }
////                        }).start();
//            }
//        });


        ssss();
    }

    private void ssss() {
        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable disposable = rxPermissions
                .requestEach(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            ToastUtils.show("唯一id:" + DeviceUtils.getUniqueDeviceId(self));
                            // 用户已经同意该权限
                            Log.d(TAG, permission.name + " 通过授权");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + "授权失败。用户拒绝了该权限，没有选中『不再询问』");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + "授权失败。用户拒绝了该权限，且选中『不再询问』");
                        }
                    }
                });
    }

    @OnClick(R.id.my_button)
    void btnClicked() {
        mPresenter.sayClick();
    }

    @Override
    public void sayHello(String hello) {
        //ToastUtils.show(hello);


        InputDialog dialog = new InputDialog();
        dialog.setTitle("金额");
        dialog.setHint("请输入金额");
        dialog.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
        dialog.addInputFilter(new MoneyInputFilter());
        dialog.setMaxLength(5);
        dialog.setButtonText("提交");
        dialog.setOnInputListener(new InputDialog.OnInputListener() {
            @Override
            public void onResult(CharSequence result) {

            }
        });
        dialog.show(this);


//
//        RxPermissions rxPermissions = new RxPermissions(this);
//        //rxPermissions.setLogging(true);
//        Disposable disposable = rxPermissions
//                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_CALENDAR,
//                        Manifest.permission.READ_CALL_LOG,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.READ_SMS,
//                        Manifest.permission.RECORD_AUDIO,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.CALL_PHONE,
//                        Manifest.permission.SEND_SMS)
//                .subscribe(new Consumer<Permission>() {
//                    @Override
//                    public void accept(Permission permission) throws Exception {
//                        if (permission.granted) {
//                            // 用户已经同意该权限
//                            Log.d(TAG, permission.name + " 通过授权");
//                            ToastUtils.show(hello);
//                        } else if (permission.shouldShowRequestPermissionRationale) {
//                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                            Log.d(TAG, permission.name + "授权失败。用户拒绝了该权限，没有选中『不再询问』");
//                        } else {
//                            // 用户拒绝了该权限，并且选中『不再询问』
//                            Log.d(TAG, permission.name + "授权失败。用户拒绝了该权限，且选中『不再询问』");
//                        }
//                    }
//                });

    }
}

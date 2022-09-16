[![](https://jitpack.io/v/lany192/box.svg)](https://jitpack.io/#lany192/box)

实验测试库，变动频繁，请勿用于生产环境

# 快速开发框架

> * MVVM
> * ViewModel
> * rxjava3
> * rxandroid3
> * retrofit2
> * Hilt
> * OkHttp3
>
使用MVVM+rxjava3+rxandroid3+retrofit2+Dagger2+OkHttp3组合封装

## 引入代码仓库

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

	dependencies {
        implementation 'com.github.lany192.box:arch:master-SNAPSHOT'
        implementation 'com.github.lany192.box:view:master-SNAPSHOT'
        implementation 'com.github.lany192.box:core:master-SNAPSHOT'
        implementation 'com.github.lany192.box:dialog:master-SNAPSHOT'
        implementation 'com.github.lany192.box:update:master-SNAPSHOT'
        implementation 'com.github.lany192.box:time:master-SNAPSHOT'
        implementation 'com.github.lany192.box:crop:master-SNAPSHOT'
        implementation 'com.github.lany192.box:matisse:master-SNAPSHOT'
        implementation 'com.github.lany192.box:permission:master-SNAPSHOT'
        implementation 'com.github.lany192.box:tablayout:master-SNAPSHOT'
        implementation 'com.github.lany192.box:link:master-SNAPSHOT'
        implementation 'com.github.lany192.box:html:master-SNAPSHOT'
        implementation 'com.github.lany192.box:richeditor:master-SNAPSHOT'
        implementation 'com.github.lany192.box:webview:master-SNAPSHOT'
        implementation 'com.github.lany192.box:scanner:master-SNAPSHOT'
        implementation 'com.github.lany192.box:cropper:master-SNAPSHOT'
	}

## 初始化

在Application类中的onCreate初始化

    public class SampleApp extends Application {
    
        @Override
        public void onCreate() {
            super.onCreate();
            Box.get().init(this);
        }
    
    }
    
## 第三方库说明
开发语言：Java和kotlin，以kotlin为主
架构类型：MVVM
下载框架：okdownload
基础支持库：androidx
网络请求：retrofit2
依赖注入：hilt
界面路由：ARouter
异步框架：协程/rxjava
数据库：room
图片加载：glide
多渠道打包：walle
图片压缩：luban
键值对存储：MMKV
日志工具：xlog
下拉刷新控件：SmartRefreshLayout
web内核：X5
状态栏工具：ImmersionBar
列表/多布局适配器：BaseRecyclerViewAdapterHelper
背景控件：BackgroundLibrary
权限工具：rxpermissions
事件通知：eventbus
Tab控件：FlycoTabLayout

[![](https://jitpack.io/v/lany192/box.svg)](https://jitpack.io/#lany192/box)

实验测试库，变动频繁，请勿用于生产环境
仅供学习使用，不

# 快速开发框架

> * MVVM
> * ViewModel
> * rxjava3
> * rxandroid3
> * retrofit2
> * Hilt
> * OkHttp3
>
使用MVVM+rxjava3+rxandroid3+retrofit2+Hilt+OkHttp3组合封装

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
        implementation 'com.github.lany192.box:tablayout:master-SNAPSHOT'
        implementation 'com.github.lany192.box:html:master-SNAPSHOT'
        implementation 'com.github.lany192.box:toolkit:master-SNAPSHOT'
        implementation 'com.github.lany192.box:blackbox:master-SNAPSHOT'
        implementation 'com.github.lany192.box:video:master-SNAPSHOT'
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
    
    键值对存储：腾讯MMKV
    
    日志工具：腾讯xlog
    
    下拉刷新控件：SmartRefreshLayout
    
    web内核：腾讯X5
    
    状态栏工具：ImmersionBar
    
    列表/多布局适配器：BaseRecyclerViewAdapterHelper
    
    背景控件：BackgroundLibrary
    
    权限工具：rxpermissions
    
    事件通知：eventbus
    
    Tab控件：FlycoTabLayout

# 指令打包

```bash
gradlew app:demo:assembleDevelop --debug --stacktrace
```
```bash
gradlew app:avatar:assembleDevelop --debug --stacktrace
```
## 查看手机当前界面activity信息

```bash
adb shell "dumpsys activity top | grep ACTIVITY | tail -n 1"
```

## 一键本地打包

点击左边绿色箭头，开始执行本地打包任务

```bash
# 测试包
gradle Develop

# 正式包
gradle Release
```

## Star History

[![Stargazers over time](https://starchart.cc/lany192/box.svg?variant=adaptive)](https://starchart.cc/lany192/box)

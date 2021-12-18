[![](https://jitpack.io/v/lany192/Box.svg)](https://jitpack.io/#lany192/Box)

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

## 添加依赖

	dependencies {
		implementation 'com.github.lany192.box:view:+'
		implementation 'com.github.lany192.box:core:+'
		implementation 'com.github.lany192.box:dialog:+'
		implementation 'com.github.lany192.box:arch:+'
		implementation 'com.github.lany192.box:update:+'
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
    

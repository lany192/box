[![](https://jitpack.io/v/lany192/Box.svg)](https://jitpack.io/#lany192/Box)

# 快速开发框架

> * MVVM
> * rxjava3
> * rxandroid3
> * retrofit2
> * Dagger2
> * OkHttp3
> 
使用MVP+rxjava3+rxandroid3+retrofit2+Dagger2+OkHttp3组合封装

## 引入代码仓库

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
	
## 添加依赖

	dependencies {
	    implementation 'com.github.lany192.box:arch:3.8.3'
            implementation 'com.github.lany192.box:view:3.8.3'
            implementation 'com.github.lany192.box:dialog:3.8.3'
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

## 其它注意事项

如果需要使用dagger，Activity继承DaggerActivity，Fragment继承DaggerFragment
    

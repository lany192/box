[![](https://jitpack.io/v/lany192/Box.svg)](https://jitpack.io/#lany192/Box)

# 快速开发框架

> * MVP
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
	        //必须
	        implementation 'com.github.lany192:Box:latest.integration'
            implementation 'com.jakewharton:butterknife:10.2.1'
            annotationProcessor 'com.jakewharton:butterknife-compiler:10.2.1'
            //选择加入，如果使用了dagger2需要添加
            implementation 'com.google.dagger:dagger-android-support:2.24'
            annotationProcessor 'com.google.dagger:dagger-compiler:2.24'
            annotationProcessor 'com.google.dagger:dagger-android-processor:2.24'
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
    

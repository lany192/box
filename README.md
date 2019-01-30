[![](https://jitpack.io/v/lany192/Box.svg)](https://jitpack.io/#lany192/Box)

# 快速开发框架 

使用MVP+retrofit2+Dagger2+OkHttp3组合封装

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
            	implementation 'com.jakewharton:butterknife:10.0.0'
            	annotationProcessor 'com.jakewharton:butterknife-compiler:10.0.0'
	}
	
## 初始化

在Application类中的onCreate初始化

    public class SampleApp extends Application {
    
        @Override
        public void onCreate() {
            super.onCreate();
            Box.of().init(this);
        }
    
    }

## gradle.properties

	android.useAndroidX=true
	android.enableJetifier=true
    

#android jar 包文件  在 Terminal 下 执行  ./gradlew makeJar
#引入依赖包rt.jar（jdk路径）
-libraryjars /Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/lib/dt.jar
# 保持相应包名下的类不被混淆
#-keep class com.baiyang.android.** { *; }
#-keep class com.ta.** { *; }
-keep class org.xmlpull.v1.** {*;}
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Appliction
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
-keepattributes Exceptions,InnerClasses,Deprecated,SourceFile,LineNumberTable,EnclosingMethod
## 保留Annotation不混淆
-keepattributes *Annotation*
##保护泛型
-keepattributes Signature
## 抛出异常时保留代码行号
#-keepattributes SourceFile,LineNumberTable
##打印混淆的详细信息
## 这句话能够使我们的项目混淆后产生映射文件
## 包含有类名->混淆后类名的映射关系
-verbose
-dontoptimize
##忽略警告
-ignorewarnings
##保证是独立的jar,没有任何项目引用,如果不写就会认为我们所有的代码是无用的,从而把所有的代码压缩掉,导出一个空的jar
-dontshrink
##代码迭代优化的次数，默认5
-optimizationpasses 5
#表示混淆时不使用大小写混合类名
#-dontusemixedcaseclassnames
#表示不跳过library中的非public的类
-dontskipnonpubliclibraryclasses
##表示不进行校验,这个校验作用 在java平台上的
-dontpreverify
## 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#
## 指定混淆是采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*
################################# 新增加
#-keepclasseswithmembers
#-libraryjars libs/classes.jar
#-libraryjars libs/commons-httpclient-3.1.jar

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keep class **.R$* { *; }
#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.* { *;}
-dontwarn com.google.gson.**


-keep class qge.cn.com.qgenglish.app.**{*;}

# ./gradlew makeJar 命令执行失败时,先Rebuld project 一下,然后在执行
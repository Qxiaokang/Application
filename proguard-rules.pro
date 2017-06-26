# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\studio\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#指定代码的压缩级别
-optimizationpasses 5
#不混合大小写
-dontusemixedcaseclassnames
#不忽略非公共的库
-dontskipnonpubliclibraryclasses
#不优化输入的类文件
-dontoptimize
#混淆是否记录日志
-verbose
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
-ignorewarning                   # 忽略警告
#保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.preference.Preference



# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

#保留R下面的资源
-keepclassmembers class **.R$* {
      public static <fields>;
}
#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
      public static **[] values();
      public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {      # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {      # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keep class * implements android.os.Parcelable {         # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable #不混淆Serializable

#-libraryjars libs/xxx.jar        #混淆第三方jar包，其中xxx为jar包名
#-keep class com.xxx.**{*;}       #不混淆某个包内的所有文件
#-dontwarn com.xxx**              #忽略某个包的警告

#生成日志数据，gradle build时在本项目根目录输出
#-dump class_files.txt            #apk包内所有class的内部结构
#-printseeds seeds.txt            #未混淆的类和成员
#-printusage unused.txt           #打印未被使用的代码
#-printmapping mapping.txt        #混淆前后的映射

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
native <methods>;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature  #不混淆泛型
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
##这里需要改成解析到哪个  javabean
-keep class com.appstore.bean.** { *; }
##---------------End: proguard configuration for Gson  ----------
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
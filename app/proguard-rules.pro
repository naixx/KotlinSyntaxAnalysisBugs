# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Programs\sdk/tools/proguard/proguard-android.txt
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
-dontoptimize
-dontobfuscate


-keepattributes Exceptions,*Annotation*,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,EnclosingMethod,JavascriptInterface
#######
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
########
-dontwarn javax.annotation.**
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn retrofit.**
-dontwarn rx.**
-dontwarn kotlin.dom.*
-dontwarn org.androidannotations.api.rest.*
-dontwarn java.lang.invoke** #retrolambda
-dontwarn com.google.common.collect** #stripped guava

#jackson
-keepattributes *Annotation*
-dontwarn com.fasterxml.jackson.databind**
-keepnames class com.fasterxml.jackson.** { *; }
-keepattributes Signature
-keep class me.packbag.android.db.model.* {
  public *** get*();
  public void set*(***);
}

#retrofit
-keepattributes *Annotation*
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
@retrofit.http.* <methods>; }
-keepattributes Signature

#EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}
# Only required if you use AsyncExecutor
#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}

-keep class android.support.v7.widget.SearchView { *; }

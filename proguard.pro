-injars app/libs/guava-18.0.jar
-outjars app/libs/guava-18.0-stripped.jar
#-libraryjars android-sdk/extras/android/support/v4/android-support-v4.jar
#-libraryjars android-sdk/platforms/android-20/android.jar
-libraryjars <java.home>/lib/rt.jar
-libraryjars jsr305-3.0.0.jar
-dontoptimize
-dontobfuscate
#-dontwarn com.google.**.R
#-dontwarn com.google.**.R$*
-dontnote
-keep public class com.google.android.gms.ads.** {
 public protected *;
}
-keep class com.google.common.collect.Iterables{ *; }
-keep class net.tribe7.common.collect.Lists{ *; }
-keep class net.tribe7.common.collect.Maps{ *; }
-keep class com.google.common.collect.FluentIterable{ *; }
#-keep class com.google.common.collect.ImmutableList{*;}
-keep class com.google.common.collect.Immutable*{*;}
-keep class com.google.common.collect.Multimaps{*;}
-keep class com.google.common.collect.Hashing{*;}
#-keep class com.google.common.collect.ImmutableMap{*;}

-keep class com.google.common.collect.Ordering{ *; }
-keep class com.google.common.base.Objects{ *; }
-keep class com.google.common.base.Optional*{*;}
-keep class com.google.common.base.Equivalence*{*;}
-keep class com.google.common.base.Ascii{*;}

-keep class com.google.common.base.Joiner{*;}
-keep class com.google.common.base.Splitter{*;}


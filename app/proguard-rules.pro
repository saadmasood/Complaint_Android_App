# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Nadeem Iqbal/Library/Android/sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keepclassmembers class com.fiberlink.maas360.android.ipc.model.* { ;}
-keepclassmembers class com.fiberlink.secure.EncryptionInfo { *;}
-keepclassmembers class com.fiberlink.secure.EncryptionInfo$EncryptionAlgo { *;}

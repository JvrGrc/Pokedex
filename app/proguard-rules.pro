# Mantén los atributos de la línea de origen para ayudar en la depuración de rastreos de pila
-keepattributes SourceFile,LineNumberTable

# Mantén las anotaciones
-keepattributes *Annotation*

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature

# Retrofit 2.6.0 y Gson converter
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Picasso
-keep class com.squareup.picasso.** { *; }
-dontwarn com.squareup.picasso.**

# ConstraintLayout
-keep class androidx.constraintlayout.** { *; }
-dontwarn androidx.constraintlayout.**

# Material Components
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# AndroidX
-keep class androidx.appcompat.** { *; }
-dontwarn androidx.appcompat.**

# JUnit
-dontwarn junit.**

# Espresso
-dontwarn androidx.test.espresso.**
-keep class androidx.test.espresso.** { *; }

# Android Test
-dontwarn androidx.test.ext.junit.**
-keep class androidx.test.ext.junit.** { *; }

# Mantén todas las clases que implementan Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Mantén clases con métodos nativos
-keepclasseswithmembernames class * {
    native <methods>;
}

# Si tu aplicación usa Reflection, mantén las clases y métodos necesarios
-keepclassmembers class * {
    ** *;
}

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }

# project specific.
-keep,includedescriptorclasses class com.example.mmm_mobile.data.entities.**$$serializer { *; }
-keepclassmembers class com.example.mmm_mobile.data.entities.** { *** Companion; }
-keepclasseswithmembers class com.example.mmm_mobile.data.entities.** { kotlinx.serialization.KSerializer serializer(...); }
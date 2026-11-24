plugins {
    java
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(platform(libs.spring.bom))
    implementation(libs.spring.kafka)
}
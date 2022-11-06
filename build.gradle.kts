plugins {
    application
}

java.sourceSets["main"].java {
    srcDir("src/gen/java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.formdev:flatlaf:2.6")
}

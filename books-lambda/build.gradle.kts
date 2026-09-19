plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.amazonaws:aws-lambda-java-core:1.4.0")
    implementation("com.amazonaws:aws-lambda-java-events:3.16.1")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
    options.encoding = "UTF-8"
}

// Lambda用のZIP:クラスファイルをルートに、依存JARをlib/に入れる
tasks.register<Zip>("buildZip") {
    from(tasks.compileJava)
    from(tasks.processResources)
    into("lib") {
        from(configurations.runtimeClasspath)
    }
}

tasks.build {
    dependsOn("buildZip")
}
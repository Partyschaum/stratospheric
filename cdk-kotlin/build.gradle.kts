plugins {
  kotlin("jvm") version "1.8.0"
  application
}

group = "com.thatveryfewthings"
version = "0.1-SNAPSHOT"
description = "cdk-kotlin"

repositories {
  mavenCentral()
}

val awsCdkVersion = "2.63.0"
val awsCdkConstructsVersion = "10.1.237"
val stratosphericCdkConstructsVersion = "0.1.13"
val jUnitVersion = "5.9.2"

val cloudformationApp = project.properties["app"]

dependencies {
  implementation("software.amazon.awscdk:aws-cdk-lib:$awsCdkVersion")
  implementation("software.constructs:constructs:$awsCdkConstructsVersion")
  implementation("dev.stratospheric:cdk-constructs:$stratosphericCdkConstructsVersion")

  testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
  testImplementation(kotlin("test"))
}

tasks.withType<Test>() {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(17)
}

val requireCloudformationApp = tasks.register("RequireCloudformationApp") {
  require(project.properties["app"] != null) {
    "Specify the Cloudformation app as Gradle argument with -Papp=<app_name>!"
  }
}

tasks.withType<JavaExec> {
  dependsOn(requireCloudformationApp)
}

application {
  mainClass.set("com.thatveryfewthings.$cloudformationApp")
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	dependencies {
		classpath("mysql:mysql-connector-java:5.1.49")
	}
}

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "co.vaale"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// https://mvnrepository.com/artifact/org.projectlombok/lombok
	compileOnly("org.projectlombok:lombok:1.18.24")
	annotationProcessor("org.projectlombok:lombok:1.18.24")

	//log4j
	implementation("org.apache.logging.log4j:log4j-core:2.17.1")
	implementation("org.apache.logging.log4j:log4j-api:2.17.1")
	implementation("org.apache.logging.log4j:log4j-to-slf4j:2.17.1")

	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")

	// https://mvnrepository.com/artifact/org.jooq/jooq
	//implementation("org.jooq:jooq:3.18.3")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.jooq:jooq:3.13.2")
	runtimeOnly("mysql:mysql-connector-java")

	//implementation(group = "org.springframework.boot", name = "spring-boot-starter-mail", version = "3.0.1")
	//implementation(group = "javax.mail", name = "mail", version = "1.5.0-b01")

	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	// https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
	implementation ("com.squareup.okhttp3:okhttp:4.9.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//implementation("com.google.cloud:google-cloud-bigquery:2.31.1")

	// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-sns
	implementation("com.amazonaws:aws-java-sdk-sns:1.12.393")


}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

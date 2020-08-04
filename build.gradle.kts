import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "com.practice"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	/**
	 * Spring
	 */
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux:2.3.1.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-security:2.3.1.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:2.3.1.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-mail:2.3.1.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.1.RELEASE")

	/**
	 * Jackson
	 */
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	/**
	 * Kotlin
	 */
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	/**
	 * JWT
	 */
	implementation("io.jsonwebtoken:jjwt:0.9.0")
	implementation("com.nimbusds:nimbus-jose-jwt:6.4.2")

	/**
	 * AWS
	 */
	implementation("com.amazonaws:aws-java-sdk:1.11.826")

	implementation("javax.activation:activation:1.1.1")
	implementation("org.freemarker:freemarker")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	/**
	 * mysql
	 */
	runtimeOnly("dev.miku:r2dbc-mysql")
	runtimeOnly("mysql:mysql-connector-java")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.3.1.RELEASE")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

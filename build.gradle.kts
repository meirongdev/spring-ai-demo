plugins {
	java
	id("org.springframework.boot") version "3.5.8-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.meirong.showcase"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springAiVersion"] = "1.1.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
  // implementation("org.springframework.ai:spring-ai-starter-model-openai")
  // implementation("org.springframework.ai:spring-ai-starter-model-google-genai")
  implementation("org.springframework.ai:spring-ai-starter-model-ollama")
  implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("cn.hutool:hutool-core:5.8.26")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.wiremock.integrations:wiremock-spring-boot:3.10.6")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

plugins {
	java
	id("org.springframework.boot") version "3.0.0"
	id("io.spring.dependency-management") version "1.1.0"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.7.1"
}

group = "com.apium"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
	testImplementation("junit:junit:4.13.2")
	runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.javamoney:moneta:1.4.2")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.apache.avro:avro:1.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

avro {
    isCreateSetters.set(true)
    stringType.set("String")
}

tasks.withType<com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask> {
    setOutputDir(file("/home/oscarlopez1616/projects/personal/priceextractor/src/main/java/com/apium/priceextractor/infrastructure/eda/broker/kafka/IntegrationEvent/"))
    setSource("/home/oscarlopez1616/projects/personal/priceextractor/src/main/java/com/apium/priceextractor/infrastructure/eda/broker/kafka/IntegrationEvent/")
}

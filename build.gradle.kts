plugins {
	id("java")
}

group = "cn.taskeren"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.apache.commons:commons-lang3:3.18.0")
	implementation("com.google.guava:guava:33.4.8-jre")
	implementation("it.unimi.dsi:fastutil:8.5.16")
	implementation("com.google.code.findbugs:jsr305:3.0.2")
	implementation("org.slf4j:slf4j-api:2.0.17")
	implementation("org.jetbrains:annotations:26.0.2")

	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
	useJUnitPlatform()
}

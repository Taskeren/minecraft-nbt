plugins {
	id("java")
	`maven-publish`
}

group = "cn.taskeren"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.slf4j:slf4j-api:2.0.17")
	implementation("org.jetbrains:annotations:26.0.2")

	testImplementation("org.slf4j:slf4j-simple:2.0.17")
	testImplementation(platform("org.junit:junit-bom:5.10.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

tasks.test {
	useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "cn.elytra"
            artifactId = "minecraft-nbt"
            version = project.version.toString()
        }
    }
    repositories {
        maven {
            name = "local"
            url = uri("file://${layout.buildDirectory.get()}/repo")
        }
    }
}

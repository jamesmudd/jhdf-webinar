plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // jHDF - HDF5 lib
    // implementation("io.jhdf:jhdf:0.6.10")

    // jHDF alpha for writing support....
    implementation("io.jhdf:jhdf:0.7.0-alpha")

    // Apache Commons for Array utils etc
    implementation("org.apache.commons:commons-lang3:3.14.0")

    // Logging
    // runtimeOnly("org.slf4j:slf4j-simple:1.7.36")

    // JUnit 5 - not used
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
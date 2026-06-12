plugins {
    java
}

group = "ar.edu.tup.programacion3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 1. Jakarta Persistence API (JPA)
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // 2. Hibernate CORE
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")

    // 3. Driver Base de datos H2
    implementation("com.h2database:h2:2.2.224")

    // Pruebas
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
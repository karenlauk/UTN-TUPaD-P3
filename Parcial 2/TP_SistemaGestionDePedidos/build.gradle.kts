plugins {
    java
}
group = "ar.edu.tup.programacion3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    // 1. Las dependencias validadas por tu corrector
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    implementation("com.h2database:h2:2.2.224")
    // 2. Lombok básico para los métodos accesores
    //compileOnly("org.projectlombok:lombok:1.18.32")
    //annotationProcessor("org.projectlombok:lombok:1.18.32")
    // 3. Testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
tasks.test {
    useJUnitPlatform()
}
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "io.spring.dependency-management" -> {
                    val springDependencyManagementVersion: String by settings
                    useVersion(springDependencyManagementVersion)
                }
                "org.springframework.boot" -> {
                    val springBootVersion: String by settings
                    useVersion(springBootVersion)
                }
            }
        }
    }
}

rootProject.name = "high-stack-spring-async"
include("content-creation")

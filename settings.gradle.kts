pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Matetrip"
include(":app")
include(":core-model")
include(":core-network")
include(":core-datastore")
include(":core-designsystem")
include(":core-repository")
include(":core-common")
include(":feature-login")
include(":feature-onboarding")
include(":feature-home")
include(":feature-mypage")
include(":feature-chatting")

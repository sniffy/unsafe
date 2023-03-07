plugins {
    id("com.itiviti.dotnet") version "1.9.5"
}

project.version = "1.0-SNAPSHOT"

dotnet {

    projectName = "unsafe-ikvm-integration-tests"

    // where dotnet sdk is installed, default is to use the one specified in PATH
    dotnetExecutable = "dotnet"

    // the workingDir of the dotnet cli, default is the current directory
    ////workingDir = "."

    // the solution / csproj to be built, it will search the workingDir if not specified
    solution = "unsafe-ikvm-integration-tests.sln"

    // configuration to be used, default is Release
    configuration = "Release"

    // Log verbosity of dotnet command
    verbosity = "Normal"

    // Stop builds if pre-release is detected, can be used when building release build, default is false
    preReleaseCheck = false

    restore {
        // Specifies to not cache packages and HTTP requests.
        noCache = false

        // Forces all dependencies to be resolved even if the last restore was successful. Specifying this flag is the same as deleting the project.assets.json file.
        force = false

        // Delay dotnet restore until dotnetBuild rather than in evaluation phase, could lead to missing project properties due to missing dependencies
        beforeBuild = false
    }

    build {
        // Any build parameter to be passed to msbuild, as /p:key=value, for example
        ////maxCpuCount = ""

        // Default values applied
        ////version = project.version
        ////packageVersion = project.version
        version = project.version.toString()
        packageVersion = project.version.toString()
    }

    test {
        // filter test to be executed, or use command arguments --dotnet-tests to override (similar to --tests)
        filter = ""

        // test run settings file, no default value
        settings = file(".runsettings")

        // collect code coverage via coverlet, default is true
        collectCoverage = true

        // coverlet output formats, default is opencover
        coverletOutputFormat = "opencover"

        // [sonar aware] coverlet output path, it must be a directory, default is build/reports/coverlet/
        coverletOutput = file("build/reports/coverlet/")

        nunit {
            // [sonar aware] nunit output path, default is build/reports/nunit/
            testOutputXml = file("build/reports/nunit/")
        }
    }

    ////nugetPush {
    ////        // The API key for the server.
    ////        apiKey = ""
    ////        // Nuget feed url, default is DefaultPushSource in Nuget config if not set
    ////        source = ""
    ////}

    ////sonarqube {
    ////    // version of dotnet-sonarscanner to be installed as global dotnet tool, default is latest
    ////    version = '3.7.1'
    ////}
}

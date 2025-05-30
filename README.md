# opcal-cloud-commons
[![pipeline status](https://gitlab.com/opcal-project/opcal-cloud-commons/badges/main/pipeline.svg)](https://gitlab.com/opcal-project/opcal-cloud-commons/-/commits/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=opcal-project_opcal-cloud-commons&metric=alert_status)](https://sonarcloud.io/dashboard?id=opcal-project_opcal-cloud-commons)
[![codecov](https://codecov.io/gl/opcal-project/opcal-cloud-commons/branch/main/graph/badge.svg?token=AEBJ3Z5AJX)](https://codecov.io/gl/opcal-project/opcal-cloud-commons)

commons project module for system

## Release Train Table
| Release  | Branch | Spring Boot | Spring Cloud |
|:--------:|:------:|:-----------:|:------------:|
| 3.5.0.1  |  main  |    3.5.0    |   2025.0.0   |
| 3.4.6.0  | 3.4.x  |    3.4.6    |   2024.0.1   |
| 3.3.12.0 | 3.3.x  |   3.3.12    |   2023.0.5   |

## How to Use
### Using maven parent

Replace {opcal-cloud.version} that if necessary to support required Spring Boot and Spring Cloud version.

```xml
<parent>
    <groupId>xyz.opcal.cloud</groupId>
    <artifactId>opcal-cloud-starter-parent</artifactId>
    <version>{opcal-cloud.version}</version>
    <relativePath/>
</parent>
```

### Using maven dependencyManagement

```xml
<dependencyManagement>
    <dependency>
        <groupId>xyz.opcal.cloud</groupId>
        <artifactId>opcal-cloud-commons-dependencies</artifactId>
        <version>{opcal-cloud.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencyManagement>
```

# opcal-cloud-commons
[![Build Status](https://jnx.opcal.xyz/buildStatus/icon?job=opcal-cloud-commons%2Fmain)](https://jnx.opcal.xyz/view/opcal-project/job/opcal-cloud-commons/job/main/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=gh_opcal-project_opcal-cloud-commons&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=gh_opcal-project_opcal-cloud-commons)

commons project module for system

## Release Train Table
| Release  | Branch | Spring Boot | Spring Cloud |
|:--------:|:------:|:-----------:|:------------:|
| 3.5.11.0 | 3.5.x  |   3.5.11    |   2025.0.1   |

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

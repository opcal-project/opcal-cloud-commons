# opcal-cloud-commons
[![pipeline status](https://gitlab.com/opcal-project/opcal-cloud-commons/badges/main/pipeline.svg)](https://gitlab.com/opcal-project/opcal-cloud-commons/-/commits/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=opcal-project_opcal-cloud-commons&metric=alert_status)](https://sonarcloud.io/dashboard?id=opcal-project_opcal-cloud-commons)
[![codecov](https://codecov.io/gl/opcal-project/opcal-cloud-commons/branch/main/graph/badge.svg?token=AEBJ3Z5AJX)](https://codecov.io/gl/opcal-project/opcal-cloud-commons)

commons project module for system

## Release Train Table
|  Release  |   Branch  | Spring Boot | Spring Cloud |
|   :---:   |   :---:   |    :---:    |     :---:    |
| 3.0.12.0  |   3.0.x   |   3.0.12    |   2022.0.4   |
| 2.7.17.0  |   2.7.x   |   2.7.17    |   2021.0.8   |

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

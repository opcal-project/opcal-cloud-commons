# opcal-cloud-commons
[![pipeline status](https://gitlab.com/opcal-project/opcal-cloud-commons/badges/main/pipeline.svg)](https://gitlab.com/opcal-project/opcal-cloud-commons/-/commits/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=opcal-project_opcal-cloud-commons&metric=alert_status)](https://sonarcloud.io/dashboard?id=opcal-project_opcal-cloud-commons)
[![codecov](https://codecov.io/gl/opcal-project/opcal-cloud-commons/branch/main/graph/badge.svg?token=AEBJ3Z5AJX)](https://codecov.io/gl/opcal-project/opcal-cloud-commons)

commons project module for system

## Release Train Table
|  Release  |   Branch  | Spring Boot | Spring Cloud |
|   :---:   |   :---:   |    :---:    |     :---:    |
| 0.2.6.13  |  0.2.6.x  |   2.6.11    |   2021.0.4   |
| 0.2.5.9   |  0.2.5.x  |   2.5.13    |   2020.0.5   |
| 0.2.4.2   |  0.2.4.x  |   2.4.13    |   2020.0.5   |
| 0.2.3.3   |  0.2.3.x  |   2.3.12    |  Hoxton.SR12 |

## How to Use
### Using maven parent

Replace {opcal-cloud.version} that if necessary to support required Spring Boot and Spring Cloud version.

Version 2.7.1.2, 0.2.6.10 and after
```xml
<parent>
    <groupId>xyz.opcal.cloud</groupId>
    <artifactId>opcal-cloud-starter-parent</artifactId>
    <version>{opcal-cloud.version}</version>
    <relativePath/>
</parent>
```

Before 2.7.1.2, 0.2.6.10
```xml
<parent>
    <groupId>xyz.opcal.cloud</groupId>
    <artifactId>opcal-cloud-parent</artifactId>
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
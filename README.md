# opcal-cloud-commons
[![pipeline status](https://gitlab.com/opcal-project/opcal-cloud-commons/badges/main/pipeline.svg)](https://gitlab.com/opcal-project/opcal-cloud-commons/-/commits/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=opcal-project_opcal-cloud-commons&metric=alert_status)](https://sonarcloud.io/dashboard?id=opcal-project_opcal-cloud-commons)
[![codecov](https://codecov.io/gl/opcal-project/opcal-cloud-commons/branch/main/graph/badge.svg?token=AEBJ3Z5AJX)](https://codecov.io/gl/opcal-project/opcal-cloud-commons)

commons project module for system

## Release Train Table
|  Release  |   Branch  | Spring Boot | Spring Cloud |
|   :---:   |   :---:   |    :---:    |     :---:    |
| 0.2.5.1   |    main   |   2.5.8     |   2020.0.4   |
| 0.2.3.1   |  0.2.3.x  |   2.3.12    |  Hoxton.SR12 |

## How to Use
### Add maven parent

```xml
<parent>
    <groupId>xyz.opcal.cloud</groupId>
    <artifactId>opcal-cloud-parent</artifactId>
    <version>0.2.3.1</version>
    <relativePath/>
</parent>
```
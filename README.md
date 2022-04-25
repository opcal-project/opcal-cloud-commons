# opcal-cloud-commons
[![pipeline status](https://gitlab.com/opcal-project/opcal-cloud-commons/badges/main/pipeline.svg)](https://gitlab.com/opcal-project/opcal-cloud-commons/-/commits/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=opcal-project_opcal-cloud-commons&metric=alert_status)](https://sonarcloud.io/dashboard?id=opcal-project_opcal-cloud-commons)
[![codecov](https://codecov.io/gl/opcal-project/opcal-cloud-commons/branch/main/graph/badge.svg?token=AEBJ3Z5AJX)](https://codecov.io/gl/opcal-project/opcal-cloud-commons)

commons project module for system

## Release Train Table
|  Release  |   Branch  | Spring Boot | Spring Cloud |
|   :---:   |   :---:   |    :---:    |     :---:    |
| 0.2.6.6   |    main   |   2.6.7     |   2021.0.1   |
| 0.2.5.9   |  0.2.5.x  |   2.5.13    |   2020.0.5   |
| 0.2.4.2   |  0.2.4.x  |   2.4.13    |   2020.0.5   |
| 0.2.3.3   |  0.2.3.x  |   2.3.12    |  Hoxton.SR12 |

## How to Use
### Using maven parent

```xml
<parent>
    <groupId>xyz.opcal.cloud</groupId>
    <artifactId>opcal-cloud-parent</artifactId>
    <version>0.2.6.5</version>
    <relativePath/>
</parent>
```
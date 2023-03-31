#!/bin/bash

## install versions
## curl -sL https://raw.githubusercontent.com/gissily/versions-tools/main/install.sh | sudo bash

set -e

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
PLUGINS_DIR_PATH=`dirname ${SCRIPT}`
MAVEN_DIR_PATH=`dirname ${PLUGINS_DIR_PATH}`
CI_DIR_PATH=`dirname ${MAVEN_DIR_PATH}`
export ROOT_PATH=`dirname ${CI_DIR_PATH}`

PLUGIN_CONFIG=${ROOT_PATH}/ci/maven/plugins/plugins.yml
PLUGIN_DEPENDENCIES=${ROOT_PATH}/ci/maven/plugins/plugins.properties

versions ${PLUGIN_CONFIG}

${ROOT_PATH}/ci/script/dependency-version.sh ${PLUGIN_DEPENDENCIES}
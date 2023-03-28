#!/bin/bash

## install props
## curl -sL https://raw.githubusercontent.com/gissily/properties-tools/main/install.sh | sudo bash

set -e

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
SCRIPT_DIR_PATH=`dirname ${SCRIPT}`
CI_DIR_PATH=`dirname ${SCRIPT_DIR_PATH}`
ROOT_PATH=`dirname ${CI_DIR_PATH}`

file=${ROOT_PATH}/dependencies.properties

KEYS=($(props keys ${file}))

for i in "${!KEYS[@]}"
do
  key=${KEYS[${i}]}
  value=$(props value ${file} ${key})
  echo "key: ${key} - value: ${value}"
  ${ROOT_PATH}/mvnw versions:set-property -Dproperty=${key} -DnewVersion=${value} >> /dev/null 2>&1
done

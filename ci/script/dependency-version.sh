#!/bin/bash

set -e

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
SCRIPT_DIR_PATH=`dirname ${SCRIPT}`
CI_DIR_PATH=`dirname ${SCRIPT_DIR_PATH}`
ROOT_PATH=`dirname ${CI_DIR_PATH}`

declare -A props

file=${ROOT_PATH}/dependencies.properties
while IFS='=' read -r key value; do
  if [ -z "${key}" ];then
    continue
  fi
  props[${key}]="${value}"
done < ${file}

for i in "${!props[@]}"
do
  echo "key: $i - value: ${props[$i]}"
  ${ROOT_PATH}/mvnw versions:set-property -Dproperty=${i} -DnewVersion=${props[$i]} >> /dev/null 2>&1
done
#!/bin/bash

set -e

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`

DIR_PATH=`dirname ${SCRIPT}`

declare -A props

file=${DIR_PATH}/dependencies.properties
while IFS='=' read -r key value; do
  if [ -z "${key}" ];then
    continue
  fi
  props[${key}]="${value}"
done < ${file}

for i in "${!props[@]}"
do
  echo "key: $i - value: ${props[$i]}"
  ${DIR_PATH}/mvnw versions:set-property -Dproperty=${i} -DnewVersion=${props[$i]} >> /dev/null 2>&1
done
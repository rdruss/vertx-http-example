#!/usr/bin/env bash
set -e -x
find . | grep openshiftio | grep application | xargs -n 1 oc apply -f
oc new-app --template=vertx-http-booster -p SOURCE_REPOSITORY_URL=https://github.com/openshiftio-vertx-boosters/vertx-http-booster
#!/usr/bin/env bash
set -e -x

source .openshiftio/openshift.sh

# Deploy the templates and required resources
oc apply -f .openshiftio/application.yaml

# Create the application
oc new-app --template=vertx-http-booster -p SOURCE_REPOSITORY_URL=https://github.com/openshiftio-vertx-boosters/vertx-http-booster

# wait for pod to be ready
waitForPodState "http-vertx" "Running"
waitForPodReadiness "http-vertx" 1

mvn verify -Popenshift-it -Denv.init.enabled=false
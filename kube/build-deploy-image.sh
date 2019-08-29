# This script will:
# 1. Build the website docker image
# 2. Upload the image to our repository if registry env variables are defined
set -e

DOCKER_IMAGE_NAME=kabanero-landing 
NAMESPACE=/r/kidus60/kabanero

CUR_DIR="$(cd $(dirname $0) && pwd)"

if [ -z "$DOCKER_IMAGE_TAG" ]; then
    DOCKER_IMAGE_TAG=latest
fi

cd "$CUR_DIR"/../
echo "building docker image $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
docker build -t "$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG" -f "$CUR_DIR"/Dockerfile .

# publish to a registry
if [ -n "$DOCKER_REGISTRY" ] && [ -n "$DOCKER_REGISTRY_USER" ] && [ -n "$DOCKER_REGISTRY_PASSWORD" ]; then
    docker login "--username=${DOCKER_REGISTRY_USER}" "--password=${DOCKER_REGISTRY_PASSWORD}" "${DOCKER_REGISTRY}"

docker push $DOCKER_REGISTRY/$IMAGE_NAME
fi
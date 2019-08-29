# This script will:
# 1. Build the website docker image
# 2. Upload the image to our repository if registry env variables are defined
set -e

DOCKER_IMAGE_NAME=kabanero-landing
NAMESPACE=kabanero

CUR_DIR="$(cd $(dirname $0) && pwd)"

if [ -z "$DOCKER_IMAGE_TAG" ]; then
    DOCKER_IMAGE_TAG=latest
fi

cd "$CUR_DIR"/../
echo "building docker image $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG"
docker build -t "$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG" -f "$CUR_DIR"/Dockerfile .

# publish to a registry
if [ -n "$DOCKER_REGISTRY" ] && [ -n "$DOCKER_REGISTRY_USER" ] && [ -n "$DOCKER_REGISTRY_PASSWORD" ]; then
docker login "--username=kidus60" "--password=Weeman2251"
    docker tag "$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG" "kidus60/kabanero"
    echo "Connecting to $DOCKER_REGISTRY Docker registry as $DOCKER_REGISTRY_USER"



    echo "Pushing $DOCKER_REGISTRY/$NAMESPACE/$DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG image to Docker registry"
    docker push "kidus60/kabanero"
fi
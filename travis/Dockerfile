# Use the official Ruby image as a parent image
FROM ruby:latest

# update the repository sources list
# and install dependencies
# RUN apt-get update \
#     && apt-get -y install maven \
#     && apt-get -y install python3-requests 

RUN apt-get update && \
    apt-get install -y gnupg wget curl unzip --no-install-recommends && \
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list && \
    apt-get update -y && \
    apt-get install -y google-chrome-stable && \
    CHROMEVER=$(google-chrome --product-version | grep -o "[^\.]*\.[^\.]*\.[^\.]*") && \
    DRIVERVER=$(curl -s "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROMEVER") && \
    wget -q --continue -P /chromedriver "http://chromedriver.storage.googleapis.com/$DRIVERVER/chromedriver_linux64.zip" && \
    unzip /chromedriver/chromedriver* -d /chromedriver \
    && apt-get -y install maven \
    && apt-get -y install python3-requests 


# install node for lint test dependencies
ENV NODE_VERSION=12.6.0
RUN wget -qO- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash
ENV NVM_DIR=/root/.nvm
ENV PATH="/root/.nvm/versions/node/v${NODE_VERSION}/bin/:${PATH}"

RUN npm install eslint --save-dev

RUN curl -fsSL https://clis.cloud.ibm.com/install/linux | sh

# Set UTF-8 Locale
ENV LANG C.UTF-8

COPY . .

RUN bash ./scripts/build_jekyll_maven.sh

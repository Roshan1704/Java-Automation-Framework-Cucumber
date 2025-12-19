FROM maven:3.8.6-openjdk-11-slim

# Install Chrome
RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    unzip \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src
COPY testng.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Set display for headless execution
ENV DISPLAY=:99

# Default command
CMD ["mvn", "clean", "test"]

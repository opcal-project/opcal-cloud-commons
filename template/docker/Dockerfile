FROM openjdk:11-jdk-slim

WORKDIR /app

# Add a user with an explicit UID/GID and create necessary directories
RUN set -eux; \
    groupadd -r opcal --gid=1000; \
    useradd -r -g opcal --uid=1000 opcal; \
    chown opcal:opcal "/app"

# Install required packges
RUN set -eux; \
    apt-get update; \
    DEBIAN_FRONTEND=noninteractive \
    apt-get install -y --no-install-recommends \
        gosu \
        netcat \
        curl; \
    rm -rf /var/lib/apt/lists/*; \
# Verify that gosu binary works
    gosu nobody true

#COPY ${WORKSPACE}/${project-path}/${jar-name}.jar /app/

EXPOSE 8080

#COPY ${WORKSPACE}/${project-path}/$docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]

CMD ["java", "-jar", "{template}.jar"]
# moby-dns

This module provides an implementation of the `sun.net.spi.nameservice.NameService` interface that facilitates integration testing with Docker.

Note that this implementation uses a proprietary service provider interface and it may not be available on all platforms and may be changed at anytime.  Thus, this implementation should be used with caution.
 
## Overview

In some cases it is necessary for a Docker host machine to contact a container by that container's hostname, but that is not currently possible since Docker hosts do not have an interface on Docker networks. As a result, the Docker container hostnames must be resolved to either `localhost` (e.g., dockerd or Docker for Mac) or a virtual machine's IP address (e.g., Docker Machine).

## Usage

To use the Moby name service implementation do the following:

1. Add the following dependency to your project's `pom.xml` (replacing `${version.moby.dns}` with the version you wish to use):

    ```
    <dependency>
        <groupId>com.deciphernow</groupId>
        <artifactId>moby-dns</artifactId>
        <version>${version.moby.dns}</version>
    </dependency>
    ```

1. Add a resource to your JAR at `META-INF/services/sun.net.spi.nameservice.NameServiceDescriptor` with the following content:

    ```
    com.deciphernow.moby.MobyNameServiceDescriptor
    ```

1. Set the following properties for your application:

    ```
    sun.net.spi.nameservice.provider.1=dns,moby
    sun.net.spi.nameservice.provider.2=default
    ```

By default, the above configuration will resolve all host names on the `docker` TLD to `127.0.0.1`. These defaults can be overridden by the following environment variables:

| Name          | Default   | Description                                                                                |
|---------------|-----------|--------------------------------------------------------------------------------------------|
| DOCKER_DOMAIN | docker    | The TLD to resolve to the Docker host.                                                     |
| DOCKER_HOST   | 127.0.0.1 | The Docker host to which names resolve. This may be any string containing an IPv4 address. |

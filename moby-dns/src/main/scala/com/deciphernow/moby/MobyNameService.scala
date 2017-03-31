/**
  * Copyright 2017 Decipher Technology Studios LLC
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.deciphernow.moby

import java.net.{InetAddress, UnknownHostException}

import com.google.common.net.InetAddresses

// scalastyle:off illegal.imports
import sun.net.spi.nameservice.NameService
// scalastyle:on illegal.imports

/**
  * Provides an implementation of the [[NameService]] interface that resolves the IP addresses of docker containers.
  *
  * By implementing the [[NameService]] interface this object allows JVM processes running in the Oracle JRE/JDK to override the system name service providers
  * for the purpose of resolving docker container names to the appropriate docker host. For example, when running docker locally (e.g., via dockerd or docker
  * for mac) container hostnames cannot be resolved from the host computer.
  */
object MobyNameService extends NameService {

  /**
    * Defines the address pattern for matching an IPv4 address.
    */
  val addressPattern = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)".r

  /**
    * Defines the default domain for docker containers.
    */
  val defaultDockerDomain = "docker"

  /**
    * Defines the default docker host IP address.
    */
  val defaultDockerHost = "127.0.0.1"

  /**
    * Defines the docker domain for docker containers.
    */
  val dockerDomain = getEnv("DOCKER_DOMAIN", MobyNameService.defaultDockerDomain)

  /**
    * Defines the docker host IP address.
    */
  val dockerHost = addressPattern.findFirstIn(getEnv("DOCKER_HOST", "")).getOrElse(MobyNameService.defaultDockerHost)

  /**
    * Defines the byte representation of the docker host IP address.
    */
  val dockerBytes = getBytes(dockerHost)

  /**
    * Returns the addresses that resolve for a hostname.
    *
    * @param host the hostname
    * @return the addresses
    * @throws UnknownHostException if the hostname does not resolve
    */
  override def lookupAllHostAddr(host: String): Array[InetAddress] = {
    if (!s"^.+\\.$dockerDomain$$".r.findAllIn(host).hasNext) {
      throw new UnknownHostException(host)
    }
    Array(InetAddress.getByAddress(host, dockerBytes))
  }

  /**
    * Returns the hostname that corresponds to an address.
    *
    * @param bytes the address
    * @return the hostname
    * @throws UnknownHostException if the address does not correspond to a hostname
    */
  override def getHostByAddr(bytes: Array[Byte]): String = {
    throw new UnknownHostException()
  }

  /**
    * Returns the value of an environment variable if set; otherwise, a default value
    *
    * @param variable the variable
    * @param default the default value
    * @return the value if set; otherwise, the default value
    */
  def getEnv(variable: String, default: String): String = {
    sys.env.getOrElse(variable, default)
  }

  /**
    * Returns the bytes for an address.
    *
    * @param address the address
    * @return the bytes
    */
  def getBytes(address: String): Array[Byte] = {
    InetAddresses.forString(address).getAddress
  }

}

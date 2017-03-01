package com.deciphernow.moby

import java.net.{InetAddress, UnknownHostException}

import com.google.common.net.InetAddresses

// scalastyle:off illegal.imports
import sun.net.spi.nameservice.NameService
// scalastyle:on illegal.imports

/**
  * Provides an implementation of the [[NameService]] interface that resolves the IP addresses of docker containers.
  *
  * By implementing the [[NameService]] interface this object allows JVM processes running in the Oracle JRE/JDK to
  * override the system name service providers for the purpose of resolving docker container names to appropriate docker
  * host. For example, when running docker locally (e.g., via dockerd or docker for mac) container hostnames cannot be
  * resolved from the host computer.  While in many cases this is not
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
    * @inheritdoc
    */
  override def lookupAllHostAddr(host: String): Array[InetAddress] = {
    if (!s"^.+\\.$dockerDomain$$".r.findAllIn(host).hasNext) {
      throw new UnknownHostException(host)
    }
    Array(InetAddress.getByAddress(host, dockerBytes))
  }

  /**
    * @inheritdoc
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

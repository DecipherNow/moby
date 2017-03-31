package com.deciphernow.moby

import java.net.InetAddress

import com.google.common.net.InetAddresses
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

import scala.util.Random

/**
  * Provides integration tests for the [[MobyNameService]] class.
  */
@RunWith(classOf[JUnitRunner])
class IntSpecMobyNameService extends FlatSpec {

  private val randomString = Random.alphanumeric.take(10).mkString
  private val knownDomain = s"$randomString.${sys.env.getOrElse("DOCKER_DOMAIN", MobyNameService.defaultDockerDomain)}"
  private val dockerHost = sys.env.getOrElse("DOCKER_HOST", MobyNameService.defaultDockerHost)
  private val expectedAddresses = Array(InetAddress.getByAddress(dockerHost, InetAddresses.forString(dockerHost).getAddress))

  "The moby nameservice" should "resolve a host on a known domain when invoked via the nameservice spi" in {
    val actualInetAddresses = InetAddress.getAllByName(knownDomain)
    assert(actualInetAddresses.sameElements(expectedAddresses))
  }

}

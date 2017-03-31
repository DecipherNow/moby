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

import java.net.InetAddress
import java.net.UnknownHostException

import com.google.common.net.InetAddresses
import org.scalatest.FlatSpec

import scala.util.Random

/**
  * Provides unit tests for the [[MobyNameService]] class.
  */
class MobyNameServiceSpec extends FlatSpec {

  val randomOctet = Random.nextInt(256)
  val randomAddress = s"$randomOctet.$randomOctet.$randomOctet.$randomOctet"
  val randomString = Random.alphanumeric.take(10).mkString
  val knownDomain = s"$randomString.${sys.env.getOrElse("DOCKER_DOMAIN", MobyNameService.defaultDockerDomain)}"
  val unknownDomain = s"$randomString.${randomString}"
  val dockerHost = sys.env.getOrElse("DOCKER_HOST", MobyNameService.defaultDockerHost)
  val expectedAddresses = Array(InetAddress.getByAddress(dockerHost, InetAddresses.forString(dockerHost).getAddress))

  "The moby nameservice" should "resolve a host on a known domain to the docker host" in {
    val actualInetAddresses = MobyNameService.lookupAllHostAddr(knownDomain)
    assert(actualInetAddresses.sameElements(expectedAddresses))
  }

  "The moby nameservice" should "raise an exception resolving a host on an unknown domain" in {
    assertThrows[UnknownHostException] {
      MobyNameService.lookupAllHostAddr(unknownDomain);
    }
  }

  "The moby nameservice" should "raise an exception during a reverse lookup" in {
    assertThrows[UnknownHostException] {
      MobyNameService.getHostByAddr(InetAddresses.forString(randomAddress).getAddress)
    }
  }

  "The moby nameservice" should "resolve a host on a known domain when invoked via the nameservice spi" in {
    val actualInetAddresses = InetAddress.getAllByName(knownDomain)
    assert(actualInetAddresses.sameElements(expectedAddresses))
  }

}

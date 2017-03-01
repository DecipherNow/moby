package com.deciphernow.moby

import org.scalatest.FlatSpec

/**
  * Provides unit tests for the [[MobyNameServiceDescriptor]] class.
  */
class MobyNameServiceDescriptorSpec extends FlatSpec {

  val mobyNameServiceDescriptor = new MobyNameServiceDescriptor()

  "A MobyNameserviceDescriptor" should "return the MobyNameService object" in {
    assert(mobyNameServiceDescriptor.createNameService() == MobyNameService)
  }

  "A MobyNameServiceDescriptor" should "have a provider type of dns" in {
    assert(mobyNameServiceDescriptor.providerType == "dns")
  }

  "A MobyNameServiceDescriptor" should "have a provider name of moby" in {
    assert(mobyNameServiceDescriptor.providerName == "moby")
  }

}

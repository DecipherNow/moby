package com.deciphernow.moby

// scalastyle:off illegal.imports
import sun.net.spi.nameservice.NameService
import sun.net.spi.nameservice.NameServiceDescriptor
// scalastyle:on illegal.imports

/**
  * Provides an implementation of the [[NameServiceDescriptor]] class for the [[MobyNameService]].
  */
class MobyNameServiceDescriptor extends NameServiceDescriptor {

  /**
    * Defines the name of the provider.
    */
  val providerName = "moby"

  /**
    * Defines the type of the provider.
    */
  val providerType = "dns"

  /**
    * {@inheritdoc}
    */
  override def createNameService(): NameService = {
    MobyNameService
  }

  /**
    * {@inheritdoc}
    */
  override def getType: String = providerType

  /**
    * {@inheritdoc}
    */
  override def getProviderName: String = providerName

}

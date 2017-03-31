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

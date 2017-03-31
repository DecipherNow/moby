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
    * Returns the [[MobyNameService]] object.
    *
    * @return the name service
    */
  override def createNameService(): NameService = {
    MobyNameService
  }

  /**
    * Gets the provider type for this descriptor.
    *
    * @return the type
    */
  override def getType: String = providerType

  /**
    * Gets the provider name for this descriptor.
    *
    * @return the name
    */
  override def getProviderName: String = providerName

}

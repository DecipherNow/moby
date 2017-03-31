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

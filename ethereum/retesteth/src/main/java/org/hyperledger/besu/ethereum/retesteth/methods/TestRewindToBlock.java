/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.ethereum.retesteth.methods;

import org.hyperledger.besu.ethereum.api.jsonrpc.internal.JsonRpcRequest;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.methods.JsonRpcMethod;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.JsonRpcResponse;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.JsonRpcSuccessResponse;
import org.hyperledger.besu.ethereum.retesteth.RetestethContext;

public class TestRewindToBlock implements JsonRpcMethod {
  private final RetestethContext context;

  public TestRewindToBlock(final RetestethContext context) {
    this.context = context;
  }

  @Override
  public String getName() {
    return "test_rewindToBlock";
  }

  @Override
  public JsonRpcResponse response(final JsonRpcRequest request) {
    final long blockNumber = request.getRequiredParameter(0, Long.TYPE);

    return new JsonRpcSuccessResponse(
        request.getId(), context.getBlockchain().rewindToBlock(blockNumber));
  }
}

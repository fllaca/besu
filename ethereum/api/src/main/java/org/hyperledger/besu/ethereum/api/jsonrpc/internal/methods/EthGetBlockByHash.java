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
package org.hyperledger.besu.ethereum.api.jsonrpc.internal.methods;

import org.hyperledger.besu.ethereum.api.jsonrpc.RpcMethod;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.JsonRpcRequest;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.JsonRpcResponse;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.response.JsonRpcSuccessResponse;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.results.BlockResult;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.results.BlockResultFactory;
import org.hyperledger.besu.ethereum.api.query.BlockchainQueries;
import org.hyperledger.besu.ethereum.core.Hash;

public class EthGetBlockByHash implements JsonRpcMethod {

  private final BlockResultFactory blockResult;
  private final BlockchainQueries blockchain;

  public EthGetBlockByHash(
      final BlockchainQueries blockchain, final BlockResultFactory blockResult) {
    this.blockchain = blockchain;
    this.blockResult = blockResult;
  }

  @Override
  public String getName() {
    return RpcMethod.ETH_GET_BLOCK_BY_HASH.getMethodName();
  }

  @Override
  public JsonRpcResponse response(final JsonRpcRequest request) {
    return new JsonRpcSuccessResponse(request.getId(), blockResult(request));
  }

  private BlockResult blockResult(final JsonRpcRequest request) {
    final Hash hash = request.getRequiredParameter(0, Hash.class);

    if (isCompleteTransactions(request)) {
      return transactionComplete(hash);
    }

    return transactionHash(hash);
  }

  private BlockResult transactionComplete(final Hash hash) {
    return blockchain.blockByHash(hash).map(tx -> blockResult.transactionComplete(tx)).orElse(null);
  }

  private BlockResult transactionHash(final Hash hash) {
    return blockchain
        .blockByHashWithTxHashes(hash)
        .map(tx -> blockResult.transactionHash(tx))
        .orElse(null);
  }

  private boolean isCompleteTransactions(final JsonRpcRequest request) {
    return request.getRequiredParameter(1, Boolean.class);
  }
}

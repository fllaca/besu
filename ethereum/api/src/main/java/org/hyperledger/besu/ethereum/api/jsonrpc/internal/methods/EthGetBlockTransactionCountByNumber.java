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
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.parameters.BlockParameter;
import org.hyperledger.besu.ethereum.api.jsonrpc.internal.results.Quantity;
import org.hyperledger.besu.ethereum.api.query.BlockchainQueries;

public class EthGetBlockTransactionCountByNumber extends AbstractBlockParameterMethod {

  public EthGetBlockTransactionCountByNumber(final BlockchainQueries blockchain) {
    super(blockchain);
  }

  @Override
  public String getName() {
    return RpcMethod.ETH_GET_BLOCK_TRANSACTION_COUNT_BY_NUMBER.getMethodName();
  }

  @Override
  protected BlockParameter blockParameter(final JsonRpcRequest request) {
    return request.getRequiredParameter(0, BlockParameter.class);
  }

  @Override
  protected String resultByBlockNumber(final JsonRpcRequest req, final long blockNumber) {
    return getBlockchainQueries()
        .getTransactionCount(blockNumber)
        .map(Quantity::create)
        .orElse(null);
  }
}

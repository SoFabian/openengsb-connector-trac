/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.connector.trac.internal.models.xmlrpc;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;

/**
 * This class is required when using Spring 2.5 and Apache's XmlRpcClient. Due to the fact that in the original class
 * the methods getConfig and setConfig have different parameter types, Spring won't allow to inject the config-property.
 * Therefore this class extends the original one, providing a "correct" pair of get- and set-method.
 */
public class XmlRpcClientSpringHelper extends XmlRpcClient {
    public XmlRpcClientConfig getConfig() {
        return super.getClientConfig();
    }

    @Override
    public void setConfig(XmlRpcClientConfig pConfig) {
        super.setConfig(pConfig);
    }

}

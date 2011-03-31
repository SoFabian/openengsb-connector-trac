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

package org.openengsb.connector.trac.internal;

import java.util.HashMap;
import java.util.Map;

import org.openengsb.connector.trac.internal.models.TicketHandlerFactory;
import org.openengsb.core.api.ServiceInstanceFactory;
import org.openengsb.core.api.descriptor.AttributeDefinition;
import org.openengsb.core.api.descriptor.ServiceDescriptor;
import org.openengsb.core.api.descriptor.ServiceDescriptor.Builder;
import org.openengsb.core.api.validation.MultipleAttributeValidationResult;
import org.openengsb.core.api.validation.MultipleAttributeValidationResultImpl;
import org.openengsb.domain.issue.IssueDomain;

public class TracServiceInstanceFactory implements ServiceInstanceFactory<IssueDomain, TracConnector> {

    static final String ATTRIB_SERVER = "serverUrl";
    static final String ATTRIB_PASSWORD = "userPassword";
    static final String ATTRIB_USERNAME = "username";

    @Override
    public ServiceDescriptor getDescriptor(Builder builder) {
        builder.name("trac.name").description("trac.description");

        builder
            .attribute(
                buildAttribute(builder, ATTRIB_USERNAME, "username.outputMode", "username.outputMode.description"))
            .attribute(builder.newAttribute().id(ATTRIB_PASSWORD).name("userPassword.outputMode")
                .description("userPassword.outputMode.description").defaultValue("").asPassword().build())
            .attribute(builder.newAttribute().id(ATTRIB_SERVER).name("serverUrl.outputMode")
                .description("serverUrl.outputMode.description").defaultValue("").required().build());

        return builder.build();
    }

    private AttributeDefinition buildAttribute(ServiceDescriptor.Builder builder, String id, String nameId,
                                               String descriptionId) {
        return builder.newAttribute().id(id).name(nameId).description(descriptionId).defaultValue("").required()
            .build();
    }

    @Override
    public void updateServiceInstance(TracConnector instance, Map<String, String> attributes) {
        TicketHandlerFactory ticketFactory = instance.getTicketHandlerFactory();
        updateTicketHandlerFactory(attributes, ticketFactory);
    }

    @Override
    public MultipleAttributeValidationResult updateValidation(TracConnector instance, Map<String, String> attributes) {
        return new MultipleAttributeValidationResultImpl(true, new HashMap<String, String>());
    }

    @Override
    public TracConnector createServiceInstance(String id, Map<String, String> attributes) {
        TicketHandlerFactory ticketFactory = new TicketHandlerFactory();
        updateTicketHandlerFactory(attributes, ticketFactory);
        TracConnector tracConnector = new TracConnector(id, ticketFactory);

        updateServiceInstance(tracConnector, attributes);
        return tracConnector;
    }

    @Override
    public MultipleAttributeValidationResult createValidation(String id, Map<String, String> attributes) {
        return new MultipleAttributeValidationResultImpl(true, new HashMap<String, String>());
    }

    private void updateTicketHandlerFactory(Map<String, String> attributes, TicketHandlerFactory ticketFactory) {
        if (attributes.containsKey(ATTRIB_SERVER)) {
            ticketFactory.setServerUrl(attributes.get(ATTRIB_SERVER));
        }
        if (attributes.containsKey(ATTRIB_USERNAME)) {
            ticketFactory.setUsername(attributes.get(ATTRIB_USERNAME));
        }
        if (attributes.containsKey(ATTRIB_PASSWORD)) {
            ticketFactory.setUserPassword(attributes.get(ATTRIB_PASSWORD));
        }
    }
}

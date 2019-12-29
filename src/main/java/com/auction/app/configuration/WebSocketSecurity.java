package com.auction.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurity extends AbstractSecurityWebSocketMessageBrokerConfigurer{
	
	@Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
        .simpDestMatchers("/ws/**").authenticated()
        .anyMessage().authenticated();

    }

    // TODO: For test purpose (and simplicity) i disabled CSRF, but you should re-enable this and provide a CRSF endpoint.
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
	
}

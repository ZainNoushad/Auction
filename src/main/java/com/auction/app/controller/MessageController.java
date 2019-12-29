package com.auction.app.controller;

import java.security.Principal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController  {
	@MessageMapping("**/ws/**")
	@SendTo("/topic/zain")
	public String modifyMessage(String message) {
		return message.toUpperCase();
	}
	
}


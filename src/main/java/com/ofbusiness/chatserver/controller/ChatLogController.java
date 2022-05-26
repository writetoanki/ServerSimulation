package com.ofbusiness.chatserver.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ofbusiness.chatserver.dto.ChatLogDTO;
import com.ofbusiness.chatserver.services.ChatLogService;

import lombok.extern.log4j.Log4j2;


@RestController
@Log4j2
public class ChatLogController {
	
	@Autowired
	private ChatLogService chatLogService;

	@PostMapping(path="/chatlogs/{user}",consumes = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public ResponseEntity<ChatLogDTO> createChatLogEntry(@PathVariable String user, @Valid @RequestBody ChatLogDTO chatLog){
		log.debug("IN Post Mapping for createChatLog Entry");
		log.debug("User = "+user+", message = "+chatLog.getMessage()+", TimeStamp = "+chatLog.getTimestamp()+", IsSent = "+chatLog.getIsSent());
		chatLog.setUser(user);
		ChatLogDTO entity=this.chatLogService.createChatLogEntry(chatLog);
		String success="Chat Log inserted successfully";
		entity.setSuccess(success);
		log.debug("MessageId = "+entity.getMsgId()+", User = "+user+", message = "+chatLog.getMessage()+", TimeStamp = "+chatLog.getTimestamp()+", IsSent = "+chatLog.getIsSent());
		return new ResponseEntity<ChatLogDTO>(entity , HttpStatus.OK);
	}
	@PostMapping(path="/chatlogs/{user}",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	@ResponseBody
	public ResponseEntity<ChatLogDTO> createChatLogEntry1(@PathVariable String user,ChatLogDTO chatLog) {
		log.debug("IN Post Mapping for createChatLog Entry");
		log.debug("User = "+user+", message = "+chatLog.getMessage()+", TimeStamp = "+chatLog.getTimestamp()+", IsSent = "+chatLog.getIsSent());
		chatLog.setUser(user);
		ChatLogDTO entity=this.chatLogService.createChatLogEntry(chatLog);
		String success="Chat Log inserted successfully";
		entity.setSuccess(success);
		log.debug("MessageId = "+entity.getMsgId()+", User = "+user+", message = "+chatLog.getMessage()+", TimeStamp = "+chatLog.getTimestamp()+", IsSent = "+chatLog.getIsSent());
		return new ResponseEntity<ChatLogDTO>(entity , HttpStatus.OK);
			// TODO: handle exception
		}
	

	@GetMapping("/chatlogs/{user}")
	public List<ChatLogDTO> getChatLogs(@PathVariable String user,
			@RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
			@RequestParam(name = "start", required = false, defaultValue = "0") Integer start) {
			
			log.info("In Get Mapping to get the Logs");
			log.debug("User = "+user+", limit = "+limit+", start = "+start);
			List<ChatLogDTO> list = chatLogService.getChatLogs(user, limit, start);
			list.forEach((entity)->log.debug("MessageId = "+entity.getMsgId()+", User = "+user+", message = "+entity.getMessage()+", TimeStamp = "+entity.getTimestamp()+", IsSent = "+entity.getIsSent()));
		return list;
	}
    @Transactional
	@DeleteMapping("/chatlogs/{user}")
	public ResponseEntity<String> deleteAll(@PathVariable String user) {
    	try {
		log.info("In Delete Mapping to delete All the Logs for given user");
		log.debug("User = "+user);
		Long count = chatLogService.deleteAll(user);
		if(count==0) {
			throw new Exception("No Record Found");
		}
		String success = count+" Chat Log deleted Successfully for " + user + ".";
		log.debug(success);
		return new ResponseEntity<String>(success, HttpStatus.OK);}catch (Exception e) {
			log.debug(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
			// TODO: handle exception
		}
	}

	@DeleteMapping("chatlogs/{user}/{msgId}")
	public ResponseEntity<String> deleteChatLog(@PathVariable String user, @PathVariable int msgId) {
		try {
			log.info("In Delete Mapping to delete All the Logs for given user and Message id ");
			log.debug("User = "+user+", Message Id = "+msgId);
			chatLogService.deleteChatLog(user, msgId);
			String success = "Chat Log deleted Successfully for " + user + " and " + msgId;
			return new ResponseEntity<String>(success, HttpStatus.OK);
		} catch (Exception e) {
			String error = msgId + " is not found for user " + user + " Please enter valid msgId";
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}

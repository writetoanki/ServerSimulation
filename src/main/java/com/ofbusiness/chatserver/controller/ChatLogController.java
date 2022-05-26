package com.ofbusiness.chatserver.controller;

import java.util.List;

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

@RestController
public class ChatLogController {

	@Autowired
	private ChatLogService chatLogService;

	@PostMapping(path="/chatlogs/{user}",consumes = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public ResponseEntity<ChatLogDTO> createChatLogEntry(@PathVariable String user, @Valid @RequestBody ChatLogDTO chatLog){
		
		chatLog.setUser(user);
		ChatLogDTO entity=this.chatLogService.createChatLogEntry(chatLog);
		String success="Chat Log inserted successfully";
		entity.setSuccess(success);
		return new ResponseEntity<ChatLogDTO>(entity , HttpStatus.OK);
	}
	@PostMapping(path="/chatlogs/{user}",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	@ResponseBody
	public ResponseEntity<ChatLogDTO> createChatLogEntry1(@PathVariable String user,ChatLogDTO chatLog) {
		try {
		chatLog.setUser(user);
		ChatLogDTO entity=this.chatLogService.createChatLogEntry(chatLog);
		String success="Chat Log inserted successfully";
		entity.setSuccess(success);
		return new ResponseEntity<ChatLogDTO>(entity , HttpStatus.OK);}catch (Exception e) {
			chatLog.setError(e.getMessage());
			return new ResponseEntity<ChatLogDTO>(chatLog , HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}
	}

	@GetMapping("/chatlogs/{user}")
	public List<ChatLogDTO> getChatLogs(@PathVariable String user,
			@RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
			@RequestParam(name = "start", required = false, defaultValue = "0") Integer start) {

		return chatLogService.getChatLogs(user, limit, start);
	}

	@DeleteMapping("/chatlogs/{user}")
	public ResponseEntity<String> deleteAll(@PathVariable String user) {
		chatLogService.deleteAll(user);
		String success = "Chat Log deleted Successfully for " + user + ".";
		return new ResponseEntity<String>(success, HttpStatus.OK);
	}

	@DeleteMapping("chatlogs/{user}/{msgId}")
	public ResponseEntity<String> deleteChatLog(@PathVariable String user, @PathVariable int msgId) {
		try {
			System.out.println("Hi " +user+"+++"+msgId);
			chatLogService.deleteChatLog(user, msgId);
			String success = "Chat Log deleted Successfully for " + user + " and " + msgId;
			return new ResponseEntity<String>(success, HttpStatus.OK);
		} catch (Exception e) {
			String error = msgId + " is not found for user " + user + " Please enter valid msgId";
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}

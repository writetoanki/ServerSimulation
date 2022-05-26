package com.ofbusiness.chatserver.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ofbusiness.chatserver.dao.ChatLogDao;
import com.ofbusiness.chatserver.dto.ChatLogDTO;
@Service
public class ChatLogServicesImpl implements ChatLogService{
	@Autowired
	private ChatLogDao chatLogDao;
	@Override
	public ChatLogDTO createChatLogEntry(ChatLogDTO entry) {
		ChatLogDTO entity = chatLogDao.save(entry);
		return entity;
	}
	@Override
	public List<ChatLogDTO> getChatLogs(String user, int limit, int start) {
		
		Pageable page =PageRequest.of(start, limit);
		if(start!=0)
			return chatLogDao.findByUser(user, limit, start);
		else {
			return chatLogDao.findByUser(user,limit);
		}
	}
	@Override
	public Long deleteAll(String user) {
		
		return chatLogDao.removeByUser(user);
	}
	@Override
	public void deleteChatLog(String user, int msgId) {
		System.out.println("Hi " +user+"+++"+msgId);
		Optional<ChatLogDTO> entity  = chatLogDao.findByMsgIdAndUser(msgId,user);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++"+entity);
		chatLogDao.delete(entity.get());
	}
}

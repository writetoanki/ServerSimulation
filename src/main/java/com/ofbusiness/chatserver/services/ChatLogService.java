package com.ofbusiness.chatserver.services;

import java.util.List;

import com.ofbusiness.chatserver.dto.ChatLogDTO;

public interface ChatLogService {
		public ChatLogDTO createChatLogEntry(ChatLogDTO entry);

		public List<ChatLogDTO> getChatLogs(String user, int limit, int start);

		public void deleteAll(String user);

		public void deleteChatLog(String user, int msgId);
}

package com.ofbusiness.chatserver.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ofbusiness.chatserver.dto.ChatLogDTO;
@Repository
public interface ChatLogDao extends JpaRepository<ChatLogDTO, Integer> {
	
	Optional<ChatLogDTO> findByMsgIdAndUser(int msgId,String user);
	@Query(value="SELECT msg_id, is_sent, message, timestamp, user from chat_logdto WHERE user = ?1 AND msg_id >= ?3 LIMIT ?2",nativeQuery = true)
	List<ChatLogDTO> findByUser(String user, int limit, int start);
	@Query(value="SELECT msg_id, is_sent, message, timestamp, user from chat_logdto WHERE user = ?1 Order By timestamp DESC LIMIT ?2",nativeQuery = true)
	List<ChatLogDTO> findByUser(String user,int limit);

}


package com.ofbusiness.chatserver.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

import lombok.Data;




@Data
@JsonInclude(Include.NON_NULL)
@Entity
public class ChatLogDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_gen")
	@SequenceGenerator(name="seq_gen")
	Integer msgId;
	@Length(max = 16,min = 16, message = "user is not valid")
	String user;
	@NotNull(message = "Message can not be null")
	@Size
	String message;
	@NotNull(message = "Timestamp can not be null")
	Long timestamp;
	@NotNull(message = "IsSent Value can not be null")
	Boolean isSent;
	@Transient
	String success;
	@Transient
	String error;
	public ChatLogDTO () {}
}

package com.nv.baonk.vo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "chatmessage")
@IdClass(ChatMessage.ChatMessageVOPK.class)
public class ChatMessage {
	@Id
	@Column(name = "message_id")
	private int messageId;
	
	@Column(name = "cluster_id")
	@NotEmpty
	private int clusterId;
	
	@Column(name = "text_message")
	private String textMessage;
	
	@Column(name = "sticker_src")
	private String stickerSrc;
	
	@Column(name = "file_src")
	private String fileSrc;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "sender_id", length = 100)
	@NotEmpty
	private String senderId;
	
	@Column(name = "sender_name")
	@NotEmpty
	private String senderName;
	
	@Column(name = "receiver_id", length = 100)
	@NotEmpty
	private String receiverId;
	
	@Column(name = "created_time")
	private String createdTime;
	
	@Column(name = "user_image")
	private String userImage;
	
	@Id
	@Column(name = "tenant_id")
	private int tenantId;
	
	public static class ChatMessageVOPK implements Serializable {
		private static final long serialVersionUID = -745140442454647867L;
		
		private int messageId;		
		private int tenantId;
		
		public ChatMessageVOPK(int messageID, int tenantID) {
			this.messageId = messageID;			
			this.tenantId = tenantID;
		}
		
		public ChatMessageVOPK() {
			
		}
		
		public boolean equals(Object object) {
			if(object instanceof ChatMessageVOPK) {
				ChatMessageVOPK obj = (ChatMessageVOPK) object;
				return (messageId == obj.messageId && tenantId == obj.tenantId);
			}
			else {
				return false;
			}
		}
		
		public int hashCode() {
			return messageId + tenantId;
		}

		public long getSerialversionuid() {
			return serialVersionUID;
		}		
	}
	
}

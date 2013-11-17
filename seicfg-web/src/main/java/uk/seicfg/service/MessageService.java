package uk.seicfg.service;

import java.util.List;

import uk.seicfg.to.Message;


public interface MessageService {
	
	List<Message> getAllMessages();
	
	List<Message> getMessages(String uid);
	
	Message getMessage(String id);
	
	List<Message> getMessagesByType(String type);
	
	Message createMessage(uk.seicfg.orm.entities.SEIMessage message);
	
	Message updateMessage(uk.seicfg.orm.entities.SEIMessage message);
	
	void deleteMessage(Message message);
	
}

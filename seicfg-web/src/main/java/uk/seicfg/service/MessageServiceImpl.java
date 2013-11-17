package uk.seicfg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.seicfg.orm.entities.SEIMessage;
import uk.seicfg.orm.entities.SEIMetric;
import uk.seicfg.orm.repositories.SEIMessageRepository;
import uk.seicfg.to.Message;
import uk.seicfg.to.Metric;
import uk.seicfg.util.converter.MessageConverter;

@Component
public class MessageServiceImpl implements MessageService {

	protected static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Autowired
	private SEIMessageRepository messageRepository;

	private MessageConverter messageConverter = new MessageConverter();

	@Override
	public List<Message> getAllMessages() {
		LOG.info("MessageServiceImpl -> getAllMessages()");
		List<SEIMessage> messagesList = this.messageRepository.findAll();
		List<uk.seicfg.to.Message> messagesList2 = new ArrayList<uk.seicfg.to.Message>();
		for(SEIMessage message : messagesList) {
			messagesList2.add(messageConverter.convertFrom(message));
		}
		return messagesList2;

	}

	@Override
	public List<Message> getMessages(String uid) {
		String queryStr = "FROM SEIMessage as U WHERE Lower(U.uid) LIKE :uid order by id";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("uid", uid);
		LOG.info("UserServiceImpl <- getMessages(" + uid +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIMessage> messageList = this.messageRepository.executeQuery(queryStr, queryParams);
		List<uk.seicfg.to.Message> messageList2 = new ArrayList<uk.seicfg.to.Message>();
		for(SEIMessage message : messageList) {
			messageList2.add(messageConverter.convertFrom(message));
		}
		return messageList2;
	}

	@Override
	public Message getMessage(String id) {
		LOG.info("MessageServiceImpl <- getMessage(" + id + ")");
		SEIMessage message = this.messageRepository.findById(SEIMessage.class, Long.valueOf(id));
		if(message != null)
			return messageConverter.convertFrom(message);
		else
			return null;
	}

	@Override
	public List<Message> getMessagesByType(String type) {
		LOG.info("MessageServiceImpl <- getMessage(" + type + ")");
		if(type != null) 
			type = type.toLowerCase();
		String queryStr = "FROM SEIMessage as U WHERE Lower(U.type) = Lower(:type) order by id";
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("type", type);
		System.out.println("MessageServiceImpl <- getMessage(" + type +")" + " -> [queryStr:" + queryStr + ", queryParams:" + queryParams + "]");
		List<SEIMessage> messageList = this.messageRepository.executeQuery(queryStr, queryParams);
		List<uk.seicfg.to.Message> messageList2 = new ArrayList<uk.seicfg.to.Message>();
		for(SEIMessage message : messageList) {
			messageList2.add(messageConverter.convertFrom(message));
		}
		System.out.println("\n metricList2 " + messageList2);
		return messageList2;
	}
	
	@Override
	public Message createMessage(SEIMessage message) {
		LOG.info("MessageServiceImpl <- createMessage(" + message + ")");
		return messageConverter.convertFrom(this.messageRepository.create(message));
	}

	@Override
	public Message updateMessage(SEIMessage message) {
		LOG.info("MessageServiceImpl <- messageMessage(" + message + ")");
		return messageConverter.convertFrom(this.messageRepository.update(message));
	}

	@Override
	public void deleteMessage(Message message) {
		LOG.info("UserServiceImpl <- deleteUser(" + message + ")");
		this.messageRepository.remove(messageConverter.convertTo(message));		
	}
}

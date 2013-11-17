package uk.seicfg.rest;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.seicfg.service.MessageService;
import uk.seicfg.to.Message;
import uk.seicfg.to.User;
import uk.seicfg.util.converter.MessageConverter;

@Controller
public class MessageResource extends AbstractResource{

	@Autowired
	private MessageService messageService;
	
	private MessageConverter messageConverter = new MessageConverter();
	protected static final Logger LOG = LoggerFactory.getLogger(MessageResource.class);
	
    @RequestMapping(value="messages")
    @ResponseBody
    public List findAll() {
    	LOG.info("MessageResource <- findAll()");
        return messageService.getAllMessages();
    }
    
    
    @RequestMapping(value="messages/{uid}", produces = "application/json")
    @ResponseBody
	public Message findByUId(@PathVariable Long uid) {
		LOG.info("MessageResource <- findById(" + uid +")");
		return messageService.getMessage(uid.toString());
	}
	
    @RequestMapping(value ="messages/search", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
	public List<Message> findByType(@RequestParam("type") String type) {
    	LOG.info("MessageResource <- findByName(" + type +")");
		return messageService.getMessagesByType(type);
	}
    
	@RequestMapping(value ="messages/del/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public void remove(@PathVariable String uid) {
		LOG.info("MessageResource <- remove(" + uid + ")");
		messageService.deleteMessage(messageService.getMessage(uid));
	}
	
	@RequestMapping(value="messages/add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Message create(String text, String type, String solved) {
			LOG.info("MessageResource <- create()");
			Message message = new Message();
			try {
				message.setType(type);
				message.setSolved(solved);
				message.setText(text);
				prepareMessageObject(message);
				return messageService.createMessage(messageConverter.convertTo(message));
			} catch (Exception e) {
				//TODO proper error message display
				e.printStackTrace();
			}
			return null;
		}

		@RequestMapping(value ="messages/mod" ,method = RequestMethod.PUT, produces = "application/json")
		@ResponseBody
		public Message update(@RequestBody Message message) {
			LOG.info("MessageResource <- Update(" + message.getId() + ")");
			return messageService.updateMessage(messageConverter.convertTo(message));
		}
		
		private Message prepareMessageObject(Message message){
			message.setCreatedby("0");
			message.setCreationDateTime((new Date()).toString());
			message.setLastModifiedDate((new Date()).toString());
			message.setModifiedby("1");
			message.setRemarks("");
			return message;
		}
		
		public Boolean isMessageExists(String messageID){
			LOG.info("MessageResource <- isMessageExists()");
			if(messageService.getMessages(messageID) != null){
				return messageService.getMessages(messageID).size() > 0 ? true : false;
			}
			return false;
		}
}

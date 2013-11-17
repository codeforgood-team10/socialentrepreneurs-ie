package uk.seicfg.to;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author harishk
 */
@XmlRootElement
public class Metric implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String	type;
	private String	value;
	private String	remarks;
	private String	creationDateTime;
	private String	createdby;
	private String	modifiedby;
	private String	lastModifiedDate;
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long uid) {
		this.id = uid;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(String creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
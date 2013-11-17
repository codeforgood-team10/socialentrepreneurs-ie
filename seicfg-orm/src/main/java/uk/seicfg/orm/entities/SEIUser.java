package uk.seicfg.orm.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the seiuser database table.
 * 
 */
@Entity
@Table(name="seiuser")
public class SEIUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="Createdby")
	private BigInteger createdby;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="CreationDateTime")
	private Date creationDateTime;

	@Column(name="EmailId")
	private String emailId;
	
	@Column(name="isActive")
	private String isActive;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="LastLogin")
	private Date lastLogin;

	@Column(name="LastModifiedDate")
	private Timestamp lastModifiedDate;

	@Column(name="Modifiedby")
	private BigInteger modifiedby;

	@Column(name="Password")
	private String password;

	@Column(name="Remarks")
	private String remarks;

	@Column(name="Type")
	private String type;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<SEIUpdate> seiupdates;

	//bi-directional many-to-one association to Usercategorymap
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<SEIMessage> seimessages;

	//bi-directional many-to-one association to Usercategorymap
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Set<SEIMetric> seimetrics;
 
    public SEIUser() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigInteger getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	public Date getCreationDateTime() {
		return this.creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public BigInteger getModifiedby() {
		return this.modifiedby;
	}

	public void setModifiedby(BigInteger modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<SEIUpdate> getUpdates() {
		return this.seiupdates;
	}

	public void setUpdates(Set<SEIUpdate> updates) {
		this.seiupdates = updates;
	}
	
	public Set<SEIMessage> getMessages() {
		return this.seimessages;
	}

	public void setMessages(Set<SEIMessage> messages) {
		this.seimessages = messages;
	}
	
	public Set<SEIMetric> getMetrics() {
		return this.seimetrics;
	}

	public void setMetrics(Set<SEIMetric> metrics) {
		this.seimetrics = metrics;
	}
}
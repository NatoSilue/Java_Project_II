package java2Project;

import java.io.Serializable;

public class Message implements Serializable, Comparable<Message> {
	private final String messageID;
	private final String ServiceName;
	private final String showAfter;
	private final String showBefore;
	private final String creationDate;
	private final String messageDetails;
	
	public Message(String messageID, String serviceName, String showAfter, String showBefore, String creationDate,
			String messageDetails) {
		super();
		this.messageID = messageID;
		this.ServiceName = serviceName;
		this.showAfter = showAfter;
		this.showBefore = showBefore;
		this.creationDate = creationDate;
		this.messageDetails = messageDetails;
	}

	public String getMessageID() {
		return messageID;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public String getShowAfter() {
		return showAfter;
	}

	public String getShowBefore() {
		return showBefore;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getMessageDetails() {
		return messageDetails;
	}

	@Override
	public String toString() {
		return "Message [messageID=" + messageID + ", ServiceName=" + ServiceName + ", showBeAfter=" + showAfter
				+ ", showBefore=" + showBefore + ", creationDate=" + creationDate + ", messageDetails=" + messageDetails
				+ "]";
	}

	@Override
	public int compareTo(Message inc) {
		// TODO Auto-generated method stub
		return this.getMessageID().compareTo(inc.getMessageID());
	}
	
	
	
}
	

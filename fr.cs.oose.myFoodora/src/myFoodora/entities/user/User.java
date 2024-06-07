package myFoodora.entities.user;

import java.util.ArrayDeque;
import java.util.Collection;

import myFoodora.clui.Display;
import myFoodora.entities.Credential;
import myFoodora.entities.Notification;

/**User is an abstract class that represents a user of the system. It is extended by the classes Customer and Courier.
 * A user has an id, a name, a credential, and a collection of notifications.
 * A user can add a notification to its collection of notifications, get its credential, set its credential, get its name, set its name, get its id, get its notifications, and display itself.
 */
public abstract class User implements Display{
	private static Integer count = 0;
	
	protected Integer id;
	protected String name;
	protected Credential credential;
	protected Collection<Notification> notifications;
	
	public User() {
		super();
		this.id = User.count++;
		this.notifications = new ArrayDeque<Notification>();
	}

	public void addNotificationTo(Notification notification) {
		notifications.add(notification);
	}
	public Credential getCredential() {
		return credential;
	}
	public void setCredential(Credential credential) {
		this.credential = credential;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public Collection<Notification> getNotifications(){
		Collection<Notification> notifications = new ArrayDeque<Notification>(this.notifications);
		this.notifications.clear();		
		return notifications;
	}
	public String display() {
		StringBuilder sb = new StringBuilder();
        sb.append("=====================================\n");
        sb.append("   \t"+this.getClass().getSimpleName()+" "+name+"\n");
        sb.append("-------------------------------------\n");
        return sb.toString();
	}

}

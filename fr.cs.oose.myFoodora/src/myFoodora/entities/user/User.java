package myFoodora.entities.user;

import myFoodora.clui.Display;
import myFoodora.entities.Credential;

public abstract class User implements Display{
	private static Integer count = 0;
	
	protected Integer id;
	protected String name;
	protected Credential credential;
	
	public User() {
		super();
		this.id = User.count++;
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
	
	public String display() {
		StringBuilder sb = new StringBuilder();
        sb.append("=====================================\n");
        sb.append("   \t"+this.getClass().getSimpleName()+" "+name+"\n");
        sb.append("-------------------------------------\n");
        return sb.toString();
	}

}

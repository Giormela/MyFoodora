package myFoodora.entities.user;

public abstract class User {
	private static Integer count = 0;
	
	private Integer id;
	private String name;
	private Credential credential;
	
	User() {
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
}

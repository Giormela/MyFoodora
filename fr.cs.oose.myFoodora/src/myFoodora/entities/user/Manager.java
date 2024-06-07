package myFoodora.entities.user;

/**
 * The Manager class represents a manager of the system. It extends the User class.
 * It has a surname.
 * It has a display method to display the information of the manager.
 */
public class Manager extends User {
	private String surname;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Override
	public String display() {
		StringBuilder sb = new StringBuilder(super.display());
		sb.append(String.format(" Name       : %s %s\n", name, surname));
        sb.append("=====================================\n");
        return sb.toString();
	}
}

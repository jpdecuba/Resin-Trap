package Model;

public enum UserRole {
	User(0),
	Admin(1);

	private int value;
	UserRole(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

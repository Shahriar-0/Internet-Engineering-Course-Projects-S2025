package entities;

public record Customer(String ssn, String name, String phone, int age) {
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getPhoneNumber() {
		return phone;
	}

	public String getSsn() {
		return ssn;
	}
}

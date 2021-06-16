import java.util.HashMap;
import java.util.Objects;

public class Person {
	private String name;
	private String passport;

	private boolean board;

	public Person(String name, String passport) {
		this.name = name.toUpperCase();
		this.passport = passport.toUpperCase();
		board = false;
	}

	// get name
	public String getName() {
		return name;
	}

	// change name
	public void setName(String name) {
		this.name = name;
	}

	// get passenger passport
	public String getPassport() {
		return passport;
	}

	// change passenger passport
	public void setPassport(String passport) {
		this.passport = passport;
	}

	// print
	public String toString() {

		return name + "\t " + passport;
	}

}

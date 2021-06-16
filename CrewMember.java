import java.util.Objects;

public class CrewMember extends Person {
    private enum Titles {
        Pilot, Navigator, Attendant
    };

    private Titles title = null;

    public CrewMember(String name, String passport, String title) {
        super(name, passport);
        if (title.equalsIgnoreCase("pilot"))
            this.title = Titles.Pilot;
        if (title.equalsIgnoreCase("navigator"))
            this.title = Titles.Navigator;
        if (title.equalsIgnoreCase("attendant"))
            this.title = Titles.Attendant;
    }

    // print crew member
    public String toString() {
        return super.toString() + "\t " + title;
    }

    // get the title
    public Titles getTitle() {
        return title;
    }

    // override the equal method
    public boolean equals(Object other) {
        CrewMember otherP = (CrewMember) other;
        return this.getName().equalsIgnoreCase(otherP.getName())
                && this.getPassport().equalsIgnoreCase(otherP.getPassport()) && this.title.equals(otherP.title);
    }

    // override hashcode
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.getName());
        hash = 41 * hash + Objects.hashCode(this.getPassport());
        hash = 53 * hash + Objects.hashCode(this.title);
        return hash;
    }

    // check if the crew is pilot
    public boolean isPilot() {
        return title.equals(Titles.Pilot);
    }

    // check if the crew is navigator
    public boolean isNavi() {
        return title.equals(Titles.Navigator);
    }

}

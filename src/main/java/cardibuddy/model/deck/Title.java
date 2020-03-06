package cardibuddy.model.deck;

public class Title {

    private String titleString;

    public Title(String titleString) {
        this.titleString = titleString;
    }

    @Override
    public String toString() {
        return titleString;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else return (other instanceof Title) && titleString.equals(other.toString());
    }
}

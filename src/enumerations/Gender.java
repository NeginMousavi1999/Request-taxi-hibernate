package enumerations;

/**
 * @author Negin Mousavi
 */
public enum Gender {
    MALE("m"),
    FEMALE("f");

    String abbr;

    Gender(String abbr) {
        this.abbr = abbr;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}

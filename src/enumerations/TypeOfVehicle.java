package enumerations;

/**
 * @author Negin Mousavi
 */
public enum TypeOfVehicle {
    CAR("car"),
    MOTORCYCLE("motorcycle"),
    VAN("van");

    String abbr;

    TypeOfVehicle(String abbr) {
        this.abbr = abbr;
    }

    public String getAbbr() {
        return abbr;
    }
}

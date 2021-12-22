package models.members;

import enumerations.Gender;
import enumerations.TypeOfVehicle;
import lombok.Data;


/**
 * @author Negin Mousavi
 */
@Data
public class Driver extends User {
    private int vehicleId;
    private TypeOfVehicle typeOfVehicle;
    private String location;

    public Driver(String personalId, String firstName, String lastName, Gender gender, String phoneNumber, int birthYear, TypeOfVehicle typeOfVehicle, int vehicleId, String location) {
        super(personalId, firstName, lastName, gender, phoneNumber, birthYear);
        this.vehicleId = vehicleId;
        this.typeOfVehicle = typeOfVehicle;
        this.location = location;
    }

    @Override
    public String toString() {
        String s = "Drivers{" +
                super.toString() + '\'' +
                ", vehicleId=" + vehicleId +
                ", typeOfVehicle=" + typeOfVehicle.toString().toLowerCase() +
                ", location=" + location +
                '}';
        return s;
    }
}

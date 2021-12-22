package models.members;

import enumerations.Gender;
import enumerations.TypeOfVehicle;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.vehicles.Vehicle;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;


/**
 * @author Negin Mousavi
 */
@Data
@NoArgsConstructor
@Entity
public class Driver extends User {
    @OneToOne
    private Vehicle vehicle;
    @Enumerated(value = EnumType.STRING)
    private TypeOfVehicle typeOfVehicle;
    private String location;

    public Driver(String personalId, String firstName, String lastName, Gender gender, String phoneNumber, int birthYear, TypeOfVehicle typeOfVehicle, Vehicle vehicle, String location) {
        super(personalId, firstName, lastName, gender, phoneNumber, birthYear);
        this.vehicle = vehicle;
        this.typeOfVehicle = typeOfVehicle;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Drivers{" +
                super.toString() + '\'' +
                ", vehicleId=" + vehicle +
                ", typeOfVehicle=" + typeOfVehicle.toString().toLowerCase() +
                ", location=" + location +
                '}';
    }
}

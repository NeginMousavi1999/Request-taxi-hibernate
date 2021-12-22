package models.trip;

import enumerations.PaymentMethod;
import enumerations.TripStatus;
import lombok.Data;
import models.members.Driver;
import models.members.Passenger;

import javax.persistence.*;

/**
 * @author Negin Mousavi
 */

@Data
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Driver driver;
    @OneToOne
    private Passenger passenger;
    private String origin;
    private String destination;
    private double cost;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    public Trip(Passenger passenger, String origin, String destination, double cost, PaymentMethod paymentMethod) {
        this.passenger = passenger;
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.paymentMethod = paymentMethod;
        this.tripStatus = TripStatus.ON_TRIP;
    }

    public Trip(int id, Driver driver, Passenger passenger, String origin, String destination, double cost, PaymentMethod paymentMethod, TripStatus tripStatus) {
        this.id = id;
        this.driver = driver;
        this.passenger = passenger;
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.paymentMethod = paymentMethod;
        this.tripStatus = tripStatus;
    }

    public Trip() {

    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", driverId=" + passenger +
                ", passengerId=" + driver +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", cost=" + cost +
                ", paymentMethod=" + paymentMethod +
                ", tripStatus=" + tripStatus +
                '}';
    }
}

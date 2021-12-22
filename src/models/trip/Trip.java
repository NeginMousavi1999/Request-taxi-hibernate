package models.trip;

import enumerations.PaymentMethod;
import enumerations.TripStatus;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Negin Mousavi
 */

@Data
@Entity
@Table(name = "trip_made_by_hibernate")
public class Trip {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int driverId;
    private int passengerId;
    private String origin;
    private String destination;
    private double cost;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    public Trip(int passengerId, String origin, String destination, double cost, PaymentMethod paymentMethod) {
        this.passengerId = passengerId;
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.paymentMethod = paymentMethod;
        this.tripStatus = TripStatus.ON_TRIP;
    }

    public Trip(int id, int driverId, int passengerId, String origin, String destination, double cost, PaymentMethod paymentMethod, TripStatus tripStatus) {
        this.id = id;
        this.driverId = driverId;
        this.passengerId = passengerId;
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
                ", driverId=" + driverId +
                ", passengerId=" + passengerId +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", cost=" + cost +
                ", paymentMethod=" + paymentMethod +
                ", tripStatus=" + tripStatus +
                '}';
    }
}

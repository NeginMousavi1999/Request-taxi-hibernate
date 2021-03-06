package models.trip;

import enumerations.PaymentMethod;
import enumerations.TripStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.members.Driver;
import models.members.Passenger;

import javax.persistence.*;

/**
 * @author Negin Mousavi
 */

@Data
@Entity
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

package models.members;

import enumerations.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;

/**
 * @author Negin Mousavi
 */
@Data
@Entity
@NoArgsConstructor
public class Passenger extends User {
    private double accountBalance;

    public Passenger(String personalId, String firstName, String lastName, Gender gender, String phoneNumber, int birthYear, double accountBalance) {
        super(personalId, firstName, lastName, gender, phoneNumber, birthYear);
        this.accountBalance = accountBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public double increaseAccountBalance(double amount) {
        return accountBalance += amount;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                super.toString() + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
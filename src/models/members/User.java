package models.members;

import enumerations.Gender;
import enumerations.UserStatus;
import lombok.Data;

/**
 * @author Negin Mousavi
 */
@Data
public abstract class User {
    protected int id;
    protected String personalId;
    protected String firstName;
    protected String lastName;
    protected Gender gender;
    protected String phoneNumber;
    protected int birthYear;
    protected int age;
    protected UserStatus userStatus;

    public User(String personalId, String firstName, String lastName, Gender gender, String phoneNumber, int birthYear) {
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.birthYear = birthYear;
        userStatus = UserStatus.NO_REQUEST;
        age = calculateAge(1400);
    }

    public int calculateAge(int nowYear) {
        age = nowYear - birthYear;
        return age;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", personalId='" + personalId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender.toString().toLowerCase() +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthYear=" + birthYear +
                ", age=" + age +
                ", tripStatus=" + userStatus.toString().toLowerCase() + '\'';
    }
}

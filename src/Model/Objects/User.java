package Model.Objects;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private String username;
    private String password;
    private LocalDate birth_Date;
    private String first_Name;
    private String last_Name;
    private String city;
    private String country;

    public User(String username, String password, LocalDate birth_Date, String first_Name, String last_Name, String city, String country) {
        this.username = username;
        this.password = password;
        this.birth_Date = birth_Date;
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.city = city;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirth_Date() {
        return birth_Date;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}

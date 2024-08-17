package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class Customer {
    private String id;
    private String  name;
    private String address;
    private String number;
    private LocalDate dob;

    public Customer(String id, String name, String address, String number, String title, LocalDate dob) {
        this.id = id;
        this.name = title+name;
        this.address = address;
        this.number = number;
        this.dob = dob;
    }

}

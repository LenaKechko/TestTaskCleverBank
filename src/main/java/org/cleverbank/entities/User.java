package org.cleverbank.entities;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String lastName;
    private String name;
    private String middleName;
    private String address;
    private String phoneNumber;
}

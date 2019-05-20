package org.russow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Contact {

    private int id;
    private String address;
    private long phone;
    private String email;
}

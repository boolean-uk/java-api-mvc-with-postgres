package com.booleanuk.api.department;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {
    private long id;
    private String name;
    private String location;
}

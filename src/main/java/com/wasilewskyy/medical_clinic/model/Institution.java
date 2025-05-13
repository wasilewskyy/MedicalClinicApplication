package com.wasilewskyy.medical_clinic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String institutionName;
    private String cityName;
    private String postalCode;
    private String streetName;
    private String houseNumber;

    @ManyToMany(mappedBy = "institutions")
    private List<Doctor> doctors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution institution = (Institution) o;
        return id != null && id.equals(institution.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

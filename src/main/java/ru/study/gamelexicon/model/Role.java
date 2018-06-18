package ru.study.gamelexicon.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role extends NamedEntity{

    public Role() {
    }

    public Role(String name) {
        super(name);
    }


    @Override
    public String toString() {
        return super.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(super.getName(), role.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.getName());
    }
}

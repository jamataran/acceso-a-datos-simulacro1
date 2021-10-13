package com.cev.aircev.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Tripulante.
 */
@Entity
@Table(name = "tripulante")
public class Tripulante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "nombre", length = 255, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "apellidos", length = 255, nullable = false)
    private String apellidos;

    @Pattern(regexp = "[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]")
    @Column(name = "dni")
    private String dni;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "direccion", length = 255, nullable = false)
    private String direccion;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToMany(mappedBy = "tripulacions")
    @JsonIgnoreProperties(value = { "avion", "origen", "destino", "piloto", "tripulacions" }, allowSetters = true)
    private Set<Vuelo> vuelos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tripulante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Tripulante nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Tripulante apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return this.dni;
    }

    public Tripulante dni(String dni) {
        this.setDni(dni);
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Tripulante direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return this.email;
    }

    public Tripulante email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Vuelo> getVuelos() {
        return this.vuelos;
    }

    public void setVuelos(Set<Vuelo> vuelos) {
        if (this.vuelos != null) {
            this.vuelos.forEach(i -> i.removeTripulacion(this));
        }
        if (vuelos != null) {
            vuelos.forEach(i -> i.addTripulacion(this));
        }
        this.vuelos = vuelos;
    }

    public Tripulante vuelos(Set<Vuelo> vuelos) {
        this.setVuelos(vuelos);
        return this;
    }

    public Tripulante addVuelos(Vuelo vuelo) {
        this.vuelos.add(vuelo);
        vuelo.getTripulacions().add(this);
        return this;
    }

    public Tripulante removeVuelos(Vuelo vuelo) {
        this.vuelos.remove(vuelo);
        vuelo.getTripulacions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tripulante)) {
            return false;
        }
        return id != null && id.equals(((Tripulante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tripulante{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", dni='" + getDni() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}

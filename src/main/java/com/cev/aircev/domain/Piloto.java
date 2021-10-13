package com.cev.aircev.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Piloto.
 */
@Entity
@Table(name = "piloto")
public class Piloto implements Serializable {

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

    @NotNull
    @Pattern(regexp = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]")
    @Column(name = "dni", nullable = false)
    private String dni;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "es_piloto")
    private Boolean esPiloto;

    @Column(name = "horas_de_vuelo")
    private Long horasDeVuelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Piloto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Piloto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Piloto apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return this.dni;
    }

    public Piloto dni(String dni) {
        this.setDni(dni);
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Piloto direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return this.email;
    }

    public Piloto email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEsPiloto() {
        return this.esPiloto;
    }

    public Piloto esPiloto(Boolean esPiloto) {
        this.setEsPiloto(esPiloto);
        return this;
    }

    public void setEsPiloto(Boolean esPiloto) {
        this.esPiloto = esPiloto;
    }

    public Long getHorasDeVuelo() {
        return this.horasDeVuelo;
    }

    public Piloto horasDeVuelo(Long horasDeVuelo) {
        this.setHorasDeVuelo(horasDeVuelo);
        return this;
    }

    public void setHorasDeVuelo(Long horasDeVuelo) {
        this.horasDeVuelo = horasDeVuelo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Piloto)) {
            return false;
        }
        return id != null && id.equals(((Piloto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Piloto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", dni='" + getDni() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", email='" + getEmail() + "'" +
            ", esPiloto='" + getEsPiloto() + "'" +
            ", horasDeVuelo=" + getHorasDeVuelo() +
            "}";
    }
}

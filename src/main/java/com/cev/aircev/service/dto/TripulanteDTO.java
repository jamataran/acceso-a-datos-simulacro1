package com.cev.aircev.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cev.aircev.domain.Tripulante} entity.
 */
public class TripulanteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 255)
    private String apellidos;

    @Pattern(regexp = "[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]")
    private String dni;

    @NotNull
    @Size(min = 10, max = 255)
    private String direccion;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TripulanteDTO)) {
            return false;
        }

        TripulanteDTO tripulanteDTO = (TripulanteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tripulanteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripulanteDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", dni='" + getDni() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}

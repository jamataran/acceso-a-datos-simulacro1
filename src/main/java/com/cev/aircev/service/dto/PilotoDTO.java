package com.cev.aircev.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cev.aircev.domain.Piloto} entity.
 */
public class PilotoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 10, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 255)
    private String apellidos;

    @NotNull
    @Pattern(regexp = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]")
    private String dni;

    @NotNull
    private String direccion;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    private Boolean esPiloto;

    private Long horasDeVuelo;

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

    public Boolean getEsPiloto() {
        return esPiloto;
    }

    public void setEsPiloto(Boolean esPiloto) {
        this.esPiloto = esPiloto;
    }

    public Long getHorasDeVuelo() {
        return horasDeVuelo;
    }

    public void setHorasDeVuelo(Long horasDeVuelo) {
        this.horasDeVuelo = horasDeVuelo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PilotoDTO)) {
            return false;
        }

        PilotoDTO pilotoDTO = (PilotoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pilotoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PilotoDTO{" +
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

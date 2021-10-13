package com.cev.aircev.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cev.aircev.domain.Aeropuerto} entity.
 */
public class AeropuertoDTO implements Serializable {

    private Long id;

    @Size(min = 10, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 255)
    private String ciudad;

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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AeropuertoDTO)) {
            return false;
        }

        AeropuertoDTO aeropuertoDTO = (AeropuertoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, aeropuertoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AeropuertoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            "}";
    }
}

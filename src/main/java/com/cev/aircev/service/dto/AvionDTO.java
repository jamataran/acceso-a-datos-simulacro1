package com.cev.aircev.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cev.aircev.domain.Avion} entity.
 */
public class AvionDTO implements Serializable {

    private Long id;

    @Pattern(regexp = "^.\\-.$")
    private String matricula;

    @Size(min = 10, max = 255)
    private String tipo;

    @Min(value = 0)
    private Integer edad;

    @Size(min = 0, max = 255)
    private String numeroSerie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvionDTO)) {
            return false;
        }

        AvionDTO avionDTO = (AvionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, avionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvionDTO{" +
            "id=" + getId() +
            ", matricula='" + getMatricula() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", edad=" + getEdad() +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            "}";
    }
}

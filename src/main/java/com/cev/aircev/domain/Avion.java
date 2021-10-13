package com.cev.aircev.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Avion.
 */
@Entity
@Table(name = "avion")
public class Avion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "^.\\-.$")
    @Column(name = "matricula")
    private String matricula;

    @Size(min = 10, max = 255)
    @Column(name = "tipo", length = 255)
    private String tipo;

    @Min(value = 0)
    @Column(name = "edad")
    private Integer edad;

    @Size(min = 0, max = 255)
    @Column(name = "numero_serie", length = 255)
    private String numeroSerie;

    @OneToMany(mappedBy = "avion")
    @JsonIgnoreProperties(value = { "avion", "origen", "destino", "piloto", "tripulacions" }, allowSetters = true)
    private Set<Vuelo> vuelos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public Avion matricula(String matricula) {
        this.setMatricula(matricula);
        return this;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Avion tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public Avion edad(Integer edad) {
        this.setEdad(edad);
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNumeroSerie() {
        return this.numeroSerie;
    }

    public Avion numeroSerie(String numeroSerie) {
        this.setNumeroSerie(numeroSerie);
        return this;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Set<Vuelo> getVuelos() {
        return this.vuelos;
    }

    public void setVuelos(Set<Vuelo> vuelos) {
        if (this.vuelos != null) {
            this.vuelos.forEach(i -> i.setAvion(null));
        }
        if (vuelos != null) {
            vuelos.forEach(i -> i.setAvion(this));
        }
        this.vuelos = vuelos;
    }

    public Avion vuelos(Set<Vuelo> vuelos) {
        this.setVuelos(vuelos);
        return this;
    }

    public Avion addVuelos(Vuelo vuelo) {
        this.vuelos.add(vuelo);
        vuelo.setAvion(this);
        return this;
    }

    public Avion removeVuelos(Vuelo vuelo) {
        this.vuelos.remove(vuelo);
        vuelo.setAvion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avion)) {
            return false;
        }
        return id != null && id.equals(((Avion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avion{" +
            "id=" + getId() +
            ", matricula='" + getMatricula() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", edad=" + getEdad() +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            "}";
    }
}

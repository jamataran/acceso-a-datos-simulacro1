package com.cev.aircev.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Aeropuerto.
 */
@Entity
@Table(name = "aeropuerto")
public class Aeropuerto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 10, max = 255)
    @Column(name = "nombre", length = 255)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 255)
    @Column(name = "ciudad", length = 255, nullable = false)
    private String ciudad;

    @OneToMany(mappedBy = "origen")
    @JsonIgnoreProperties(value = { "avion", "origen", "destino", "piloto", "tripulacions" }, allowSetters = true)
    private Set<Vuelo> salidas = new HashSet<>();

    @OneToMany(mappedBy = "destino")
    @JsonIgnoreProperties(value = { "avion", "origen", "destino", "piloto", "tripulacions" }, allowSetters = true)
    private Set<Vuelo> destinos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aeropuerto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Aeropuerto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Aeropuerto ciudad(String ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Set<Vuelo> getSalidas() {
        return this.salidas;
    }

    public void setSalidas(Set<Vuelo> vuelos) {
        if (this.salidas != null) {
            this.salidas.forEach(i -> i.setOrigen(null));
        }
        if (vuelos != null) {
            vuelos.forEach(i -> i.setOrigen(this));
        }
        this.salidas = vuelos;
    }

    public Aeropuerto salidas(Set<Vuelo> vuelos) {
        this.setSalidas(vuelos);
        return this;
    }

    public Aeropuerto addSalidas(Vuelo vuelo) {
        this.salidas.add(vuelo);
        vuelo.setOrigen(this);
        return this;
    }

    public Aeropuerto removeSalidas(Vuelo vuelo) {
        this.salidas.remove(vuelo);
        vuelo.setOrigen(null);
        return this;
    }

    public Set<Vuelo> getDestinos() {
        return this.destinos;
    }

    public void setDestinos(Set<Vuelo> vuelos) {
        if (this.destinos != null) {
            this.destinos.forEach(i -> i.setDestino(null));
        }
        if (vuelos != null) {
            vuelos.forEach(i -> i.setDestino(this));
        }
        this.destinos = vuelos;
    }

    public Aeropuerto destinos(Set<Vuelo> vuelos) {
        this.setDestinos(vuelos);
        return this;
    }

    public Aeropuerto addDestinos(Vuelo vuelo) {
        this.destinos.add(vuelo);
        vuelo.setDestino(this);
        return this;
    }

    public Aeropuerto removeDestinos(Vuelo vuelo) {
        this.destinos.remove(vuelo);
        vuelo.setDestino(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aeropuerto)) {
            return false;
        }
        return id != null && id.equals(((Aeropuerto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aeropuerto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            "}";
    }
}

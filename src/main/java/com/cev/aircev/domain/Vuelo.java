package com.cev.aircev.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Vuelo.
 *
 * Eso lo he comentado yo
 */
@Entity
@Table(name = "vuelo")
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pasaporte_covid")
    private Boolean pasaporteCovid;

    @Column(name = "prueba")
    private String prueba;

    @ManyToOne
    @JsonIgnoreProperties(value = { "vuelos" }, allowSetters = true)
    private Avion avion;

    @ManyToOne
    @JsonIgnoreProperties(value = { "salidas", "destinos" }, allowSetters = true)
    private Aeropuerto origen;

    @ManyToOne
    @JsonIgnoreProperties(value = { "salidas", "destinos" }, allowSetters = true)
    private Aeropuerto destino;

    @ManyToOne
    private Piloto piloto;

    @ManyToMany
    @JoinTable(
        name = "rel_vuelo__tripulacion",
        joinColumns = @JoinColumn(name = "vuelo_id"),
        inverseJoinColumns = @JoinColumn(name = "tripulacion_id")
    )
    @JsonIgnoreProperties(value = { "vuelos" }, allowSetters = true)
    private Set<Tripulante> tripulacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vuelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPasaporteCovid() {
        return this.pasaporteCovid;
    }

    public Vuelo pasaporteCovid(Boolean pasaporteCovid) {
        this.setPasaporteCovid(pasaporteCovid);
        return this;
    }

    public void setPasaporteCovid(Boolean pasaporteCovid) {
        this.pasaporteCovid = pasaporteCovid;
    }

    public String getPrueba() {
        return this.prueba;
    }

    public Vuelo prueba(String prueba) {
        this.setPrueba(prueba);
        return this;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public Avion getAvion() {
        return this.avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Vuelo avion(Avion avion) {
        this.setAvion(avion);
        return this;
    }

    public Aeropuerto getOrigen() {
        return this.origen;
    }

    public void setOrigen(Aeropuerto aeropuerto) {
        this.origen = aeropuerto;
    }

    public Vuelo origen(Aeropuerto aeropuerto) {
        this.setOrigen(aeropuerto);
        return this;
    }

    public Aeropuerto getDestino() {
        return this.destino;
    }

    public void setDestino(Aeropuerto aeropuerto) {
        this.destino = aeropuerto;
    }

    public Vuelo destino(Aeropuerto aeropuerto) {
        this.setDestino(aeropuerto);
        return this;
    }

    public Piloto getPiloto() {
        return this.piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Vuelo piloto(Piloto piloto) {
        this.setPiloto(piloto);
        return this;
    }

    public Set<Tripulante> getTripulacions() {
        return this.tripulacions;
    }

    public void setTripulacions(Set<Tripulante> tripulantes) {
        this.tripulacions = tripulantes;
    }

    public Vuelo tripulacions(Set<Tripulante> tripulantes) {
        this.setTripulacions(tripulantes);
        return this;
    }

    public Vuelo addTripulacion(Tripulante tripulante) {
        this.tripulacions.add(tripulante);
        tripulante.getVuelos().add(this);
        return this;
    }

    public Vuelo removeTripulacion(Tripulante tripulante) {
        this.tripulacions.remove(tripulante);
        tripulante.getVuelos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vuelo)) {
            return false;
        }
        return id != null && id.equals(((Vuelo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vuelo{" +
            "id=" + getId() +
            ", pasaporteCovid='" + getPasaporteCovid() + "'" +
            ", prueba='" + getPrueba() + "'" +
            "}";
    }
}

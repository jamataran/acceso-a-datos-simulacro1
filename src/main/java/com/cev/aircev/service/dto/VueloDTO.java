package com.cev.aircev.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.cev.aircev.domain.Vuelo} entity.
 */
public class VueloDTO implements Serializable {

    private Long id;

    private Boolean pasaporteCovid;

    private String prueba;

    private AvionDTO avion;

    private AeropuertoDTO origen;

    private AeropuertoDTO destino;

    private PilotoDTO piloto;

    private Set<TripulanteDTO> tripulacions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPasaporteCovid() {
        return pasaporteCovid;
    }

    public void setPasaporteCovid(Boolean pasaporteCovid) {
        this.pasaporteCovid = pasaporteCovid;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }

    public AvionDTO getAvion() {
        return avion;
    }

    public void setAvion(AvionDTO avion) {
        this.avion = avion;
    }

    public AeropuertoDTO getOrigen() {
        return origen;
    }

    public void setOrigen(AeropuertoDTO origen) {
        this.origen = origen;
    }

    public AeropuertoDTO getDestino() {
        return destino;
    }

    public void setDestino(AeropuertoDTO destino) {
        this.destino = destino;
    }

    public PilotoDTO getPiloto() {
        return piloto;
    }

    public void setPiloto(PilotoDTO piloto) {
        this.piloto = piloto;
    }

    public Set<TripulanteDTO> getTripulacions() {
        return tripulacions;
    }

    public void setTripulacions(Set<TripulanteDTO> tripulacions) {
        this.tripulacions = tripulacions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VueloDTO)) {
            return false;
        }

        VueloDTO vueloDTO = (VueloDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vueloDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VueloDTO{" +
            "id=" + getId() +
            ", pasaporteCovid='" + getPasaporteCovid() + "'" +
            ", prueba='" + getPrueba() + "'" +
            ", avion=" + getAvion() +
            ", origen=" + getOrigen() +
            ", destino=" + getDestino() +
            ", piloto=" + getPiloto() +
            ", tripulacions=" + getTripulacions() +
            "}";
    }
}

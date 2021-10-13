import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVuelo, Vuelo } from '../vuelo.model';
import { VueloService } from '../service/vuelo.service';
import { IAvion } from 'app/entities/avion/avion.model';
import { AvionService } from 'app/entities/avion/service/avion.service';
import { IAeropuerto } from 'app/entities/aeropuerto/aeropuerto.model';
import { AeropuertoService } from 'app/entities/aeropuerto/service/aeropuerto.service';
import { IPiloto } from 'app/entities/piloto/piloto.model';
import { PilotoService } from 'app/entities/piloto/service/piloto.service';
import { ITripulante } from 'app/entities/tripulante/tripulante.model';
import { TripulanteService } from 'app/entities/tripulante/service/tripulante.service';

@Component({
  selector: 'jhi-vuelo-update',
  templateUrl: './vuelo-update.component.html',
})
export class VueloUpdateComponent implements OnInit {
  isSaving = false;

  avionsSharedCollection: IAvion[] = [];
  aeropuertosSharedCollection: IAeropuerto[] = [];
  pilotosSharedCollection: IPiloto[] = [];
  tripulantesSharedCollection: ITripulante[] = [];

  editForm = this.fb.group({
    id: [],
    avion: [],
    origen: [],
    destino: [],
    piloto: [],
    tripulacions: [],
  });

  constructor(
    protected vueloService: VueloService,
    protected avionService: AvionService,
    protected aeropuertoService: AeropuertoService,
    protected pilotoService: PilotoService,
    protected tripulanteService: TripulanteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vuelo }) => {
      this.updateForm(vuelo);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vuelo = this.createFromForm();
    if (vuelo.id !== undefined) {
      this.subscribeToSaveResponse(this.vueloService.update(vuelo));
    } else {
      this.subscribeToSaveResponse(this.vueloService.create(vuelo));
    }
  }

  trackAvionById(index: number, item: IAvion): number {
    return item.id!;
  }

  trackAeropuertoById(index: number, item: IAeropuerto): number {
    return item.id!;
  }

  trackPilotoById(index: number, item: IPiloto): number {
    return item.id!;
  }

  trackTripulanteById(index: number, item: ITripulante): number {
    return item.id!;
  }

  getSelectedTripulante(option: ITripulante, selectedVals?: ITripulante[]): ITripulante {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVuelo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vuelo: IVuelo): void {
    this.editForm.patchValue({
      id: vuelo.id,
      avion: vuelo.avion,
      origen: vuelo.origen,
      destino: vuelo.destino,
      piloto: vuelo.piloto,
      tripulacions: vuelo.tripulacions,
    });

    this.avionsSharedCollection = this.avionService.addAvionToCollectionIfMissing(this.avionsSharedCollection, vuelo.avion);
    this.aeropuertosSharedCollection = this.aeropuertoService.addAeropuertoToCollectionIfMissing(
      this.aeropuertosSharedCollection,
      vuelo.origen,
      vuelo.destino
    );
    this.pilotosSharedCollection = this.pilotoService.addPilotoToCollectionIfMissing(this.pilotosSharedCollection, vuelo.piloto);
    this.tripulantesSharedCollection = this.tripulanteService.addTripulanteToCollectionIfMissing(
      this.tripulantesSharedCollection,
      ...(vuelo.tripulacions ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.avionService
      .query()
      .pipe(map((res: HttpResponse<IAvion[]>) => res.body ?? []))
      .pipe(map((avions: IAvion[]) => this.avionService.addAvionToCollectionIfMissing(avions, this.editForm.get('avion')!.value)))
      .subscribe((avions: IAvion[]) => (this.avionsSharedCollection = avions));

    this.aeropuertoService
      .query()
      .pipe(map((res: HttpResponse<IAeropuerto[]>) => res.body ?? []))
      .pipe(
        map((aeropuertos: IAeropuerto[]) =>
          this.aeropuertoService.addAeropuertoToCollectionIfMissing(
            aeropuertos,
            this.editForm.get('origen')!.value,
            this.editForm.get('destino')!.value
          )
        )
      )
      .subscribe((aeropuertos: IAeropuerto[]) => (this.aeropuertosSharedCollection = aeropuertos));

    this.pilotoService
      .query()
      .pipe(map((res: HttpResponse<IPiloto[]>) => res.body ?? []))
      .pipe(map((pilotos: IPiloto[]) => this.pilotoService.addPilotoToCollectionIfMissing(pilotos, this.editForm.get('piloto')!.value)))
      .subscribe((pilotos: IPiloto[]) => (this.pilotosSharedCollection = pilotos));

    this.tripulanteService
      .query()
      .pipe(map((res: HttpResponse<ITripulante[]>) => res.body ?? []))
      .pipe(
        map((tripulantes: ITripulante[]) =>
          this.tripulanteService.addTripulanteToCollectionIfMissing(tripulantes, ...(this.editForm.get('tripulacions')!.value ?? []))
        )
      )
      .subscribe((tripulantes: ITripulante[]) => (this.tripulantesSharedCollection = tripulantes));
  }

  protected createFromForm(): IVuelo {
    return {
      ...new Vuelo(),
      id: this.editForm.get(['id'])!.value,
      avion: this.editForm.get(['avion'])!.value,
      origen: this.editForm.get(['origen'])!.value,
      destino: this.editForm.get(['destino'])!.value,
      piloto: this.editForm.get(['piloto'])!.value,
      tripulacions: this.editForm.get(['tripulacions'])!.value,
    };
  }
}

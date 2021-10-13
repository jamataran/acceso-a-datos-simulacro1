jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VueloService } from '../service/vuelo.service';
import { IVuelo, Vuelo } from '../vuelo.model';
import { IAvion } from 'app/entities/avion/avion.model';
import { AvionService } from 'app/entities/avion/service/avion.service';
import { IAeropuerto } from 'app/entities/aeropuerto/aeropuerto.model';
import { AeropuertoService } from 'app/entities/aeropuerto/service/aeropuerto.service';
import { IPiloto } from 'app/entities/piloto/piloto.model';
import { PilotoService } from 'app/entities/piloto/service/piloto.service';
import { ITripulante } from 'app/entities/tripulante/tripulante.model';
import { TripulanteService } from 'app/entities/tripulante/service/tripulante.service';

import { VueloUpdateComponent } from './vuelo-update.component';

describe('Component Tests', () => {
  describe('Vuelo Management Update Component', () => {
    let comp: VueloUpdateComponent;
    let fixture: ComponentFixture<VueloUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vueloService: VueloService;
    let avionService: AvionService;
    let aeropuertoService: AeropuertoService;
    let pilotoService: PilotoService;
    let tripulanteService: TripulanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VueloUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VueloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VueloUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vueloService = TestBed.inject(VueloService);
      avionService = TestBed.inject(AvionService);
      aeropuertoService = TestBed.inject(AeropuertoService);
      pilotoService = TestBed.inject(PilotoService);
      tripulanteService = TestBed.inject(TripulanteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Avion query and add missing value', () => {
        const vuelo: IVuelo = { id: 456 };
        const avion: IAvion = { id: 67364 };
        vuelo.avion = avion;

        const avionCollection: IAvion[] = [{ id: 5314 }];
        jest.spyOn(avionService, 'query').mockReturnValue(of(new HttpResponse({ body: avionCollection })));
        const additionalAvions = [avion];
        const expectedCollection: IAvion[] = [...additionalAvions, ...avionCollection];
        jest.spyOn(avionService, 'addAvionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        expect(avionService.query).toHaveBeenCalled();
        expect(avionService.addAvionToCollectionIfMissing).toHaveBeenCalledWith(avionCollection, ...additionalAvions);
        expect(comp.avionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Aeropuerto query and add missing value', () => {
        const vuelo: IVuelo = { id: 456 };
        const origen: IAeropuerto = { id: 34038 };
        vuelo.origen = origen;
        const destino: IAeropuerto = { id: 69703 };
        vuelo.destino = destino;

        const aeropuertoCollection: IAeropuerto[] = [{ id: 33008 }];
        jest.spyOn(aeropuertoService, 'query').mockReturnValue(of(new HttpResponse({ body: aeropuertoCollection })));
        const additionalAeropuertos = [origen, destino];
        const expectedCollection: IAeropuerto[] = [...additionalAeropuertos, ...aeropuertoCollection];
        jest.spyOn(aeropuertoService, 'addAeropuertoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        expect(aeropuertoService.query).toHaveBeenCalled();
        expect(aeropuertoService.addAeropuertoToCollectionIfMissing).toHaveBeenCalledWith(aeropuertoCollection, ...additionalAeropuertos);
        expect(comp.aeropuertosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Piloto query and add missing value', () => {
        const vuelo: IVuelo = { id: 456 };
        const piloto: IPiloto = { id: 14476 };
        vuelo.piloto = piloto;

        const pilotoCollection: IPiloto[] = [{ id: 78976 }];
        jest.spyOn(pilotoService, 'query').mockReturnValue(of(new HttpResponse({ body: pilotoCollection })));
        const additionalPilotos = [piloto];
        const expectedCollection: IPiloto[] = [...additionalPilotos, ...pilotoCollection];
        jest.spyOn(pilotoService, 'addPilotoToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        expect(pilotoService.query).toHaveBeenCalled();
        expect(pilotoService.addPilotoToCollectionIfMissing).toHaveBeenCalledWith(pilotoCollection, ...additionalPilotos);
        expect(comp.pilotosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Tripulante query and add missing value', () => {
        const vuelo: IVuelo = { id: 456 };
        const tripulacions: ITripulante[] = [{ id: 3487 }];
        vuelo.tripulacions = tripulacions;

        const tripulanteCollection: ITripulante[] = [{ id: 3240 }];
        jest.spyOn(tripulanteService, 'query').mockReturnValue(of(new HttpResponse({ body: tripulanteCollection })));
        const additionalTripulantes = [...tripulacions];
        const expectedCollection: ITripulante[] = [...additionalTripulantes, ...tripulanteCollection];
        jest.spyOn(tripulanteService, 'addTripulanteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        expect(tripulanteService.query).toHaveBeenCalled();
        expect(tripulanteService.addTripulanteToCollectionIfMissing).toHaveBeenCalledWith(tripulanteCollection, ...additionalTripulantes);
        expect(comp.tripulantesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const vuelo: IVuelo = { id: 456 };
        const avion: IAvion = { id: 84608 };
        vuelo.avion = avion;
        const origen: IAeropuerto = { id: 81311 };
        vuelo.origen = origen;
        const destino: IAeropuerto = { id: 25090 };
        vuelo.destino = destino;
        const piloto: IPiloto = { id: 42707 };
        vuelo.piloto = piloto;
        const tripulacions: ITripulante = { id: 70358 };
        vuelo.tripulacions = [tripulacions];

        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(vuelo));
        expect(comp.avionsSharedCollection).toContain(avion);
        expect(comp.aeropuertosSharedCollection).toContain(origen);
        expect(comp.aeropuertosSharedCollection).toContain(destino);
        expect(comp.pilotosSharedCollection).toContain(piloto);
        expect(comp.tripulantesSharedCollection).toContain(tripulacions);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Vuelo>>();
        const vuelo = { id: 123 };
        jest.spyOn(vueloService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vuelo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vueloService.update).toHaveBeenCalledWith(vuelo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Vuelo>>();
        const vuelo = new Vuelo();
        jest.spyOn(vueloService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: vuelo }));
        saveSubject.complete();

        // THEN
        expect(vueloService.create).toHaveBeenCalledWith(vuelo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Vuelo>>();
        const vuelo = { id: 123 };
        jest.spyOn(vueloService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ vuelo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vueloService.update).toHaveBeenCalledWith(vuelo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAvionById', () => {
        it('Should return tracked Avion primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAvionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAeropuertoById', () => {
        it('Should return tracked Aeropuerto primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAeropuertoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackPilotoById', () => {
        it('Should return tracked Piloto primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPilotoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTripulanteById', () => {
        it('Should return tracked Tripulante primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTripulanteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedTripulante', () => {
        it('Should return option if no Tripulante is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedTripulante(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Tripulante for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedTripulante(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Tripulante is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedTripulante(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});

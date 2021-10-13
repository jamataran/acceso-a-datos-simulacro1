jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TripulanteService } from '../service/tripulante.service';
import { ITripulante, Tripulante } from '../tripulante.model';

import { TripulanteUpdateComponent } from './tripulante-update.component';

describe('Component Tests', () => {
  describe('Tripulante Management Update Component', () => {
    let comp: TripulanteUpdateComponent;
    let fixture: ComponentFixture<TripulanteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let tripulanteService: TripulanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TripulanteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TripulanteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TripulanteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      tripulanteService = TestBed.inject(TripulanteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const tripulante: ITripulante = { id: 456 };

        activatedRoute.data = of({ tripulante });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(tripulante));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Tripulante>>();
        const tripulante = { id: 123 };
        jest.spyOn(tripulanteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tripulante });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tripulante }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(tripulanteService.update).toHaveBeenCalledWith(tripulante);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Tripulante>>();
        const tripulante = new Tripulante();
        jest.spyOn(tripulanteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tripulante });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: tripulante }));
        saveSubject.complete();

        // THEN
        expect(tripulanteService.create).toHaveBeenCalledWith(tripulante);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Tripulante>>();
        const tripulante = { id: 123 };
        jest.spyOn(tripulanteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ tripulante });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(tripulanteService.update).toHaveBeenCalledWith(tripulante);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

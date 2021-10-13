jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AeropuertoService } from '../service/aeropuerto.service';
import { IAeropuerto, Aeropuerto } from '../aeropuerto.model';

import { AeropuertoUpdateComponent } from './aeropuerto-update.component';

describe('Component Tests', () => {
  describe('Aeropuerto Management Update Component', () => {
    let comp: AeropuertoUpdateComponent;
    let fixture: ComponentFixture<AeropuertoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let aeropuertoService: AeropuertoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AeropuertoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AeropuertoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AeropuertoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      aeropuertoService = TestBed.inject(AeropuertoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const aeropuerto: IAeropuerto = { id: 456 };

        activatedRoute.data = of({ aeropuerto });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(aeropuerto));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Aeropuerto>>();
        const aeropuerto = { id: 123 };
        jest.spyOn(aeropuertoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ aeropuerto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: aeropuerto }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(aeropuertoService.update).toHaveBeenCalledWith(aeropuerto);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Aeropuerto>>();
        const aeropuerto = new Aeropuerto();
        jest.spyOn(aeropuertoService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ aeropuerto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: aeropuerto }));
        saveSubject.complete();

        // THEN
        expect(aeropuertoService.create).toHaveBeenCalledWith(aeropuerto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Aeropuerto>>();
        const aeropuerto = { id: 123 };
        jest.spyOn(aeropuertoService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ aeropuerto });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(aeropuertoService.update).toHaveBeenCalledWith(aeropuerto);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

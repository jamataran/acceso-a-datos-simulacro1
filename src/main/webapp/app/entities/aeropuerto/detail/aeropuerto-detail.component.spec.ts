import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AeropuertoDetailComponent } from './aeropuerto-detail.component';

describe('Component Tests', () => {
  describe('Aeropuerto Management Detail Component', () => {
    let comp: AeropuertoDetailComponent;
    let fixture: ComponentFixture<AeropuertoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AeropuertoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ aeropuerto: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AeropuertoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AeropuertoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aeropuerto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aeropuerto).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

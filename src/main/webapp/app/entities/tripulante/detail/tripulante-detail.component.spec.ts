import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TripulanteDetailComponent } from './tripulante-detail.component';

describe('Component Tests', () => {
  describe('Tripulante Management Detail Component', () => {
    let comp: TripulanteDetailComponent;
    let fixture: ComponentFixture<TripulanteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TripulanteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ tripulante: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TripulanteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TripulanteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tripulante on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tripulante).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

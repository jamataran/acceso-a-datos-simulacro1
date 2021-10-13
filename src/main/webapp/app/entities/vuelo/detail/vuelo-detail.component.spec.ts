import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VueloDetailComponent } from './vuelo-detail.component';

describe('Component Tests', () => {
  describe('Vuelo Management Detail Component', () => {
    let comp: VueloDetailComponent;
    let fixture: ComponentFixture<VueloDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VueloDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ vuelo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VueloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VueloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load vuelo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vuelo).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

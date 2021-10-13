import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AvionDetailComponent } from './avion-detail.component';

describe('Component Tests', () => {
  describe('Avion Management Detail Component', () => {
    let comp: AvionDetailComponent;
    let fixture: ComponentFixture<AvionDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AvionDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ avion: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AvionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load avion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avion).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});

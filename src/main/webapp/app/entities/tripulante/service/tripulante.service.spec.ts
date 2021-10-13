import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITripulante, Tripulante } from '../tripulante.model';

import { TripulanteService } from './tripulante.service';

describe('Service Tests', () => {
  describe('Tripulante Service', () => {
    let service: TripulanteService;
    let httpMock: HttpTestingController;
    let elemDefault: ITripulante;
    let expectedResult: ITripulante | ITripulante[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TripulanteService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nombre: 'AAAAAAA',
        apellidos: 'AAAAAAA',
        dni: 'AAAAAAA',
        direccion: 'AAAAAAA',
        email: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Tripulante', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Tripulante()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Tripulante', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
            apellidos: 'BBBBBB',
            dni: 'BBBBBB',
            direccion: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Tripulante', () => {
        const patchObject = Object.assign(
          {
            apellidos: 'BBBBBB',
            direccion: 'BBBBBB',
            email: 'BBBBBB',
          },
          new Tripulante()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Tripulante', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
            apellidos: 'BBBBBB',
            dni: 'BBBBBB',
            direccion: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Tripulante', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTripulanteToCollectionIfMissing', () => {
        it('should add a Tripulante to an empty array', () => {
          const tripulante: ITripulante = { id: 123 };
          expectedResult = service.addTripulanteToCollectionIfMissing([], tripulante);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tripulante);
        });

        it('should not add a Tripulante to an array that contains it', () => {
          const tripulante: ITripulante = { id: 123 };
          const tripulanteCollection: ITripulante[] = [
            {
              ...tripulante,
            },
            { id: 456 },
          ];
          expectedResult = service.addTripulanteToCollectionIfMissing(tripulanteCollection, tripulante);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Tripulante to an array that doesn't contain it", () => {
          const tripulante: ITripulante = { id: 123 };
          const tripulanteCollection: ITripulante[] = [{ id: 456 }];
          expectedResult = service.addTripulanteToCollectionIfMissing(tripulanteCollection, tripulante);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tripulante);
        });

        it('should add only unique Tripulante to an array', () => {
          const tripulanteArray: ITripulante[] = [{ id: 123 }, { id: 456 }, { id: 96222 }];
          const tripulanteCollection: ITripulante[] = [{ id: 123 }];
          expectedResult = service.addTripulanteToCollectionIfMissing(tripulanteCollection, ...tripulanteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const tripulante: ITripulante = { id: 123 };
          const tripulante2: ITripulante = { id: 456 };
          expectedResult = service.addTripulanteToCollectionIfMissing([], tripulante, tripulante2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(tripulante);
          expect(expectedResult).toContain(tripulante2);
        });

        it('should accept null and undefined values', () => {
          const tripulante: ITripulante = { id: 123 };
          expectedResult = service.addTripulanteToCollectionIfMissing([], null, tripulante, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(tripulante);
        });

        it('should return initial array if no Tripulante is added', () => {
          const tripulanteCollection: ITripulante[] = [{ id: 123 }];
          expectedResult = service.addTripulanteToCollectionIfMissing(tripulanteCollection, undefined, null);
          expect(expectedResult).toEqual(tripulanteCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

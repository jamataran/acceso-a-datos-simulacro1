import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAvion, Avion } from '../avion.model';

import { AvionService } from './avion.service';

describe('Service Tests', () => {
  describe('Avion Service', () => {
    let service: AvionService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvion;
    let expectedResult: IAvion | IAvion[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AvionService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        matricula: 'AAAAAAA',
        tipo: 'AAAAAAA',
        edad: 0,
        numeroSerie: 'AAAAAAA',
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

      it('should create a Avion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Avion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Avion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            matricula: 'BBBBBB',
            tipo: 'BBBBBB',
            edad: 1,
            numeroSerie: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Avion', () => {
        const patchObject = Object.assign(
          {
            numeroSerie: 'BBBBBB',
          },
          new Avion()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Avion', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            matricula: 'BBBBBB',
            tipo: 'BBBBBB',
            edad: 1,
            numeroSerie: 'BBBBBB',
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

      it('should delete a Avion', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAvionToCollectionIfMissing', () => {
        it('should add a Avion to an empty array', () => {
          const avion: IAvion = { id: 123 };
          expectedResult = service.addAvionToCollectionIfMissing([], avion);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(avion);
        });

        it('should not add a Avion to an array that contains it', () => {
          const avion: IAvion = { id: 123 };
          const avionCollection: IAvion[] = [
            {
              ...avion,
            },
            { id: 456 },
          ];
          expectedResult = service.addAvionToCollectionIfMissing(avionCollection, avion);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Avion to an array that doesn't contain it", () => {
          const avion: IAvion = { id: 123 };
          const avionCollection: IAvion[] = [{ id: 456 }];
          expectedResult = service.addAvionToCollectionIfMissing(avionCollection, avion);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(avion);
        });

        it('should add only unique Avion to an array', () => {
          const avionArray: IAvion[] = [{ id: 123 }, { id: 456 }, { id: 92286 }];
          const avionCollection: IAvion[] = [{ id: 123 }];
          expectedResult = service.addAvionToCollectionIfMissing(avionCollection, ...avionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const avion: IAvion = { id: 123 };
          const avion2: IAvion = { id: 456 };
          expectedResult = service.addAvionToCollectionIfMissing([], avion, avion2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(avion);
          expect(expectedResult).toContain(avion2);
        });

        it('should accept null and undefined values', () => {
          const avion: IAvion = { id: 123 };
          expectedResult = service.addAvionToCollectionIfMissing([], null, avion, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(avion);
        });

        it('should return initial array if no Avion is added', () => {
          const avionCollection: IAvion[] = [{ id: 123 }];
          expectedResult = service.addAvionToCollectionIfMissing(avionCollection, undefined, null);
          expect(expectedResult).toEqual(avionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

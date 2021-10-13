import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAeropuerto, Aeropuerto } from '../aeropuerto.model';

import { AeropuertoService } from './aeropuerto.service';

describe('Service Tests', () => {
  describe('Aeropuerto Service', () => {
    let service: AeropuertoService;
    let httpMock: HttpTestingController;
    let elemDefault: IAeropuerto;
    let expectedResult: IAeropuerto | IAeropuerto[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AeropuertoService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nombre: 'AAAAAAA',
        ciudad: 'AAAAAAA',
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

      it('should create a Aeropuerto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Aeropuerto()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Aeropuerto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
            ciudad: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Aeropuerto', () => {
        const patchObject = Object.assign(
          {
            ciudad: 'BBBBBB',
          },
          new Aeropuerto()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Aeropuerto', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nombre: 'BBBBBB',
            ciudad: 'BBBBBB',
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

      it('should delete a Aeropuerto', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAeropuertoToCollectionIfMissing', () => {
        it('should add a Aeropuerto to an empty array', () => {
          const aeropuerto: IAeropuerto = { id: 123 };
          expectedResult = service.addAeropuertoToCollectionIfMissing([], aeropuerto);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(aeropuerto);
        });

        it('should not add a Aeropuerto to an array that contains it', () => {
          const aeropuerto: IAeropuerto = { id: 123 };
          const aeropuertoCollection: IAeropuerto[] = [
            {
              ...aeropuerto,
            },
            { id: 456 },
          ];
          expectedResult = service.addAeropuertoToCollectionIfMissing(aeropuertoCollection, aeropuerto);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Aeropuerto to an array that doesn't contain it", () => {
          const aeropuerto: IAeropuerto = { id: 123 };
          const aeropuertoCollection: IAeropuerto[] = [{ id: 456 }];
          expectedResult = service.addAeropuertoToCollectionIfMissing(aeropuertoCollection, aeropuerto);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(aeropuerto);
        });

        it('should add only unique Aeropuerto to an array', () => {
          const aeropuertoArray: IAeropuerto[] = [{ id: 123 }, { id: 456 }, { id: 74656 }];
          const aeropuertoCollection: IAeropuerto[] = [{ id: 123 }];
          expectedResult = service.addAeropuertoToCollectionIfMissing(aeropuertoCollection, ...aeropuertoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const aeropuerto: IAeropuerto = { id: 123 };
          const aeropuerto2: IAeropuerto = { id: 456 };
          expectedResult = service.addAeropuertoToCollectionIfMissing([], aeropuerto, aeropuerto2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(aeropuerto);
          expect(expectedResult).toContain(aeropuerto2);
        });

        it('should accept null and undefined values', () => {
          const aeropuerto: IAeropuerto = { id: 123 };
          expectedResult = service.addAeropuertoToCollectionIfMissing([], null, aeropuerto, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(aeropuerto);
        });

        it('should return initial array if no Aeropuerto is added', () => {
          const aeropuertoCollection: IAeropuerto[] = [{ id: 123 }];
          expectedResult = service.addAeropuertoToCollectionIfMissing(aeropuertoCollection, undefined, null);
          expect(expectedResult).toEqual(aeropuertoCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

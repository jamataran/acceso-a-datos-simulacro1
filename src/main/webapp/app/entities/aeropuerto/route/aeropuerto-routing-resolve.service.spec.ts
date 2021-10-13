jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAeropuerto, Aeropuerto } from '../aeropuerto.model';
import { AeropuertoService } from '../service/aeropuerto.service';

import { AeropuertoRoutingResolveService } from './aeropuerto-routing-resolve.service';

describe('Service Tests', () => {
  describe('Aeropuerto routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AeropuertoRoutingResolveService;
    let service: AeropuertoService;
    let resultAeropuerto: IAeropuerto | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AeropuertoRoutingResolveService);
      service = TestBed.inject(AeropuertoService);
      resultAeropuerto = undefined;
    });

    describe('resolve', () => {
      it('should return IAeropuerto returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAeropuerto = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAeropuerto).toEqual({ id: 123 });
      });

      it('should return new IAeropuerto if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAeropuerto = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAeropuerto).toEqual(new Aeropuerto());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Aeropuerto })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAeropuerto = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAeropuerto).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

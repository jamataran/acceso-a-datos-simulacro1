jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAvion, Avion } from '../avion.model';
import { AvionService } from '../service/avion.service';

import { AvionRoutingResolveService } from './avion-routing-resolve.service';

describe('Service Tests', () => {
  describe('Avion routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AvionRoutingResolveService;
    let service: AvionService;
    let resultAvion: IAvion | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AvionRoutingResolveService);
      service = TestBed.inject(AvionService);
      resultAvion = undefined;
    });

    describe('resolve', () => {
      it('should return IAvion returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAvion = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAvion).toEqual({ id: 123 });
      });

      it('should return new IAvion if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAvion = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAvion).toEqual(new Avion());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Avion })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAvion = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAvion).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

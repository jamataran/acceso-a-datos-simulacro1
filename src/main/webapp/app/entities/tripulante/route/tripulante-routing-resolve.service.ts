import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITripulante, Tripulante } from '../tripulante.model';
import { TripulanteService } from '../service/tripulante.service';

@Injectable({ providedIn: 'root' })
export class TripulanteRoutingResolveService implements Resolve<ITripulante> {
  constructor(protected service: TripulanteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITripulante> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tripulante: HttpResponse<Tripulante>) => {
          if (tripulante.body) {
            return of(tripulante.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tripulante());
  }
}

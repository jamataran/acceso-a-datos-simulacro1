import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAeropuerto, Aeropuerto } from '../aeropuerto.model';
import { AeropuertoService } from '../service/aeropuerto.service';

@Injectable({ providedIn: 'root' })
export class AeropuertoRoutingResolveService implements Resolve<IAeropuerto> {
  constructor(protected service: AeropuertoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAeropuerto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((aeropuerto: HttpResponse<Aeropuerto>) => {
          if (aeropuerto.body) {
            return of(aeropuerto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Aeropuerto());
  }
}

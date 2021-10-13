import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVuelo, Vuelo } from '../vuelo.model';
import { VueloService } from '../service/vuelo.service';

@Injectable({ providedIn: 'root' })
export class VueloRoutingResolveService implements Resolve<IVuelo> {
  constructor(protected service: VueloService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVuelo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vuelo: HttpResponse<Vuelo>) => {
          if (vuelo.body) {
            return of(vuelo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vuelo());
  }
}

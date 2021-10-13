import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAvion, Avion } from '../avion.model';
import { AvionService } from '../service/avion.service';

@Injectable({ providedIn: 'root' })
export class AvionRoutingResolveService implements Resolve<IAvion> {
  constructor(protected service: AvionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAvion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((avion: HttpResponse<Avion>) => {
          if (avion.body) {
            return of(avion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Avion());
  }
}

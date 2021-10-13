import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITripulante, getTripulanteIdentifier } from '../tripulante.model';

export type EntityResponseType = HttpResponse<ITripulante>;
export type EntityArrayResponseType = HttpResponse<ITripulante[]>;

@Injectable({ providedIn: 'root' })
export class TripulanteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tripulantes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tripulante: ITripulante): Observable<EntityResponseType> {
    return this.http.post<ITripulante>(this.resourceUrl, tripulante, { observe: 'response' });
  }

  update(tripulante: ITripulante): Observable<EntityResponseType> {
    return this.http.put<ITripulante>(`${this.resourceUrl}/${getTripulanteIdentifier(tripulante) as number}`, tripulante, {
      observe: 'response',
    });
  }

  partialUpdate(tripulante: ITripulante): Observable<EntityResponseType> {
    return this.http.patch<ITripulante>(`${this.resourceUrl}/${getTripulanteIdentifier(tripulante) as number}`, tripulante, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITripulante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITripulante[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTripulanteToCollectionIfMissing(
    tripulanteCollection: ITripulante[],
    ...tripulantesToCheck: (ITripulante | null | undefined)[]
  ): ITripulante[] {
    const tripulantes: ITripulante[] = tripulantesToCheck.filter(isPresent);
    if (tripulantes.length > 0) {
      const tripulanteCollectionIdentifiers = tripulanteCollection.map(tripulanteItem => getTripulanteIdentifier(tripulanteItem)!);
      const tripulantesToAdd = tripulantes.filter(tripulanteItem => {
        const tripulanteIdentifier = getTripulanteIdentifier(tripulanteItem);
        if (tripulanteIdentifier == null || tripulanteCollectionIdentifiers.includes(tripulanteIdentifier)) {
          return false;
        }
        tripulanteCollectionIdentifiers.push(tripulanteIdentifier);
        return true;
      });
      return [...tripulantesToAdd, ...tripulanteCollection];
    }
    return tripulanteCollection;
  }
}

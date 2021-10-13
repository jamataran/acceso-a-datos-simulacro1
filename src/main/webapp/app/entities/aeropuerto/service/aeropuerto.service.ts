import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAeropuerto, getAeropuertoIdentifier } from '../aeropuerto.model';

export type EntityResponseType = HttpResponse<IAeropuerto>;
export type EntityArrayResponseType = HttpResponse<IAeropuerto[]>;

@Injectable({ providedIn: 'root' })
export class AeropuertoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/aeropuertos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(aeropuerto: IAeropuerto): Observable<EntityResponseType> {
    return this.http.post<IAeropuerto>(this.resourceUrl, aeropuerto, { observe: 'response' });
  }

  update(aeropuerto: IAeropuerto): Observable<EntityResponseType> {
    return this.http.put<IAeropuerto>(`${this.resourceUrl}/${getAeropuertoIdentifier(aeropuerto) as number}`, aeropuerto, {
      observe: 'response',
    });
  }

  partialUpdate(aeropuerto: IAeropuerto): Observable<EntityResponseType> {
    return this.http.patch<IAeropuerto>(`${this.resourceUrl}/${getAeropuertoIdentifier(aeropuerto) as number}`, aeropuerto, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAeropuerto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAeropuerto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAeropuertoToCollectionIfMissing(
    aeropuertoCollection: IAeropuerto[],
    ...aeropuertosToCheck: (IAeropuerto | null | undefined)[]
  ): IAeropuerto[] {
    const aeropuertos: IAeropuerto[] = aeropuertosToCheck.filter(isPresent);
    if (aeropuertos.length > 0) {
      const aeropuertoCollectionIdentifiers = aeropuertoCollection.map(aeropuertoItem => getAeropuertoIdentifier(aeropuertoItem)!);
      const aeropuertosToAdd = aeropuertos.filter(aeropuertoItem => {
        const aeropuertoIdentifier = getAeropuertoIdentifier(aeropuertoItem);
        if (aeropuertoIdentifier == null || aeropuertoCollectionIdentifiers.includes(aeropuertoIdentifier)) {
          return false;
        }
        aeropuertoCollectionIdentifiers.push(aeropuertoIdentifier);
        return true;
      });
      return [...aeropuertosToAdd, ...aeropuertoCollection];
    }
    return aeropuertoCollection;
  }
}

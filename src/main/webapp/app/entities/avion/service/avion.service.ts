import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAvion, getAvionIdentifier } from '../avion.model';

export type EntityResponseType = HttpResponse<IAvion>;
export type EntityArrayResponseType = HttpResponse<IAvion[]>;

@Injectable({ providedIn: 'root' })
export class AvionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/avions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(avion: IAvion): Observable<EntityResponseType> {
    return this.http.post<IAvion>(this.resourceUrl, avion, { observe: 'response' });
  }

  update(avion: IAvion): Observable<EntityResponseType> {
    return this.http.put<IAvion>(`${this.resourceUrl}/${getAvionIdentifier(avion) as number}`, avion, { observe: 'response' });
  }

  partialUpdate(avion: IAvion): Observable<EntityResponseType> {
    return this.http.patch<IAvion>(`${this.resourceUrl}/${getAvionIdentifier(avion) as number}`, avion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAvionToCollectionIfMissing(avionCollection: IAvion[], ...avionsToCheck: (IAvion | null | undefined)[]): IAvion[] {
    const avions: IAvion[] = avionsToCheck.filter(isPresent);
    if (avions.length > 0) {
      const avionCollectionIdentifiers = avionCollection.map(avionItem => getAvionIdentifier(avionItem)!);
      const avionsToAdd = avions.filter(avionItem => {
        const avionIdentifier = getAvionIdentifier(avionItem);
        if (avionIdentifier == null || avionCollectionIdentifiers.includes(avionIdentifier)) {
          return false;
        }
        avionCollectionIdentifiers.push(avionIdentifier);
        return true;
      });
      return [...avionsToAdd, ...avionCollection];
    }
    return avionCollection;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMain, getMainIdentifier } from '../main.model';

export type EntityResponseType = HttpResponse<IMain>;
export type EntityArrayResponseType = HttpResponse<IMain[]>;

@Injectable({ providedIn: 'root' })
export class MainService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/mains');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(main: IMain): Observable<EntityResponseType> {
    return this.http.post<IMain>(this.resourceUrl, main, { observe: 'response' });
  }

  update(main: IMain): Observable<EntityResponseType> {
    return this.http.put<IMain>(`${this.resourceUrl}/${getMainIdentifier(main) as number}`, main, { observe: 'response' });
  }

  partialUpdate(main: IMain): Observable<EntityResponseType> {
    return this.http.patch<IMain>(`${this.resourceUrl}/${getMainIdentifier(main) as number}`, main, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMain>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMain[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  geta(): Observable<EntityArrayResponseType> {
    return this.http.get<IMain[]>(this.resourceUrl + 'a', { observe: 'response' });
  }

  getr(): Observable<EntityArrayResponseType> {
    return this.http.get<IMain[]>(this.resourceUrl + 'r', { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMainToCollectionIfMissing(mainCollection: IMain[], ...mainsToCheck: (IMain | null | undefined)[]): IMain[] {
    const mains: IMain[] = mainsToCheck.filter(isPresent);
    if (mains.length > 0) {
      const mainCollectionIdentifiers = mainCollection.map(mainItem => getMainIdentifier(mainItem)!);
      const mainsToAdd = mains.filter(mainItem => {
        const mainIdentifier = getMainIdentifier(mainItem);
        if (mainIdentifier == null || mainCollectionIdentifiers.includes(mainIdentifier)) {
          return false;
        }
        mainCollectionIdentifiers.push(mainIdentifier);
        return true;
      });
      return [...mainsToAdd, ...mainCollection];
    }
    return mainCollection;
  }
}

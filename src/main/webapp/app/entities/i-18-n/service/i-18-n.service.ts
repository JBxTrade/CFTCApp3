import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { II18n, getI18nIdentifier } from '../i-18-n.model';

export type EntityResponseType = HttpResponse<II18n>;
export type EntityArrayResponseType = HttpResponse<II18n[]>;

@Injectable({ providedIn: 'root' })
export class I18nService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/i-18-ns');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(i18n: II18n): Observable<EntityResponseType> {
    return this.http.post<II18n>(this.resourceUrl, i18n, { observe: 'response' });
  }

  update(i18n: II18n): Observable<EntityResponseType> {
    return this.http.put<II18n>(`${this.resourceUrl}/${getI18nIdentifier(i18n) as number}`, i18n, { observe: 'response' });
  }

  partialUpdate(i18n: II18n): Observable<EntityResponseType> {
    return this.http.patch<II18n>(`${this.resourceUrl}/${getI18nIdentifier(i18n) as number}`, i18n, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<II18n>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<II18n[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addI18nToCollectionIfMissing(i18nCollection: II18n[], ...i18nsToCheck: (II18n | null | undefined)[]): II18n[] {
    const i18ns: II18n[] = i18nsToCheck.filter(isPresent);
    if (i18ns.length > 0) {
      const i18nCollectionIdentifiers = i18nCollection.map(i18nItem => getI18nIdentifier(i18nItem)!);
      const i18nsToAdd = i18ns.filter(i18nItem => {
        const i18nIdentifier = getI18nIdentifier(i18nItem);
        if (i18nIdentifier == null || i18nCollectionIdentifiers.includes(i18nIdentifier)) {
          return false;
        }
        i18nCollectionIdentifiers.push(i18nIdentifier);
        return true;
      });
      return [...i18nsToAdd, ...i18nCollection];
    }
    return i18nCollection;
  }
}

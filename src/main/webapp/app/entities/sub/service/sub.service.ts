import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISub, getSubIdentifier } from '../sub.model';
import { ILink } from 'app/entities/link/link.model';
import { ILinkData } from 'app/entities/link-data/link-data.model';

export type EntityResponseType = HttpResponse<ISub>;
export type EntityArrayResponseType = HttpResponse<ISub[]>;

@Injectable({ providedIn: 'root' })
export class SubService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/subs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(sub: ISub): Observable<EntityResponseType> {
    return this.http.post<ISub>(this.resourceUrl, sub, { observe: 'response' });
  }

  update(sub: ISub): Observable<EntityResponseType> {
    return this.http.put<ISub>(`${this.resourceUrl}/${getSubIdentifier(sub) as number}`, sub, { observe: 'response' });
  }

  partialUpdate(sub: ISub): Observable<EntityResponseType> {
    return this.http.patch<ISub>(`${this.resourceUrl}/${getSubIdentifier(sub) as number}`, sub, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISub>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISub[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getLinks(id: number): Observable<HttpResponse<ILink[]>> {
    return this.http.get<ILink[]>(`${this.resourceUrl}/links/${id}`, { observe: 'response' });
  }

  getLinkData(id: number): Observable<HttpResponse<ILinkData[]>> {
    return this.http.get<ILinkData[]>(`${this.resourceUrl}/linkdata/${id}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSubToCollectionIfMissing(subCollection: ISub[], ...subsToCheck: (ISub | null | undefined)[]): ISub[] {
    const subs: ISub[] = subsToCheck.filter(isPresent);
    if (subs.length > 0) {
      const subCollectionIdentifiers = subCollection.map(subItem => getSubIdentifier(subItem)!);
      const subsToAdd = subs.filter(subItem => {
        const subIdentifier = getSubIdentifier(subItem);
        if (subIdentifier == null || subCollectionIdentifiers.includes(subIdentifier)) {
          return false;
        }
        subCollectionIdentifiers.push(subIdentifier);
        return true;
      });
      return [...subsToAdd, ...subCollection];
    }
    return subCollection;
  }
}

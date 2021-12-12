import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUnSub, getUnSubIdentifier } from '../un-sub.model';

export type EntityResponseType = HttpResponse<IUnSub>;
export type EntityArrayResponseType = HttpResponse<IUnSub[]>;

@Injectable({ providedIn: 'root' })
export class UnSubService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/un-subs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(unSub: IUnSub): Observable<EntityResponseType> {
    return this.http.post<IUnSub>(this.resourceUrl, unSub, { observe: 'response' });
  }

  update(unSub: IUnSub): Observable<EntityResponseType> {
    return this.http.put<IUnSub>(`${this.resourceUrl}/${getUnSubIdentifier(unSub) as number}`, unSub, { observe: 'response' });
  }

  partialUpdate(unSub: IUnSub): Observable<EntityResponseType> {
    return this.http.patch<IUnSub>(`${this.resourceUrl}/${getUnSubIdentifier(unSub) as number}`, unSub, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUnSub>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUnSub[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUnSubToCollectionIfMissing(unSubCollection: IUnSub[], ...unSubsToCheck: (IUnSub | null | undefined)[]): IUnSub[] {
    const unSubs: IUnSub[] = unSubsToCheck.filter(isPresent);
    if (unSubs.length > 0) {
      const unSubCollectionIdentifiers = unSubCollection.map(unSubItem => getUnSubIdentifier(unSubItem)!);
      const unSubsToAdd = unSubs.filter(unSubItem => {
        const unSubIdentifier = getUnSubIdentifier(unSubItem);
        if (unSubIdentifier == null || unSubCollectionIdentifiers.includes(unSubIdentifier)) {
          return false;
        }
        unSubCollectionIdentifiers.push(unSubIdentifier);
        return true;
      });
      return [...unSubsToAdd, ...unSubCollection];
    }
    return unSubCollection;
  }
}

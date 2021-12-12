import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILinkData, getLinkDataIdentifier } from '../link-data.model';

export type EntityResponseType = HttpResponse<ILinkData>;
export type EntityArrayResponseType = HttpResponse<ILinkData[]>;

@Injectable({ providedIn: 'root' })
export class LinkDataService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/link-data');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(linkData: ILinkData): Observable<EntityResponseType> {
    return this.http.post<ILinkData>(this.resourceUrl, linkData, { observe: 'response' });
  }

  update(linkData: ILinkData): Observable<EntityResponseType> {
    return this.http.put<ILinkData>(`${this.resourceUrl}/${getLinkDataIdentifier(linkData) as number}`, linkData, { observe: 'response' });
  }

  partialUpdate(linkData: ILinkData): Observable<EntityResponseType> {
    return this.http.patch<ILinkData>(`${this.resourceUrl}/${getLinkDataIdentifier(linkData) as number}`, linkData, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILinkData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILinkData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLinkDataToCollectionIfMissing(linkDataCollection: ILinkData[], ...linkDataToCheck: (ILinkData | null | undefined)[]): ILinkData[] {
    const linkData: ILinkData[] = linkDataToCheck.filter(isPresent);
    if (linkData.length > 0) {
      const linkDataCollectionIdentifiers = linkDataCollection.map(linkDataItem => getLinkDataIdentifier(linkDataItem)!);
      const linkDataToAdd = linkData.filter(linkDataItem => {
        const linkDataIdentifier = getLinkDataIdentifier(linkDataItem);
        if (linkDataIdentifier == null || linkDataCollectionIdentifiers.includes(linkDataIdentifier)) {
          return false;
        }
        linkDataCollectionIdentifiers.push(linkDataIdentifier);
        return true;
      });
      return [...linkDataToAdd, ...linkDataCollection];
    }
    return linkDataCollection;
  }
}

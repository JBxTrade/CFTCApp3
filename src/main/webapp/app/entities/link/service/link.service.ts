import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILink, getLinkIdentifier } from '../link.model';

export type EntityResponseType = HttpResponse<ILink>;
export type EntityArrayResponseType = HttpResponse<ILink[]>;

@Injectable({ providedIn: 'root' })
export class LinkService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/links');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(link: ILink): Observable<EntityResponseType> {
    return this.http.post<ILink>(this.resourceUrl, link, { observe: 'response' });
  }

  update(link: ILink): Observable<EntityResponseType> {
    return this.http.put<ILink>(`${this.resourceUrl}/${getLinkIdentifier(link) as number}`, link, { observe: 'response' });
  }

  partialUpdate(link: ILink): Observable<EntityResponseType> {
    return this.http.patch<ILink>(`${this.resourceUrl}/${getLinkIdentifier(link) as number}`, link, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILink>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILink[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLinkToCollectionIfMissing(linkCollection: ILink[], ...linksToCheck: (ILink | null | undefined)[]): ILink[] {
    const links: ILink[] = linksToCheck.filter(isPresent);
    if (links.length > 0) {
      const linkCollectionIdentifiers = linkCollection.map(linkItem => getLinkIdentifier(linkItem)!);
      const linksToAdd = links.filter(linkItem => {
        const linkIdentifier = getLinkIdentifier(linkItem);
        if (linkIdentifier == null || linkCollectionIdentifiers.includes(linkIdentifier)) {
          return false;
        }
        linkCollectionIdentifiers.push(linkIdentifier);
        return true;
      });
      return [...linksToAdd, ...linkCollection];
    }
    return linkCollection;
  }
}

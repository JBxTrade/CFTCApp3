import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { II18n, I18n } from '../i-18-n.model';
import { I18nService } from '../service/i-18-n.service';

@Injectable({ providedIn: 'root' })
export class I18nRoutingResolveService implements Resolve<II18n> {
  constructor(protected service: I18nService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<II18n> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((i18n: HttpResponse<I18n>) => {
          if (i18n.body) {
            return of(i18n.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new I18n());
  }
}

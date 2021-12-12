import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUnSub, UnSub } from '../un-sub.model';
import { UnSubService } from '../service/un-sub.service';

@Injectable({ providedIn: 'root' })
export class UnSubRoutingResolveService implements Resolve<IUnSub> {
  constructor(protected service: UnSubService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnSub> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((unSub: HttpResponse<UnSub>) => {
          if (unSub.body) {
            return of(unSub.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UnSub());
  }
}

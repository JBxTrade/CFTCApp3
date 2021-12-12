import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMain, Main } from '../main.model';
import { MainService } from '../service/main.service';

@Injectable({ providedIn: 'root' })
export class MainRoutingResolveService implements Resolve<IMain> {
  constructor(protected service: MainService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMain> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((main: HttpResponse<Main>) => {
          if (main.body) {
            return of(main.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Main());
  }
}

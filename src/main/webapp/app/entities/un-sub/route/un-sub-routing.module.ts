import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UnSubComponent } from '../list/un-sub.component';
import { UnSubDetailComponent } from '../detail/un-sub-detail.component';
import { UnSubUpdateComponent } from '../update/un-sub-update.component';
import { UnSubRoutingResolveService } from './un-sub-routing-resolve.service';

const unSubRoute: Routes = [
  {
    path: '',
    component: UnSubComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UnSubDetailComponent,
    resolve: {
      unSub: UnSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UnSubUpdateComponent,
    resolve: {
      unSub: UnSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UnSubUpdateComponent,
    resolve: {
      unSub: UnSubRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(unSubRoute)],
  exports: [RouterModule],
})
export class UnSubRoutingModule {}

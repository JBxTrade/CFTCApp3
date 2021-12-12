import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LinkDataComponent } from '../list/link-data.component';
import { LinkDataDetailComponent } from '../detail/link-data-detail.component';
import { LinkDataUpdateComponent } from '../update/link-data-update.component';
import { LinkDataRoutingResolveService } from './link-data-routing-resolve.service';

const linkDataRoute: Routes = [
  {
    path: '',
    component: LinkDataComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LinkDataDetailComponent,
    resolve: {
      linkData: LinkDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LinkDataUpdateComponent,
    resolve: {
      linkData: LinkDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LinkDataUpdateComponent,
    resolve: {
      linkData: LinkDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(linkDataRoute)],
  exports: [RouterModule],
})
export class LinkDataRoutingModule {}

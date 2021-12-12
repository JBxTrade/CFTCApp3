import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MainComponent } from '../list/main.component';
import { MainDetailComponent } from '../detail/main-detail.component';
import { MainUpdateComponent } from '../update/main-update.component';
import { MainRoutingResolveService } from './main-routing-resolve.service';

const mainRoute: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MainDetailComponent,
    resolve: {
      main: MainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MainUpdateComponent,
    resolve: {
      main: MainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MainUpdateComponent,
    resolve: {
      main: MainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mainRoute)],
  exports: [RouterModule],
})
export class MainRoutingModule {}

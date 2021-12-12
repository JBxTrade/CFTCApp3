import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { I18nComponent } from '../list/i-18-n.component';
import { I18nDetailComponent } from '../detail/i-18-n-detail.component';
import { I18nUpdateComponent } from '../update/i-18-n-update.component';
import { I18nRoutingResolveService } from './i-18-n-routing-resolve.service';

const i18nRoute: Routes = [
  {
    path: '',
    component: I18nComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: I18nDetailComponent,
    resolve: {
      i18n: I18nRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: I18nUpdateComponent,
    resolve: {
      i18n: I18nRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: I18nUpdateComponent,
    resolve: {
      i18n: I18nRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(i18nRoute)],
  exports: [RouterModule],
})
export class I18nRoutingModule {}

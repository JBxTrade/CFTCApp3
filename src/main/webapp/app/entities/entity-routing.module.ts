import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'main',
        data: { pageTitle: 'cftcApp3App.main.home.title' },
        loadChildren: () => import('./main/main.module').then(m => m.MainModule),
      },
      {
        path: 'sub',
        data: { pageTitle: 'cftcApp3App.sub.home.title' },
        loadChildren: () => import('./sub/sub.module').then(m => m.SubModule),
      },
      {
        path: 'un-sub',
        data: { pageTitle: 'cftcApp3App.unSub.home.title' },
        loadChildren: () => import('./un-sub/un-sub.module').then(m => m.UnSubModule),
      },
      {
        path: 'link',
        data: { pageTitle: 'cftcApp3App.link.home.title' },
        loadChildren: () => import('./link/link.module').then(m => m.LinkModule),
      },
      {
        path: 'link-data',
        data: { pageTitle: 'cftcApp3App.linkData.home.title' },
        loadChildren: () => import('./link-data/link-data.module').then(m => m.LinkDataModule),
      },
      {
        path: 'i-18-n',
        data: { pageTitle: 'cftcApp3App.i18n.home.title' },
        loadChildren: () => import('./i-18-n/i-18-n.module').then(m => m.I18nModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

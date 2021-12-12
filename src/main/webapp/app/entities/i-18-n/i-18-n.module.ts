import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { I18nComponent } from './list/i-18-n.component';
import { I18nDetailComponent } from './detail/i-18-n-detail.component';
import { I18nUpdateComponent } from './update/i-18-n-update.component';
import { I18nDeleteDialogComponent } from './delete/i-18-n-delete-dialog.component';
import { I18nRoutingModule } from './route/i-18-n-routing.module';

@NgModule({
  imports: [SharedModule, I18nRoutingModule],
  declarations: [I18nComponent, I18nDetailComponent, I18nUpdateComponent, I18nDeleteDialogComponent],
  entryComponents: [I18nDeleteDialogComponent],
})
export class I18nModule {}

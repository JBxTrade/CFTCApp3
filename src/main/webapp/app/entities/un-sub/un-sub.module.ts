import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { UnSubComponent } from './list/un-sub.component';
import { UnSubDetailComponent } from './detail/un-sub-detail.component';
import { UnSubUpdateComponent } from './update/un-sub-update.component';
import { UnSubDeleteDialogComponent } from './delete/un-sub-delete-dialog.component';
import { UnSubRoutingModule } from './route/un-sub-routing.module';

@NgModule({
  imports: [SharedModule, UnSubRoutingModule],
  declarations: [UnSubComponent, UnSubDetailComponent, UnSubUpdateComponent, UnSubDeleteDialogComponent],
  entryComponents: [UnSubDeleteDialogComponent],
})
export class UnSubModule {}

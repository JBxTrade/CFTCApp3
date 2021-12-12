import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { LinkDataComponent } from './list/link-data.component';
import { LinkDataDetailComponent } from './detail/link-data-detail.component';
import { LinkDataUpdateComponent } from './update/link-data-update.component';
import { LinkDataDeleteDialogComponent } from './delete/link-data-delete-dialog.component';
import { LinkDataRoutingModule } from './route/link-data-routing.module';

@NgModule({
  imports: [SharedModule, LinkDataRoutingModule],
  declarations: [LinkDataComponent, LinkDataDetailComponent, LinkDataUpdateComponent, LinkDataDeleteDialogComponent],
  entryComponents: [LinkDataDeleteDialogComponent],
})
export class LinkDataModule {}

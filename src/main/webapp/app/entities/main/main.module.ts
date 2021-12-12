import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MainComponent } from './list/main.component';
import { MainDetailComponent } from './detail/main-detail.component';
import { MainUpdateComponent } from './update/main-update.component';
import { MainDeleteDialogComponent } from './delete/main-delete-dialog.component';
import { MainRoutingModule } from './route/main-routing.module';

@NgModule({
  imports: [SharedModule, MainRoutingModule],
  declarations: [MainComponent, MainDetailComponent, MainUpdateComponent, MainDeleteDialogComponent],
  entryComponents: [MainDeleteDialogComponent],
})
export class MainModule {}

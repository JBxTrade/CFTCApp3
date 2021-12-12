import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnSub } from '../un-sub.model';
import { UnSubService } from '../service/un-sub.service';

@Component({
  templateUrl: './un-sub-delete-dialog.component.html',
})
export class UnSubDeleteDialogComponent {
  unSub?: IUnSub;

  constructor(protected unSubService: UnSubService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.unSubService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

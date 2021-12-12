import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISub } from '../sub.model';
import { SubService } from '../service/sub.service';

@Component({
  templateUrl: './sub-delete-dialog.component.html',
})
export class SubDeleteDialogComponent {
  sub?: ISub;

  constructor(protected subService: SubService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMain } from '../main.model';
import { MainService } from '../service/main.service';

@Component({
  templateUrl: './main-delete-dialog.component.html',
})
export class MainDeleteDialogComponent {
  main?: IMain;

  constructor(protected mainService: MainService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mainService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { II18n } from '../i-18-n.model';
import { I18nService } from '../service/i-18-n.service';

@Component({
  templateUrl: './i-18-n-delete-dialog.component.html',
})
export class I18nDeleteDialogComponent {
  i18n?: II18n;

  constructor(protected i18nService: I18nService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.i18nService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

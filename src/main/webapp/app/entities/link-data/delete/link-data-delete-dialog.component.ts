import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILinkData } from '../link-data.model';
import { LinkDataService } from '../service/link-data.service';

@Component({
  templateUrl: './link-data-delete-dialog.component.html',
})
export class LinkDataDeleteDialogComponent {
  linkData?: ILinkData;

  constructor(protected linkDataService: LinkDataService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.linkDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}

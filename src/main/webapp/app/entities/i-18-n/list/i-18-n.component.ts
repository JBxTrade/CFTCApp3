import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { II18n } from '../i-18-n.model';
import { I18nService } from '../service/i-18-n.service';
import { I18nDeleteDialogComponent } from '../delete/i-18-n-delete-dialog.component';

@Component({
  selector: 'jhi-i-18-n',
  templateUrl: './i-18-n.component.html',
})
export class I18nComponent implements OnInit {
  i18ns?: II18n[];
  isLoading = false;

  constructor(protected i18nService: I18nService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.i18nService.query().subscribe(
      (res: HttpResponse<II18n[]>) => {
        this.isLoading = false;
        this.i18ns = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: II18n): number {
    return item.id!;
  }

  delete(i18n: II18n): void {
    const modalRef = this.modalService.open(I18nDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.i18n = i18n;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

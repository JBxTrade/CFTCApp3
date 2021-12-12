import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnSub } from '../un-sub.model';
import { UnSubService } from '../service/un-sub.service';
import { UnSubDeleteDialogComponent } from '../delete/un-sub-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-un-sub',
  templateUrl: './un-sub.component.html',
})
export class UnSubComponent implements OnInit {
  unSubs?: IUnSub[];
  isLoading = false;

  constructor(protected unSubService: UnSubService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.unSubService.query().subscribe(
      (res: HttpResponse<IUnSub[]>) => {
        this.isLoading = false;
        this.unSubs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IUnSub): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(unSub: IUnSub): void {
    const modalRef = this.modalService.open(UnSubDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unSub = unSub;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISub } from '../sub.model';
import { SubService } from '../service/sub.service';
import { SubDeleteDialogComponent } from '../delete/sub-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-sub',
  templateUrl: './sub.component.html',
})
export class SubComponent implements OnInit {
  subs?: ISub[];
  isLoading = false;

  constructor(protected subService: SubService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.subService.query().subscribe(
      (res: HttpResponse<ISub[]>) => {
        this.isLoading = false;
        this.subs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISub): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(sub: ISub): void {
    const modalRef = this.modalService.open(SubDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sub = sub;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

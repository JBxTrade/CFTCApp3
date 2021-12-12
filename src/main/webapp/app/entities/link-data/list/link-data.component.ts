import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILinkData } from '../link-data.model';
import { LinkDataService } from '../service/link-data.service';
import { LinkDataDeleteDialogComponent } from '../delete/link-data-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-link-data',
  templateUrl: './link-data.component.html',
})
export class LinkDataComponent implements OnInit {
  linkData?: ILinkData[];
  isLoading = false;

  constructor(protected linkDataService: LinkDataService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.linkDataService.query().subscribe(
      (res: HttpResponse<ILinkData[]>) => {
        this.isLoading = false;
        this.linkData = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILinkData): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(linkData: ILinkData): void {
    const modalRef = this.modalService.open(LinkDataDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.linkData = linkData;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

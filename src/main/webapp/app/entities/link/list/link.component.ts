import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILink } from '../link.model';
import { LinkService } from '../service/link.service';
import { LinkDeleteDialogComponent } from '../delete/link-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-link',
  templateUrl: './link.component.html',
})
export class LinkComponent implements OnInit {
  links?: ILink[];
  isLoading = false;

  constructor(protected linkService: LinkService, protected dataUtils: DataUtils, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.linkService.query().subscribe(
      (res: HttpResponse<ILink[]>) => {
        this.isLoading = false;
        this.links = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ILink): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  delete(link: ILink): void {
    const modalRef = this.modalService.open(LinkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.link = link;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISub } from '../sub.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { SubService } from 'app/entities/sub/service/sub.service';
import { ILinkData } from 'app/entities/link-data/link-data.model';
import { ILink } from 'app/entities/link/link.model';
import { AccountService } from 'app/core/auth/account.service';
import { TranslateService } from '@ngx-translate/core';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-sub-detail',
  templateUrl: './sub-detail.component.html',
})
export class SubDetailComponent implements OnInit {
  sub: ISub | null = null;
  links?: ILink[];
  linkData?: ILinkData[];

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    protected translateService: TranslateService,
    protected subService: SubService
  ) {}

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  getlanguage(): boolean {
    const language = this.translateService.currentLang;

    if (language === 'fr') {
      return true;
    } else {
      return false;
    }
  }

  getLinks(): void {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    this.subService.getLinks(this.sub?.id).subscribe((res: HttpResponse<ILink[]>) => (this.links = res.body ?? []));
  }

  getLinkData(): void {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    this.subService.getLinkData(this.sub?.id).subscribe((res: HttpResponse<ILinkData[]>) => (this.linkData = res.body ?? []));
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sub }) => {
      this.sub = sub;
    });
    this.getLinks();
    this.getLinkData();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMain } from '../main.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-main-detail',
  templateUrl: './main-detail.component.html',
})
export class MainDetailComponent implements OnInit {
  main: IMain | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ main }) => {
      this.main = main;
    });
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

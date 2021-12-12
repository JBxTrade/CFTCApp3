import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { II18n } from '../i-18-n.model';

@Component({
  selector: 'jhi-i-18-n-detail',
  templateUrl: './i-18-n-detail.component.html',
})
export class I18nDetailComponent implements OnInit {
  i18n: II18n | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ i18n }) => {
      this.i18n = i18n;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

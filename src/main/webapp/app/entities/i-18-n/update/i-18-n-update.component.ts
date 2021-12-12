import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { II18n, I18n } from '../i-18-n.model';
import { I18nService } from '../service/i-18-n.service';

@Component({
  selector: 'jhi-i-18-n-update',
  templateUrl: './i-18-n-update.component.html',
})
export class I18nUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fr: [],
    en: [],
  });

  constructor(protected i18nService: I18nService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ i18n }) => {
      this.updateForm(i18n);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const i18n = this.createFromForm();
    if (i18n.id !== undefined) {
      this.subscribeToSaveResponse(this.i18nService.update(i18n));
    } else {
      this.subscribeToSaveResponse(this.i18nService.create(i18n));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<II18n>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(i18n: II18n): void {
    this.editForm.patchValue({
      id: i18n.id,
      fr: i18n.fr,
      en: i18n.en,
    });
  }

  protected createFromForm(): II18n {
    return {
      ...new I18n(),
      id: this.editForm.get(['id'])!.value,
      fr: this.editForm.get(['fr'])!.value,
      en: this.editForm.get(['en'])!.value,
    };
  }
}

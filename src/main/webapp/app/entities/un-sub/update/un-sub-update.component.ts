import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IUnSub, UnSub } from '../un-sub.model';
import { UnSubService } from '../service/un-sub.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';

@Component({
  selector: 'jhi-un-sub-update',
  templateUrl: './un-sub-update.component.html',
})
export class UnSubUpdateComponent implements OnInit {
  isSaving = false;

  titleFrsCollection: II18n[] = [];
  titleEnsCollection: II18n[] = [];
  descriptionFrsCollection: II18n[] = [];
  descriptionEnsCollection: II18n[] = [];

  editForm = this.fb.group({
    id: [],
    theRole: [null, [Validators.required]],
    image: [null, [Validators.required]],
    imageContentType: [],
    titleFr: [],
    titleEn: [],
    descriptionFr: [],
    descriptionEn: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected unSubService: UnSubService,
    protected i18nService: I18nService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unSub }) => {
      this.updateForm(unSub);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('cftcApp3App.error', { ...err, key: 'error.file.' + err.key })
        ),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unSub = this.createFromForm();
    if (unSub.id !== undefined) {
      this.subscribeToSaveResponse(this.unSubService.update(unSub));
    } else {
      this.subscribeToSaveResponse(this.unSubService.create(unSub));
    }
  }

  trackI18nById(index: number, item: II18n): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnSub>>): void {
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

  protected updateForm(unSub: IUnSub): void {
    this.editForm.patchValue({
      id: unSub.id,
      theRole: unSub.theRole,
      image: unSub.image,
      imageContentType: unSub.imageContentType,
      titleFr: unSub.titleFr,
      titleEn: unSub.titleEn,
      descriptionFr: unSub.descriptionFr,
      descriptionEn: unSub.descriptionEn,
    });

    this.titleFrsCollection = this.i18nService.addI18nToCollectionIfMissing(this.titleFrsCollection, unSub.titleFr);
    this.titleEnsCollection = this.i18nService.addI18nToCollectionIfMissing(this.titleEnsCollection, unSub.titleEn);
    this.descriptionFrsCollection = this.i18nService.addI18nToCollectionIfMissing(this.descriptionFrsCollection, unSub.descriptionFr);
    this.descriptionEnsCollection = this.i18nService.addI18nToCollectionIfMissing(this.descriptionEnsCollection, unSub.descriptionEn);
  }

  protected loadRelationshipsOptions(): void {
    this.i18nService
      .query({ filter: 'unsubtitlefr-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('titleFr')!.value)))
      .subscribe((i18ns: II18n[]) => (this.titleFrsCollection = i18ns));

    this.i18nService
      .query({ filter: 'unsubtitleen-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('titleEn')!.value)))
      .subscribe((i18ns: II18n[]) => (this.titleEnsCollection = i18ns));

    this.i18nService
      .query({ filter: 'unsubdescriptionfr-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('descriptionFr')!.value)))
      .subscribe((i18ns: II18n[]) => (this.descriptionFrsCollection = i18ns));

    this.i18nService
      .query({ filter: 'unsubdescriptionen-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('descriptionEn')!.value)))
      .subscribe((i18ns: II18n[]) => (this.descriptionEnsCollection = i18ns));
  }

  protected createFromForm(): IUnSub {
    return {
      ...new UnSub(),
      id: this.editForm.get(['id'])!.value,
      theRole: this.editForm.get(['theRole'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      titleFr: this.editForm.get(['titleFr'])!.value,
      titleEn: this.editForm.get(['titleEn'])!.value,
      descriptionFr: this.editForm.get(['descriptionFr'])!.value,
      descriptionEn: this.editForm.get(['descriptionEn'])!.value,
    };
  }
}

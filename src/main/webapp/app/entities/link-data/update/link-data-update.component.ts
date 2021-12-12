import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILinkData, LinkData } from '../link-data.model';
import { LinkDataService } from '../service/link-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';
import { ISub } from 'app/entities/sub/sub.model';
import { SubService } from 'app/entities/sub/service/sub.service';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { UnSubService } from 'app/entities/un-sub/service/un-sub.service';

@Component({
  selector: 'jhi-link-data-update',
  templateUrl: './link-data-update.component.html',
})
export class LinkDataUpdateComponent implements OnInit {
  isSaving = false;

  titleFrsCollection: II18n[] = [];
  titleEnsCollection: II18n[] = [];
  descriptionFrsCollection: II18n[] = [];
  descriptionEnsCollection: II18n[] = [];
  codeFrsCollection: II18n[] = [];
  codeEnsCollection: II18n[] = [];
  subsSharedCollection: ISub[] = [];
  unSubsSharedCollection: IUnSub[] = [];

  editForm = this.fb.group({
    id: [],
    theRole: [null, [Validators.required]],
    imageCard: [null, [Validators.required]],
    imageCardContentType: [],
    image: [],
    imageContentType: [],
    image2: [],
    image2ContentType: [],
    image3: [],
    image3ContentType: [],
    titleFr: [],
    titleEn: [],
    descriptionFr: [],
    descriptionEn: [],
    codeFr: [],
    codeEn: [],
    sub: [],
    unSub: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected linkDataService: LinkDataService,
    protected i18nService: I18nService,
    protected subService: SubService,
    protected unSubService: UnSubService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ linkData }) => {
      this.updateForm(linkData);

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
    const linkData = this.createFromForm();
    if (linkData.id !== undefined) {
      this.subscribeToSaveResponse(this.linkDataService.update(linkData));
    } else {
      this.subscribeToSaveResponse(this.linkDataService.create(linkData));
    }
  }

  trackI18nById(index: number, item: II18n): number {
    return item.id!;
  }

  trackSubById(index: number, item: ISub): number {
    return item.id!;
  }

  trackUnSubById(index: number, item: IUnSub): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILinkData>>): void {
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

  protected updateForm(linkData: ILinkData): void {
    this.editForm.patchValue({
      id: linkData.id,
      theRole: linkData.theRole,
      imageCard: linkData.imageCard,
      imageCardContentType: linkData.imageCardContentType,
      image: linkData.image,
      imageContentType: linkData.imageContentType,
      image2: linkData.image2,
      image2ContentType: linkData.image2ContentType,
      image3: linkData.image3,
      image3ContentType: linkData.image3ContentType,
      titleFr: linkData.titleFr,
      titleEn: linkData.titleEn,
      descriptionFr: linkData.descriptionFr,
      descriptionEn: linkData.descriptionEn,
      codeFr: linkData.codeFr,
      codeEn: linkData.codeEn,
      sub: linkData.sub,
      unSub: linkData.unSub,
    });

    this.titleFrsCollection = this.i18nService.addI18nToCollectionIfMissing(this.titleFrsCollection, linkData.titleFr);
    this.titleEnsCollection = this.i18nService.addI18nToCollectionIfMissing(this.titleEnsCollection, linkData.titleEn);
    this.descriptionFrsCollection = this.i18nService.addI18nToCollectionIfMissing(this.descriptionFrsCollection, linkData.descriptionFr);
    this.descriptionEnsCollection = this.i18nService.addI18nToCollectionIfMissing(this.descriptionEnsCollection, linkData.descriptionEn);
    this.codeFrsCollection = this.i18nService.addI18nToCollectionIfMissing(this.codeFrsCollection, linkData.codeFr);
    this.codeEnsCollection = this.i18nService.addI18nToCollectionIfMissing(this.codeEnsCollection, linkData.codeEn);
    this.subsSharedCollection = this.subService.addSubToCollectionIfMissing(this.subsSharedCollection, linkData.sub);
    this.unSubsSharedCollection = this.unSubService.addUnSubToCollectionIfMissing(this.unSubsSharedCollection, linkData.unSub);
  }

  protected loadRelationshipsOptions(): void {
    this.i18nService
      .query({ filter: 'linkdatatitlefr-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('titleFr')!.value)))
      .subscribe((i18ns: II18n[]) => (this.titleFrsCollection = i18ns));

    this.i18nService
      .query({ filter: 'linkdatatitleen-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('titleEn')!.value)))
      .subscribe((i18ns: II18n[]) => (this.titleEnsCollection = i18ns));

    this.i18nService
      .query({ filter: 'linkdatadescriptionfr-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('descriptionFr')!.value)))
      .subscribe((i18ns: II18n[]) => (this.descriptionFrsCollection = i18ns));

    this.i18nService
      .query({ filter: 'linkdatadescriptionen-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('descriptionEn')!.value)))
      .subscribe((i18ns: II18n[]) => (this.descriptionEnsCollection = i18ns));

    this.i18nService
      .query({ filter: 'codefr-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('codeFr')!.value)))
      .subscribe((i18ns: II18n[]) => (this.codeFrsCollection = i18ns));

    this.i18nService
      .query({ filter: 'codeen-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('codeEn')!.value)))
      .subscribe((i18ns: II18n[]) => (this.codeEnsCollection = i18ns));

    this.subService
      .query()
      .pipe(map((res: HttpResponse<ISub[]>) => res.body ?? []))
      .pipe(map((subs: ISub[]) => this.subService.addSubToCollectionIfMissing(subs, this.editForm.get('sub')!.value)))
      .subscribe((subs: ISub[]) => (this.subsSharedCollection = subs));

    this.unSubService
      .query()
      .pipe(map((res: HttpResponse<IUnSub[]>) => res.body ?? []))
      .pipe(map((unSubs: IUnSub[]) => this.unSubService.addUnSubToCollectionIfMissing(unSubs, this.editForm.get('unSub')!.value)))
      .subscribe((unSubs: IUnSub[]) => (this.unSubsSharedCollection = unSubs));
  }

  protected createFromForm(): ILinkData {
    return {
      ...new LinkData(),
      id: this.editForm.get(['id'])!.value,
      theRole: this.editForm.get(['theRole'])!.value,
      imageCardContentType: this.editForm.get(['imageCardContentType'])!.value,
      imageCard: this.editForm.get(['imageCard'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      image2ContentType: this.editForm.get(['image2ContentType'])!.value,
      image2: this.editForm.get(['image2'])!.value,
      image3ContentType: this.editForm.get(['image3ContentType'])!.value,
      image3: this.editForm.get(['image3'])!.value,
      titleFr: this.editForm.get(['titleFr'])!.value,
      titleEn: this.editForm.get(['titleEn'])!.value,
      descriptionFr: this.editForm.get(['descriptionFr'])!.value,
      descriptionEn: this.editForm.get(['descriptionEn'])!.value,
      codeFr: this.editForm.get(['codeFr'])!.value,
      codeEn: this.editForm.get(['codeEn'])!.value,
      sub: this.editForm.get(['sub'])!.value,
      unSub: this.editForm.get(['unSub'])!.value,
    };
  }
}

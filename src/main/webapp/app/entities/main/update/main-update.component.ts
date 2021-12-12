import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMain, Main } from '../main.model';
import { MainService } from '../service/main.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISub } from 'app/entities/sub/sub.model';
import { SubService } from 'app/entities/sub/service/sub.service';
import { ILink } from 'app/entities/link/link.model';
import { LinkService } from 'app/entities/link/service/link.service';
import { ILinkData } from 'app/entities/link-data/link-data.model';
import { LinkDataService } from 'app/entities/link-data/service/link-data.service';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';

@Component({
  selector: 'jhi-main-update',
  templateUrl: './main-update.component.html',
})
export class MainUpdateComponent implements OnInit {
  isSaving = false;

  subLinksCollection: ISub[] = [];
  linksCollection: ILink[] = [];
  linkDataCollection: ILinkData[] = [];
  titleFrsCollection: II18n[] = [];
  titleEnsCollection: II18n[] = [];
  descriptionFrsCollection: II18n[] = [];
  descriptionEnsCollection: II18n[] = [];

  editForm = this.fb.group({
    id: [],
    theRole: [null, [Validators.required]],
    image: [null, [Validators.required]],
    imageContentType: [],
    subLink: [],
    link: [],
    linkData: [],
    titleFr: [],
    titleEn: [],
    descriptionFr: [],
    descriptionEn: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected mainService: MainService,
    protected subService: SubService,
    protected linkService: LinkService,
    protected linkDataService: LinkDataService,
    protected i18nService: I18nService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ main }) => {
      this.updateForm(main);

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
    const main = this.createFromForm();
    if (main.id !== undefined) {
      this.subscribeToSaveResponse(this.mainService.update(main));
    } else {
      this.subscribeToSaveResponse(this.mainService.create(main));
    }
  }

  trackSubById(index: number, item: ISub): number {
    return item.id!;
  }

  trackLinkById(index: number, item: ILink): number {
    return item.id!;
  }

  trackLinkDataById(index: number, item: ILinkData): number {
    return item.id!;
  }

  trackI18nById(index: number, item: II18n): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMain>>): void {
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

  protected updateForm(main: IMain): void {
    this.editForm.patchValue({
      id: main.id,
      theRole: main.theRole,
      image: main.image,
      imageContentType: main.imageContentType,
      subLink: main.subLink,
      link: main.link,
      linkData: main.linkData,
      titleFr: main.titleFr,
      titleEn: main.titleEn,
      descriptionFr: main.descriptionFr,
      descriptionEn: main.descriptionEn,
    });

    this.subLinksCollection = this.subService.addSubToCollectionIfMissing(this.subLinksCollection, main.subLink);
    this.linksCollection = this.linkService.addLinkToCollectionIfMissing(this.linksCollection, main.link);
    this.linkDataCollection = this.linkDataService.addLinkDataToCollectionIfMissing(this.linkDataCollection, main.linkData);
    this.titleFrsCollection = this.i18nService.addI18nToCollectionIfMissing(this.titleFrsCollection, main.titleFr);
    this.titleEnsCollection = this.i18nService.addI18nToCollectionIfMissing(this.titleEnsCollection, main.titleEn);
    this.descriptionFrsCollection = this.i18nService.addI18nToCollectionIfMissing(this.descriptionFrsCollection, main.descriptionFr);
    this.descriptionEnsCollection = this.i18nService.addI18nToCollectionIfMissing(this.descriptionEnsCollection, main.descriptionEn);
  }

  protected loadRelationshipsOptions(): void {
    this.subService
      .query({ filter: 'main-is-null' })
      .pipe(map((res: HttpResponse<ISub[]>) => res.body ?? []))
      .pipe(map((subs: ISub[]) => this.subService.addSubToCollectionIfMissing(subs, this.editForm.get('subLink')!.value)))
      .subscribe((subs: ISub[]) => (this.subLinksCollection = subs));

    this.linkService
      .query({ filter: 'main-is-null' })
      .pipe(map((res: HttpResponse<ILink[]>) => res.body ?? []))
      .pipe(map((links: ILink[]) => this.linkService.addLinkToCollectionIfMissing(links, this.editForm.get('link')!.value)))
      .subscribe((links: ILink[]) => (this.linksCollection = links));

    this.linkDataService
      .query({ filter: 'main-is-null' })
      .pipe(map((res: HttpResponse<ILinkData[]>) => res.body ?? []))
      .pipe(
        map((linkData: ILinkData[]) =>
          this.linkDataService.addLinkDataToCollectionIfMissing(linkData, this.editForm.get('linkData')!.value)
        )
      )
      .subscribe((linkData: ILinkData[]) => (this.linkDataCollection = linkData));

    this.i18nService
      .query({ filter: 'maintitlefr-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('titleFr')!.value)))
      .subscribe((i18ns: II18n[]) => (this.titleFrsCollection = i18ns));

    this.i18nService
      .query({ filter: 'maintitleen-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('titleEn')!.value)))
      .subscribe((i18ns: II18n[]) => (this.titleEnsCollection = i18ns));

    this.i18nService
      .query({ filter: 'maindescriptionfr-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('descriptionFr')!.value)))
      .subscribe((i18ns: II18n[]) => (this.descriptionFrsCollection = i18ns));

    this.i18nService
      .query({ filter: 'maindescriptionen-is-null' })
      .pipe(map((res: HttpResponse<II18n[]>) => res.body ?? []))
      .pipe(map((i18ns: II18n[]) => this.i18nService.addI18nToCollectionIfMissing(i18ns, this.editForm.get('descriptionEn')!.value)))
      .subscribe((i18ns: II18n[]) => (this.descriptionEnsCollection = i18ns));
  }

  protected createFromForm(): IMain {
    return {
      ...new Main(),
      id: this.editForm.get(['id'])!.value,
      theRole: this.editForm.get(['theRole'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      subLink: this.editForm.get(['subLink'])!.value,
      link: this.editForm.get(['link'])!.value,
      linkData: this.editForm.get(['linkData'])!.value,
      titleFr: this.editForm.get(['titleFr'])!.value,
      titleEn: this.editForm.get(['titleEn'])!.value,
      descriptionFr: this.editForm.get(['descriptionFr'])!.value,
      descriptionEn: this.editForm.get(['descriptionEn'])!.value,
    };
  }
}

jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LinkDataService } from '../service/link-data.service';
import { ILinkData, LinkData } from '../link-data.model';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';
import { ISub } from 'app/entities/sub/sub.model';
import { SubService } from 'app/entities/sub/service/sub.service';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { UnSubService } from 'app/entities/un-sub/service/un-sub.service';

import { LinkDataUpdateComponent } from './link-data-update.component';

describe('Component Tests', () => {
  describe('LinkData Management Update Component', () => {
    let comp: LinkDataUpdateComponent;
    let fixture: ComponentFixture<LinkDataUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let linkDataService: LinkDataService;
    let i18nService: I18nService;
    let subService: SubService;
    let unSubService: UnSubService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LinkDataUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LinkDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LinkDataUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      linkDataService = TestBed.inject(LinkDataService);
      i18nService = TestBed.inject(I18nService);
      subService = TestBed.inject(SubService);
      unSubService = TestBed.inject(UnSubService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call titleFr query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const titleFr: II18n = { id: 1259 };
        linkData.titleFr = titleFr;

        const titleFrCollection: II18n[] = [{ id: 60617 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleFrCollection })));
        const expectedCollection: II18n[] = [titleFr, ...titleFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleFrCollection, titleFr);
        expect(comp.titleFrsCollection).toEqual(expectedCollection);
      });

      it('Should call titleEn query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const titleEn: II18n = { id: 55637 };
        linkData.titleEn = titleEn;

        const titleEnCollection: II18n[] = [{ id: 98225 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleEnCollection })));
        const expectedCollection: II18n[] = [titleEn, ...titleEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleEnCollection, titleEn);
        expect(comp.titleEnsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionFr query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const descriptionFr: II18n = { id: 53683 };
        linkData.descriptionFr = descriptionFr;

        const descriptionFrCollection: II18n[] = [{ id: 54045 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionFrCollection })));
        const expectedCollection: II18n[] = [descriptionFr, ...descriptionFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionFrCollection, descriptionFr);
        expect(comp.descriptionFrsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionEn query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const descriptionEn: II18n = { id: 13727 };
        linkData.descriptionEn = descriptionEn;

        const descriptionEnCollection: II18n[] = [{ id: 88122 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionEnCollection })));
        const expectedCollection: II18n[] = [descriptionEn, ...descriptionEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionEnCollection, descriptionEn);
        expect(comp.descriptionEnsCollection).toEqual(expectedCollection);
      });

      it('Should call codeFr query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const codeFr: II18n = { id: 85833 };
        linkData.codeFr = codeFr;

        const codeFrCollection: II18n[] = [{ id: 89694 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: codeFrCollection })));
        const expectedCollection: II18n[] = [codeFr, ...codeFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(codeFrCollection, codeFr);
        expect(comp.codeFrsCollection).toEqual(expectedCollection);
      });

      it('Should call codeEn query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const codeEn: II18n = { id: 3854 };
        linkData.codeEn = codeEn;

        const codeEnCollection: II18n[] = [{ id: 63150 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: codeEnCollection })));
        const expectedCollection: II18n[] = [codeEn, ...codeEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(codeEnCollection, codeEn);
        expect(comp.codeEnsCollection).toEqual(expectedCollection);
      });

      it('Should call Sub query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const sub: ISub = { id: 15769 };
        linkData.sub = sub;

        const subCollection: ISub[] = [{ id: 857 }];
        spyOn(subService, 'query').and.returnValue(of(new HttpResponse({ body: subCollection })));
        const additionalSubs = [sub];
        const expectedCollection: ISub[] = [...additionalSubs, ...subCollection];
        spyOn(subService, 'addSubToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(subService.query).toHaveBeenCalled();
        expect(subService.addSubToCollectionIfMissing).toHaveBeenCalledWith(subCollection, ...additionalSubs);
        expect(comp.subsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call UnSub query and add missing value', () => {
        const linkData: ILinkData = { id: 456 };
        const unSub: IUnSub = { id: 5369 };
        linkData.unSub = unSub;

        const unSubCollection: IUnSub[] = [{ id: 70646 }];
        spyOn(unSubService, 'query').and.returnValue(of(new HttpResponse({ body: unSubCollection })));
        const additionalUnSubs = [unSub];
        const expectedCollection: IUnSub[] = [...additionalUnSubs, ...unSubCollection];
        spyOn(unSubService, 'addUnSubToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(unSubService.query).toHaveBeenCalled();
        expect(unSubService.addUnSubToCollectionIfMissing).toHaveBeenCalledWith(unSubCollection, ...additionalUnSubs);
        expect(comp.unSubsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const linkData: ILinkData = { id: 456 };
        const titleFr: II18n = { id: 74814 };
        linkData.titleFr = titleFr;
        const titleEn: II18n = { id: 49581 };
        linkData.titleEn = titleEn;
        const descriptionFr: II18n = { id: 9331 };
        linkData.descriptionFr = descriptionFr;
        const descriptionEn: II18n = { id: 13657 };
        linkData.descriptionEn = descriptionEn;
        const codeFr: II18n = { id: 2923 };
        linkData.codeFr = codeFr;
        const codeEn: II18n = { id: 78639 };
        linkData.codeEn = codeEn;
        const sub: ISub = { id: 30484 };
        linkData.sub = sub;
        const unSub: IUnSub = { id: 72385 };
        linkData.unSub = unSub;

        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(linkData));
        expect(comp.titleFrsCollection).toContain(titleFr);
        expect(comp.titleEnsCollection).toContain(titleEn);
        expect(comp.descriptionFrsCollection).toContain(descriptionFr);
        expect(comp.descriptionEnsCollection).toContain(descriptionEn);
        expect(comp.codeFrsCollection).toContain(codeFr);
        expect(comp.codeEnsCollection).toContain(codeEn);
        expect(comp.subsSharedCollection).toContain(sub);
        expect(comp.unSubsSharedCollection).toContain(unSub);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const linkData = { id: 123 };
        spyOn(linkDataService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: linkData }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(linkDataService.update).toHaveBeenCalledWith(linkData);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const linkData = new LinkData();
        spyOn(linkDataService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: linkData }));
        saveSubject.complete();

        // THEN
        expect(linkDataService.create).toHaveBeenCalledWith(linkData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const linkData = { id: 123 };
        spyOn(linkDataService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ linkData });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(linkDataService.update).toHaveBeenCalledWith(linkData);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackI18nById', () => {
        it('Should return tracked I18n primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackI18nById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackSubById', () => {
        it('Should return tracked Sub primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSubById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackUnSubById', () => {
        it('Should return tracked UnSub primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUnSubById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});

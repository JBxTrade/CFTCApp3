jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LinkService } from '../service/link.service';
import { ILink, Link } from '../link.model';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';
import { ISub } from 'app/entities/sub/sub.model';
import { SubService } from 'app/entities/sub/service/sub.service';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { UnSubService } from 'app/entities/un-sub/service/un-sub.service';

import { LinkUpdateComponent } from './link-update.component';

describe('Component Tests', () => {
  describe('Link Management Update Component', () => {
    let comp: LinkUpdateComponent;
    let fixture: ComponentFixture<LinkUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let linkService: LinkService;
    let i18nService: I18nService;
    let subService: SubService;
    let unSubService: UnSubService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LinkUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LinkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LinkUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      linkService = TestBed.inject(LinkService);
      i18nService = TestBed.inject(I18nService);
      subService = TestBed.inject(SubService);
      unSubService = TestBed.inject(UnSubService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call titleFr query and add missing value', () => {
        const link: ILink = { id: 456 };
        const titleFr: II18n = { id: 62681 };
        link.titleFr = titleFr;

        const titleFrCollection: II18n[] = [{ id: 28010 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleFrCollection })));
        const expectedCollection: II18n[] = [titleFr, ...titleFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ link });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleFrCollection, titleFr);
        expect(comp.titleFrsCollection).toEqual(expectedCollection);
      });

      it('Should call titleEn query and add missing value', () => {
        const link: ILink = { id: 456 };
        const titleEn: II18n = { id: 43456 };
        link.titleEn = titleEn;

        const titleEnCollection: II18n[] = [{ id: 83802 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleEnCollection })));
        const expectedCollection: II18n[] = [titleEn, ...titleEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ link });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleEnCollection, titleEn);
        expect(comp.titleEnsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionFr query and add missing value', () => {
        const link: ILink = { id: 456 };
        const descriptionFr: II18n = { id: 33987 };
        link.descriptionFr = descriptionFr;

        const descriptionFrCollection: II18n[] = [{ id: 92470 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionFrCollection })));
        const expectedCollection: II18n[] = [descriptionFr, ...descriptionFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ link });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionFrCollection, descriptionFr);
        expect(comp.descriptionFrsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionEn query and add missing value', () => {
        const link: ILink = { id: 456 };
        const descriptionEn: II18n = { id: 77701 };
        link.descriptionEn = descriptionEn;

        const descriptionEnCollection: II18n[] = [{ id: 29310 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionEnCollection })));
        const expectedCollection: II18n[] = [descriptionEn, ...descriptionEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ link });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionEnCollection, descriptionEn);
        expect(comp.descriptionEnsCollection).toEqual(expectedCollection);
      });

      it('Should call Sub query and add missing value', () => {
        const link: ILink = { id: 456 };
        const sub: ISub = { id: 68015 };
        link.sub = sub;

        const subCollection: ISub[] = [{ id: 33900 }];
        spyOn(subService, 'query').and.returnValue(of(new HttpResponse({ body: subCollection })));
        const additionalSubs = [sub];
        const expectedCollection: ISub[] = [...additionalSubs, ...subCollection];
        spyOn(subService, 'addSubToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ link });
        comp.ngOnInit();

        expect(subService.query).toHaveBeenCalled();
        expect(subService.addSubToCollectionIfMissing).toHaveBeenCalledWith(subCollection, ...additionalSubs);
        expect(comp.subsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call UnSub query and add missing value', () => {
        const link: ILink = { id: 456 };
        const unSub: IUnSub = { id: 44056 };
        link.unSub = unSub;

        const unSubCollection: IUnSub[] = [{ id: 54013 }];
        spyOn(unSubService, 'query').and.returnValue(of(new HttpResponse({ body: unSubCollection })));
        const additionalUnSubs = [unSub];
        const expectedCollection: IUnSub[] = [...additionalUnSubs, ...unSubCollection];
        spyOn(unSubService, 'addUnSubToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ link });
        comp.ngOnInit();

        expect(unSubService.query).toHaveBeenCalled();
        expect(unSubService.addUnSubToCollectionIfMissing).toHaveBeenCalledWith(unSubCollection, ...additionalUnSubs);
        expect(comp.unSubsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const link: ILink = { id: 456 };
        const titleFr: II18n = { id: 36006 };
        link.titleFr = titleFr;
        const titleEn: II18n = { id: 3293 };
        link.titleEn = titleEn;
        const descriptionFr: II18n = { id: 26940 };
        link.descriptionFr = descriptionFr;
        const descriptionEn: II18n = { id: 78953 };
        link.descriptionEn = descriptionEn;
        const sub: ISub = { id: 2882 };
        link.sub = sub;
        const unSub: IUnSub = { id: 60431 };
        link.unSub = unSub;

        activatedRoute.data = of({ link });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(link));
        expect(comp.titleFrsCollection).toContain(titleFr);
        expect(comp.titleEnsCollection).toContain(titleEn);
        expect(comp.descriptionFrsCollection).toContain(descriptionFr);
        expect(comp.descriptionEnsCollection).toContain(descriptionEn);
        expect(comp.subsSharedCollection).toContain(sub);
        expect(comp.unSubsSharedCollection).toContain(unSub);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const link = { id: 123 };
        spyOn(linkService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ link });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: link }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(linkService.update).toHaveBeenCalledWith(link);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const link = new Link();
        spyOn(linkService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ link });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: link }));
        saveSubject.complete();

        // THEN
        expect(linkService.create).toHaveBeenCalledWith(link);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const link = { id: 123 };
        spyOn(linkService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ link });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(linkService.update).toHaveBeenCalledWith(link);
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

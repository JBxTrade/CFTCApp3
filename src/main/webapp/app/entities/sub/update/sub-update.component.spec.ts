jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SubService } from '../service/sub.service';
import { ISub, Sub } from '../sub.model';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';
import { IUnSub } from 'app/entities/un-sub/un-sub.model';
import { UnSubService } from 'app/entities/un-sub/service/un-sub.service';

import { SubUpdateComponent } from './sub-update.component';

describe('Component Tests', () => {
  describe('Sub Management Update Component', () => {
    let comp: SubUpdateComponent;
    let fixture: ComponentFixture<SubUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let subService: SubService;
    let i18nService: I18nService;
    let unSubService: UnSubService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SubUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SubUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      subService = TestBed.inject(SubService);
      i18nService = TestBed.inject(I18nService);
      unSubService = TestBed.inject(UnSubService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call titleFr query and add missing value', () => {
        const sub: ISub = { id: 456 };
        const titleFr: II18n = { id: 71540 };
        sub.titleFr = titleFr;

        const titleFrCollection: II18n[] = [{ id: 97632 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleFrCollection })));
        const expectedCollection: II18n[] = [titleFr, ...titleFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleFrCollection, titleFr);
        expect(comp.titleFrsCollection).toEqual(expectedCollection);
      });

      it('Should call titleEn query and add missing value', () => {
        const sub: ISub = { id: 456 };
        const titleEn: II18n = { id: 22393 };
        sub.titleEn = titleEn;

        const titleEnCollection: II18n[] = [{ id: 12380 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleEnCollection })));
        const expectedCollection: II18n[] = [titleEn, ...titleEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleEnCollection, titleEn);
        expect(comp.titleEnsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionFr query and add missing value', () => {
        const sub: ISub = { id: 456 };
        const descriptionFr: II18n = { id: 52500 };
        sub.descriptionFr = descriptionFr;

        const descriptionFrCollection: II18n[] = [{ id: 6174 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionFrCollection })));
        const expectedCollection: II18n[] = [descriptionFr, ...descriptionFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionFrCollection, descriptionFr);
        expect(comp.descriptionFrsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionEn query and add missing value', () => {
        const sub: ISub = { id: 456 };
        const descriptionEn: II18n = { id: 7041 };
        sub.descriptionEn = descriptionEn;

        const descriptionEnCollection: II18n[] = [{ id: 30525 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionEnCollection })));
        const expectedCollection: II18n[] = [descriptionEn, ...descriptionEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionEnCollection, descriptionEn);
        expect(comp.descriptionEnsCollection).toEqual(expectedCollection);
      });

      it('Should call UnSub query and add missing value', () => {
        const sub: ISub = { id: 456 };
        const unSub: IUnSub = { id: 42136 };
        sub.unSub = unSub;

        const unSubCollection: IUnSub[] = [{ id: 93765 }];
        spyOn(unSubService, 'query').and.returnValue(of(new HttpResponse({ body: unSubCollection })));
        const additionalUnSubs = [unSub];
        const expectedCollection: IUnSub[] = [...additionalUnSubs, ...unSubCollection];
        spyOn(unSubService, 'addUnSubToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        expect(unSubService.query).toHaveBeenCalled();
        expect(unSubService.addUnSubToCollectionIfMissing).toHaveBeenCalledWith(unSubCollection, ...additionalUnSubs);
        expect(comp.unSubsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const sub: ISub = { id: 456 };
        const titleFr: II18n = { id: 39833 };
        sub.titleFr = titleFr;
        const titleEn: II18n = { id: 23480 };
        sub.titleEn = titleEn;
        const descriptionFr: II18n = { id: 24024 };
        sub.descriptionFr = descriptionFr;
        const descriptionEn: II18n = { id: 89886 };
        sub.descriptionEn = descriptionEn;
        const unSub: IUnSub = { id: 92141 };
        sub.unSub = unSub;

        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(sub));
        expect(comp.titleFrsCollection).toContain(titleFr);
        expect(comp.titleEnsCollection).toContain(titleEn);
        expect(comp.descriptionFrsCollection).toContain(descriptionFr);
        expect(comp.descriptionEnsCollection).toContain(descriptionEn);
        expect(comp.unSubsSharedCollection).toContain(unSub);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sub = { id: 123 };
        spyOn(subService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sub }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(subService.update).toHaveBeenCalledWith(sub);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sub = new Sub();
        spyOn(subService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sub }));
        saveSubject.complete();

        // THEN
        expect(subService.create).toHaveBeenCalledWith(sub);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sub = { id: 123 };
        spyOn(subService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sub });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(subService.update).toHaveBeenCalledWith(sub);
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

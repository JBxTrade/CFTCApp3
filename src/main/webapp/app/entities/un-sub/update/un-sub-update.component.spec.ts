jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UnSubService } from '../service/un-sub.service';
import { IUnSub, UnSub } from '../un-sub.model';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';

import { UnSubUpdateComponent } from './un-sub-update.component';

describe('Component Tests', () => {
  describe('UnSub Management Update Component', () => {
    let comp: UnSubUpdateComponent;
    let fixture: ComponentFixture<UnSubUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let unSubService: UnSubService;
    let i18nService: I18nService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UnSubUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(UnSubUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UnSubUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      unSubService = TestBed.inject(UnSubService);
      i18nService = TestBed.inject(I18nService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call titleFr query and add missing value', () => {
        const unSub: IUnSub = { id: 456 };
        const titleFr: II18n = { id: 66757 };
        unSub.titleFr = titleFr;

        const titleFrCollection: II18n[] = [{ id: 2291 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleFrCollection })));
        const expectedCollection: II18n[] = [titleFr, ...titleFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleFrCollection, titleFr);
        expect(comp.titleFrsCollection).toEqual(expectedCollection);
      });

      it('Should call titleEn query and add missing value', () => {
        const unSub: IUnSub = { id: 456 };
        const titleEn: II18n = { id: 1723 };
        unSub.titleEn = titleEn;

        const titleEnCollection: II18n[] = [{ id: 13709 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleEnCollection })));
        const expectedCollection: II18n[] = [titleEn, ...titleEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleEnCollection, titleEn);
        expect(comp.titleEnsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionFr query and add missing value', () => {
        const unSub: IUnSub = { id: 456 };
        const descriptionFr: II18n = { id: 86205 };
        unSub.descriptionFr = descriptionFr;

        const descriptionFrCollection: II18n[] = [{ id: 15128 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionFrCollection })));
        const expectedCollection: II18n[] = [descriptionFr, ...descriptionFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionFrCollection, descriptionFr);
        expect(comp.descriptionFrsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionEn query and add missing value', () => {
        const unSub: IUnSub = { id: 456 };
        const descriptionEn: II18n = { id: 76711 };
        unSub.descriptionEn = descriptionEn;

        const descriptionEnCollection: II18n[] = [{ id: 88205 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionEnCollection })));
        const expectedCollection: II18n[] = [descriptionEn, ...descriptionEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionEnCollection, descriptionEn);
        expect(comp.descriptionEnsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const unSub: IUnSub = { id: 456 };
        const titleFr: II18n = { id: 97749 };
        unSub.titleFr = titleFr;
        const titleEn: II18n = { id: 41087 };
        unSub.titleEn = titleEn;
        const descriptionFr: II18n = { id: 35409 };
        unSub.descriptionFr = descriptionFr;
        const descriptionEn: II18n = { id: 80439 };
        unSub.descriptionEn = descriptionEn;

        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(unSub));
        expect(comp.titleFrsCollection).toContain(titleFr);
        expect(comp.titleEnsCollection).toContain(titleEn);
        expect(comp.descriptionFrsCollection).toContain(descriptionFr);
        expect(comp.descriptionEnsCollection).toContain(descriptionEn);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const unSub = { id: 123 };
        spyOn(unSubService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: unSub }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(unSubService.update).toHaveBeenCalledWith(unSub);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const unSub = new UnSub();
        spyOn(unSubService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: unSub }));
        saveSubject.complete();

        // THEN
        expect(unSubService.create).toHaveBeenCalledWith(unSub);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const unSub = { id: 123 };
        spyOn(unSubService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ unSub });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(unSubService.update).toHaveBeenCalledWith(unSub);
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
    });
  });
});

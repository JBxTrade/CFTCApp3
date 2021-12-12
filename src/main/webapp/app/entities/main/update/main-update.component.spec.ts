jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MainService } from '../service/main.service';
import { IMain, Main } from '../main.model';
import { ISub } from 'app/entities/sub/sub.model';
import { SubService } from 'app/entities/sub/service/sub.service';
import { ILink } from 'app/entities/link/link.model';
import { LinkService } from 'app/entities/link/service/link.service';
import { ILinkData } from 'app/entities/link-data/link-data.model';
import { LinkDataService } from 'app/entities/link-data/service/link-data.service';
import { II18n } from 'app/entities/i-18-n/i-18-n.model';
import { I18nService } from 'app/entities/i-18-n/service/i-18-n.service';

import { MainUpdateComponent } from './main-update.component';

describe('Component Tests', () => {
  describe('Main Management Update Component', () => {
    let comp: MainUpdateComponent;
    let fixture: ComponentFixture<MainUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let mainService: MainService;
    let subService: SubService;
    let linkService: LinkService;
    let linkDataService: LinkDataService;
    let i18nService: I18nService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MainUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MainUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MainUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      mainService = TestBed.inject(MainService);
      subService = TestBed.inject(SubService);
      linkService = TestBed.inject(LinkService);
      linkDataService = TestBed.inject(LinkDataService);
      i18nService = TestBed.inject(I18nService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call subLink query and add missing value', () => {
        const main: IMain = { id: 456 };
        const subLink: ISub = { id: 5162 };
        main.subLink = subLink;

        const subLinkCollection: ISub[] = [{ id: 7667 }];
        spyOn(subService, 'query').and.returnValue(of(new HttpResponse({ body: subLinkCollection })));
        const expectedCollection: ISub[] = [subLink, ...subLinkCollection];
        spyOn(subService, 'addSubToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(subService.query).toHaveBeenCalled();
        expect(subService.addSubToCollectionIfMissing).toHaveBeenCalledWith(subLinkCollection, subLink);
        expect(comp.subLinksCollection).toEqual(expectedCollection);
      });

      it('Should call link query and add missing value', () => {
        const main: IMain = { id: 456 };
        const link: ILink = { id: 96835 };
        main.link = link;

        const linkCollection: ILink[] = [{ id: 16585 }];
        spyOn(linkService, 'query').and.returnValue(of(new HttpResponse({ body: linkCollection })));
        const expectedCollection: ILink[] = [link, ...linkCollection];
        spyOn(linkService, 'addLinkToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(linkService.query).toHaveBeenCalled();
        expect(linkService.addLinkToCollectionIfMissing).toHaveBeenCalledWith(linkCollection, link);
        expect(comp.linksCollection).toEqual(expectedCollection);
      });

      it('Should call linkData query and add missing value', () => {
        const main: IMain = { id: 456 };
        const linkData: ILinkData = { id: 46904 };
        main.linkData = linkData;

        const linkDataCollection: ILinkData[] = [{ id: 17427 }];
        spyOn(linkDataService, 'query').and.returnValue(of(new HttpResponse({ body: linkDataCollection })));
        const expectedCollection: ILinkData[] = [linkData, ...linkDataCollection];
        spyOn(linkDataService, 'addLinkDataToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(linkDataService.query).toHaveBeenCalled();
        expect(linkDataService.addLinkDataToCollectionIfMissing).toHaveBeenCalledWith(linkDataCollection, linkData);
        expect(comp.linkDataCollection).toEqual(expectedCollection);
      });

      it('Should call titleFr query and add missing value', () => {
        const main: IMain = { id: 456 };
        const titleFr: II18n = { id: 8652 };
        main.titleFr = titleFr;

        const titleFrCollection: II18n[] = [{ id: 12472 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleFrCollection })));
        const expectedCollection: II18n[] = [titleFr, ...titleFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleFrCollection, titleFr);
        expect(comp.titleFrsCollection).toEqual(expectedCollection);
      });

      it('Should call titleEn query and add missing value', () => {
        const main: IMain = { id: 456 };
        const titleEn: II18n = { id: 22516 };
        main.titleEn = titleEn;

        const titleEnCollection: II18n[] = [{ id: 80169 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: titleEnCollection })));
        const expectedCollection: II18n[] = [titleEn, ...titleEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(titleEnCollection, titleEn);
        expect(comp.titleEnsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionFr query and add missing value', () => {
        const main: IMain = { id: 456 };
        const descriptionFr: II18n = { id: 20837 };
        main.descriptionFr = descriptionFr;

        const descriptionFrCollection: II18n[] = [{ id: 50682 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionFrCollection })));
        const expectedCollection: II18n[] = [descriptionFr, ...descriptionFrCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionFrCollection, descriptionFr);
        expect(comp.descriptionFrsCollection).toEqual(expectedCollection);
      });

      it('Should call descriptionEn query and add missing value', () => {
        const main: IMain = { id: 456 };
        const descriptionEn: II18n = { id: 15945 };
        main.descriptionEn = descriptionEn;

        const descriptionEnCollection: II18n[] = [{ id: 54533 }];
        spyOn(i18nService, 'query').and.returnValue(of(new HttpResponse({ body: descriptionEnCollection })));
        const expectedCollection: II18n[] = [descriptionEn, ...descriptionEnCollection];
        spyOn(i18nService, 'addI18nToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(i18nService.query).toHaveBeenCalled();
        expect(i18nService.addI18nToCollectionIfMissing).toHaveBeenCalledWith(descriptionEnCollection, descriptionEn);
        expect(comp.descriptionEnsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const main: IMain = { id: 456 };
        const subLink: ISub = { id: 52857 };
        main.subLink = subLink;
        const link: ILink = { id: 14603 };
        main.link = link;
        const linkData: ILinkData = { id: 14180 };
        main.linkData = linkData;
        const titleFr: II18n = { id: 10933 };
        main.titleFr = titleFr;
        const titleEn: II18n = { id: 65924 };
        main.titleEn = titleEn;
        const descriptionFr: II18n = { id: 54974 };
        main.descriptionFr = descriptionFr;
        const descriptionEn: II18n = { id: 5364 };
        main.descriptionEn = descriptionEn;

        activatedRoute.data = of({ main });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(main));
        expect(comp.subLinksCollection).toContain(subLink);
        expect(comp.linksCollection).toContain(link);
        expect(comp.linkDataCollection).toContain(linkData);
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
        const main = { id: 123 };
        spyOn(mainService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ main });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: main }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(mainService.update).toHaveBeenCalledWith(main);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const main = new Main();
        spyOn(mainService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ main });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: main }));
        saveSubject.complete();

        // THEN
        expect(mainService.create).toHaveBeenCalledWith(main);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const main = { id: 123 };
        spyOn(mainService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ main });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(mainService.update).toHaveBeenCalledWith(main);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackSubById', () => {
        it('Should return tracked Sub primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSubById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackLinkById', () => {
        it('Should return tracked Link primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackLinkById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackLinkDataById', () => {
        it('Should return tracked LinkData primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackLinkDataById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

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

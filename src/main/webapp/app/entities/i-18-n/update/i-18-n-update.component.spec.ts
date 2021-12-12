jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { I18nService } from '../service/i-18-n.service';
import { II18n, I18n } from '../i-18-n.model';

import { I18nUpdateComponent } from './i-18-n-update.component';

describe('Component Tests', () => {
  describe('I18n Management Update Component', () => {
    let comp: I18nUpdateComponent;
    let fixture: ComponentFixture<I18nUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let i18nService: I18nService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [I18nUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(I18nUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(I18nUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      i18nService = TestBed.inject(I18nService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const i18n: II18n = { id: 456 };

        activatedRoute.data = of({ i18n });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(i18n));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const i18n = { id: 123 };
        spyOn(i18nService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ i18n });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: i18n }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(i18nService.update).toHaveBeenCalledWith(i18n);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const i18n = new I18n();
        spyOn(i18nService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ i18n });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: i18n }));
        saveSubject.complete();

        // THEN
        expect(i18nService.create).toHaveBeenCalledWith(i18n);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const i18n = { id: 123 };
        spyOn(i18nService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ i18n });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(i18nService.update).toHaveBeenCalledWith(i18n);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});

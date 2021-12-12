import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { I18nDetailComponent } from './i-18-n-detail.component';

describe('Component Tests', () => {
  describe('I18n Management Detail Component', () => {
    let comp: I18nDetailComponent;
    let fixture: ComponentFixture<I18nDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [I18nDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ i18n: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(I18nDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(I18nDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load i18n on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.i18n).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

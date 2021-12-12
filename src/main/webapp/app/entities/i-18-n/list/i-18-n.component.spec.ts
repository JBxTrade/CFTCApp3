import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { I18nService } from '../service/i-18-n.service';

import { I18nComponent } from './i-18-n.component';

describe('Component Tests', () => {
  describe('I18n Management Component', () => {
    let comp: I18nComponent;
    let fixture: ComponentFixture<I18nComponent>;
    let service: I18nService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [I18nComponent],
      })
        .overrideTemplate(I18nComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(I18nComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(I18nService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.i18ns?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

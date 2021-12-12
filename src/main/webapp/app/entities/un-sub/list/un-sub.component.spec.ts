import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { UnSubService } from '../service/un-sub.service';

import { UnSubComponent } from './un-sub.component';

describe('Component Tests', () => {
  describe('UnSub Management Component', () => {
    let comp: UnSubComponent;
    let fixture: ComponentFixture<UnSubComponent>;
    let service: UnSubService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UnSubComponent],
      })
        .overrideTemplate(UnSubComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UnSubComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(UnSubService);

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
      expect(comp.unSubs?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

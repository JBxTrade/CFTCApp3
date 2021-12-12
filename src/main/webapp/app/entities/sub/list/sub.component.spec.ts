import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SubService } from '../service/sub.service';

import { SubComponent } from './sub.component';

describe('Component Tests', () => {
  describe('Sub Management Component', () => {
    let comp: SubComponent;
    let fixture: ComponentFixture<SubComponent>;
    let service: SubService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SubComponent],
      })
        .overrideTemplate(SubComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SubService);

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
      expect(comp.subs?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

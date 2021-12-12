import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MainService } from '../service/main.service';

import { MainComponent } from './main.component';

describe('Component Tests', () => {
  describe('Main Management Component', () => {
    let comp: MainComponent;
    let fixture: ComponentFixture<MainComponent>;
    let service: MainService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MainComponent],
      })
        .overrideTemplate(MainComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MainComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(MainService);

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
      expect(comp.mains?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

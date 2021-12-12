import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataUtils } from 'app/core/util/data-util.service';

import { UnSubDetailComponent } from './un-sub-detail.component';

describe('Component Tests', () => {
  describe('UnSub Management Detail Component', () => {
    let comp: UnSubDetailComponent;
    let fixture: ComponentFixture<UnSubDetailComponent>;
    let dataUtils: DataUtils;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [UnSubDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ unSub: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(UnSubDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnSubDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = TestBed.inject(DataUtils);
    });

    describe('OnInit', () => {
      it('Should load unSub on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unSub).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from DataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from DataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeBase64, fakeContentType);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeBase64, fakeContentType);
      });
    });
  });
});

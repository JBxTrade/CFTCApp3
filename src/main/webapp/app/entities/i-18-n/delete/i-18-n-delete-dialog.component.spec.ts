jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { I18nService } from '../service/i-18-n.service';

import { I18nDeleteDialogComponent } from './i-18-n-delete-dialog.component';

describe('Component Tests', () => {
  describe('I18n Management Delete Component', () => {
    let comp: I18nDeleteDialogComponent;
    let fixture: ComponentFixture<I18nDeleteDialogComponent>;
    let service: I18nService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [I18nDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(I18nDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(I18nDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(I18nService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});

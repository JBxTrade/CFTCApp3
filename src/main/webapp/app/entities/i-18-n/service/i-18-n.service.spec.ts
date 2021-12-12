import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { II18n, I18n } from '../i-18-n.model';

import { I18nService } from './i-18-n.service';

describe('Service Tests', () => {
  describe('I18n Service', () => {
    let service: I18nService;
    let httpMock: HttpTestingController;
    let elemDefault: II18n;
    let expectedResult: II18n | II18n[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(I18nService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        fr: 'AAAAAAA',
        en: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a I18n', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new I18n()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a I18n', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            fr: 'BBBBBB',
            en: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a I18n', () => {
        const patchObject = Object.assign(
          {
            fr: 'BBBBBB',
            en: 'BBBBBB',
          },
          new I18n()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of I18n', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            fr: 'BBBBBB',
            en: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a I18n', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addI18nToCollectionIfMissing', () => {
        it('should add a I18n to an empty array', () => {
          const i18n: II18n = { id: 123 };
          expectedResult = service.addI18nToCollectionIfMissing([], i18n);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(i18n);
        });

        it('should not add a I18n to an array that contains it', () => {
          const i18n: II18n = { id: 123 };
          const i18nCollection: II18n[] = [
            {
              ...i18n,
            },
            { id: 456 },
          ];
          expectedResult = service.addI18nToCollectionIfMissing(i18nCollection, i18n);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a I18n to an array that doesn't contain it", () => {
          const i18n: II18n = { id: 123 };
          const i18nCollection: II18n[] = [{ id: 456 }];
          expectedResult = service.addI18nToCollectionIfMissing(i18nCollection, i18n);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(i18n);
        });

        it('should add only unique I18n to an array', () => {
          const i18nArray: II18n[] = [{ id: 123 }, { id: 456 }, { id: 17720 }];
          const i18nCollection: II18n[] = [{ id: 123 }];
          expectedResult = service.addI18nToCollectionIfMissing(i18nCollection, ...i18nArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const i18n: II18n = { id: 123 };
          const i18n2: II18n = { id: 456 };
          expectedResult = service.addI18nToCollectionIfMissing([], i18n, i18n2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(i18n);
          expect(expectedResult).toContain(i18n2);
        });

        it('should accept null and undefined values', () => {
          const i18n: II18n = { id: 123 };
          expectedResult = service.addI18nToCollectionIfMissing([], null, i18n, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(i18n);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

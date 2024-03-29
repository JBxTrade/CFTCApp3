import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TheRole } from 'app/entities/enumerations/the-role.model';
import { IUnSub, UnSub } from '../un-sub.model';

import { UnSubService } from './un-sub.service';

describe('Service Tests', () => {
  describe('UnSub Service', () => {
    let service: UnSubService;
    let httpMock: HttpTestingController;
    let elemDefault: IUnSub;
    let expectedResult: IUnSub | IUnSub[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(UnSubService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        theRole: TheRole.ADMIN,
        imageContentType: 'image/png',
        image: 'AAAAAAA',
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

      it('should create a UnSub', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UnSub()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UnSub', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            theRole: 'BBBBBB',
            image: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a UnSub', () => {
        const patchObject = Object.assign(
          {
            image: 'BBBBBB',
          },
          new UnSub()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UnSub', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            theRole: 'BBBBBB',
            image: 'BBBBBB',
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

      it('should delete a UnSub', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addUnSubToCollectionIfMissing', () => {
        it('should add a UnSub to an empty array', () => {
          const unSub: IUnSub = { id: 123 };
          expectedResult = service.addUnSubToCollectionIfMissing([], unSub);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(unSub);
        });

        it('should not add a UnSub to an array that contains it', () => {
          const unSub: IUnSub = { id: 123 };
          const unSubCollection: IUnSub[] = [
            {
              ...unSub,
            },
            { id: 456 },
          ];
          expectedResult = service.addUnSubToCollectionIfMissing(unSubCollection, unSub);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a UnSub to an array that doesn't contain it", () => {
          const unSub: IUnSub = { id: 123 };
          const unSubCollection: IUnSub[] = [{ id: 456 }];
          expectedResult = service.addUnSubToCollectionIfMissing(unSubCollection, unSub);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(unSub);
        });

        it('should add only unique UnSub to an array', () => {
          const unSubArray: IUnSub[] = [{ id: 123 }, { id: 456 }, { id: 39644 }];
          const unSubCollection: IUnSub[] = [{ id: 123 }];
          expectedResult = service.addUnSubToCollectionIfMissing(unSubCollection, ...unSubArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const unSub: IUnSub = { id: 123 };
          const unSub2: IUnSub = { id: 456 };
          expectedResult = service.addUnSubToCollectionIfMissing([], unSub, unSub2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(unSub);
          expect(expectedResult).toContain(unSub2);
        });

        it('should accept null and undefined values', () => {
          const unSub: IUnSub = { id: 123 };
          expectedResult = service.addUnSubToCollectionIfMissing([], null, unSub, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(unSub);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

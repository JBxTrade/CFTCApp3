import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TheRole } from 'app/entities/enumerations/the-role.model';
import { ISub, Sub } from '../sub.model';

import { SubService } from './sub.service';

describe('Service Tests', () => {
  describe('Sub Service', () => {
    let service: SubService;
    let httpMock: HttpTestingController;
    let elemDefault: ISub;
    let expectedResult: ISub | ISub[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SubService);
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

      it('should create a Sub', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Sub()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Sub', () => {
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

      it('should partial update a Sub', () => {
        const patchObject = Object.assign(
          {
            theRole: 'BBBBBB',
          },
          new Sub()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Sub', () => {
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

      it('should delete a Sub', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSubToCollectionIfMissing', () => {
        it('should add a Sub to an empty array', () => {
          const sub: ISub = { id: 123 };
          expectedResult = service.addSubToCollectionIfMissing([], sub);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sub);
        });

        it('should not add a Sub to an array that contains it', () => {
          const sub: ISub = { id: 123 };
          const subCollection: ISub[] = [
            {
              ...sub,
            },
            { id: 456 },
          ];
          expectedResult = service.addSubToCollectionIfMissing(subCollection, sub);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Sub to an array that doesn't contain it", () => {
          const sub: ISub = { id: 123 };
          const subCollection: ISub[] = [{ id: 456 }];
          expectedResult = service.addSubToCollectionIfMissing(subCollection, sub);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sub);
        });

        it('should add only unique Sub to an array', () => {
          const subArray: ISub[] = [{ id: 123 }, { id: 456 }, { id: 64746 }];
          const subCollection: ISub[] = [{ id: 123 }];
          expectedResult = service.addSubToCollectionIfMissing(subCollection, ...subArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const sub: ISub = { id: 123 };
          const sub2: ISub = { id: 456 };
          expectedResult = service.addSubToCollectionIfMissing([], sub, sub2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sub);
          expect(expectedResult).toContain(sub2);
        });

        it('should accept null and undefined values', () => {
          const sub: ISub = { id: 123 };
          expectedResult = service.addSubToCollectionIfMissing([], null, sub, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sub);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

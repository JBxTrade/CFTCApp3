import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TheRole } from 'app/entities/enumerations/the-role.model';
import { IMain, Main } from '../main.model';

import { MainService } from './main.service';

describe('Service Tests', () => {
  describe('Main Service', () => {
    let service: MainService;
    let httpMock: HttpTestingController;
    let elemDefault: IMain;
    let expectedResult: IMain | IMain[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MainService);
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

      it('should create a Main', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Main()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Main', () => {
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

      it('should partial update a Main', () => {
        const patchObject = Object.assign({}, new Main());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Main', () => {
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

      it('should delete a Main', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMainToCollectionIfMissing', () => {
        it('should add a Main to an empty array', () => {
          const main: IMain = { id: 123 };
          expectedResult = service.addMainToCollectionIfMissing([], main);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(main);
        });

        it('should not add a Main to an array that contains it', () => {
          const main: IMain = { id: 123 };
          const mainCollection: IMain[] = [
            {
              ...main,
            },
            { id: 456 },
          ];
          expectedResult = service.addMainToCollectionIfMissing(mainCollection, main);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Main to an array that doesn't contain it", () => {
          const main: IMain = { id: 123 };
          const mainCollection: IMain[] = [{ id: 456 }];
          expectedResult = service.addMainToCollectionIfMissing(mainCollection, main);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(main);
        });

        it('should add only unique Main to an array', () => {
          const mainArray: IMain[] = [{ id: 123 }, { id: 456 }, { id: 52121 }];
          const mainCollection: IMain[] = [{ id: 123 }];
          expectedResult = service.addMainToCollectionIfMissing(mainCollection, ...mainArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const main: IMain = { id: 123 };
          const main2: IMain = { id: 456 };
          expectedResult = service.addMainToCollectionIfMissing([], main, main2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(main);
          expect(expectedResult).toContain(main2);
        });

        it('should accept null and undefined values', () => {
          const main: IMain = { id: 123 };
          expectedResult = service.addMainToCollectionIfMissing([], null, main, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(main);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

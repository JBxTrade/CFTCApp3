import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TheRole } from 'app/entities/enumerations/the-role.model';
import { ILinkData, LinkData } from '../link-data.model';

import { LinkDataService } from './link-data.service';

describe('Service Tests', () => {
  describe('LinkData Service', () => {
    let service: LinkDataService;
    let httpMock: HttpTestingController;
    let elemDefault: ILinkData;
    let expectedResult: ILinkData | ILinkData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(LinkDataService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        theRole: TheRole.ADMIN,
        imageCardContentType: 'image/png',
        imageCard: 'AAAAAAA',
        imageContentType: 'image/png',
        image: 'AAAAAAA',
        image2ContentType: 'image/png',
        image2: 'AAAAAAA',
        image3ContentType: 'image/png',
        image3: 'AAAAAAA',
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

      it('should create a LinkData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new LinkData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LinkData', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            theRole: 'BBBBBB',
            imageCard: 'BBBBBB',
            image: 'BBBBBB',
            image2: 'BBBBBB',
            image3: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a LinkData', () => {
        const patchObject = Object.assign(
          {
            image: 'BBBBBB',
            image2: 'BBBBBB',
          },
          new LinkData()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LinkData', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            theRole: 'BBBBBB',
            imageCard: 'BBBBBB',
            image: 'BBBBBB',
            image2: 'BBBBBB',
            image3: 'BBBBBB',
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

      it('should delete a LinkData', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addLinkDataToCollectionIfMissing', () => {
        it('should add a LinkData to an empty array', () => {
          const linkData: ILinkData = { id: 123 };
          expectedResult = service.addLinkDataToCollectionIfMissing([], linkData);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(linkData);
        });

        it('should not add a LinkData to an array that contains it', () => {
          const linkData: ILinkData = { id: 123 };
          const linkDataCollection: ILinkData[] = [
            {
              ...linkData,
            },
            { id: 456 },
          ];
          expectedResult = service.addLinkDataToCollectionIfMissing(linkDataCollection, linkData);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a LinkData to an array that doesn't contain it", () => {
          const linkData: ILinkData = { id: 123 };
          const linkDataCollection: ILinkData[] = [{ id: 456 }];
          expectedResult = service.addLinkDataToCollectionIfMissing(linkDataCollection, linkData);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(linkData);
        });

        it('should add only unique LinkData to an array', () => {
          const linkDataArray: ILinkData[] = [{ id: 123 }, { id: 456 }, { id: 600 }];
          const linkDataCollection: ILinkData[] = [{ id: 123 }];
          expectedResult = service.addLinkDataToCollectionIfMissing(linkDataCollection, ...linkDataArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const linkData: ILinkData = { id: 123 };
          const linkData2: ILinkData = { id: 456 };
          expectedResult = service.addLinkDataToCollectionIfMissing([], linkData, linkData2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(linkData);
          expect(expectedResult).toContain(linkData2);
        });

        it('should accept null and undefined values', () => {
          const linkData: ILinkData = { id: 123 };
          expectedResult = service.addLinkDataToCollectionIfMissing([], null, linkData, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(linkData);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

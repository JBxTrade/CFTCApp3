jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILinkData, LinkData } from '../link-data.model';
import { LinkDataService } from '../service/link-data.service';

import { LinkDataRoutingResolveService } from './link-data-routing-resolve.service';

describe('Service Tests', () => {
  describe('LinkData routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: LinkDataRoutingResolveService;
    let service: LinkDataService;
    let resultLinkData: ILinkData | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(LinkDataRoutingResolveService);
      service = TestBed.inject(LinkDataService);
      resultLinkData = undefined;
    });

    describe('resolve', () => {
      it('should return ILinkData returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLinkData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLinkData).toEqual({ id: 123 });
      });

      it('should return new ILinkData if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLinkData = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultLinkData).toEqual(new LinkData());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLinkData = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLinkData).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

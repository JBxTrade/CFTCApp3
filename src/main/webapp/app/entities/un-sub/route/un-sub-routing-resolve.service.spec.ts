jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IUnSub, UnSub } from '../un-sub.model';
import { UnSubService } from '../service/un-sub.service';

import { UnSubRoutingResolveService } from './un-sub-routing-resolve.service';

describe('Service Tests', () => {
  describe('UnSub routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: UnSubRoutingResolveService;
    let service: UnSubService;
    let resultUnSub: IUnSub | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(UnSubRoutingResolveService);
      service = TestBed.inject(UnSubService);
      resultUnSub = undefined;
    });

    describe('resolve', () => {
      it('should return IUnSub returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUnSub = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUnSub).toEqual({ id: 123 });
      });

      it('should return new IUnSub if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUnSub = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultUnSub).toEqual(new UnSub());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUnSub = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUnSub).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

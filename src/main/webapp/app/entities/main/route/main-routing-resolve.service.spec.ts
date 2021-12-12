jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMain, Main } from '../main.model';
import { MainService } from '../service/main.service';

import { MainRoutingResolveService } from './main-routing-resolve.service';

describe('Service Tests', () => {
  describe('Main routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MainRoutingResolveService;
    let service: MainService;
    let resultMain: IMain | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MainRoutingResolveService);
      service = TestBed.inject(MainService);
      resultMain = undefined;
    });

    describe('resolve', () => {
      it('should return IMain returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMain = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMain).toEqual({ id: 123 });
      });

      it('should return new IMain if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMain = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMain).toEqual(new Main());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMain = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMain).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

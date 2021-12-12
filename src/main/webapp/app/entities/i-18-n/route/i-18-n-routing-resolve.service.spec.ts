jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { II18n, I18n } from '../i-18-n.model';
import { I18nService } from '../service/i-18-n.service';

import { I18nRoutingResolveService } from './i-18-n-routing-resolve.service';

describe('Service Tests', () => {
  describe('I18n routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: I18nRoutingResolveService;
    let service: I18nService;
    let resultI18n: II18n | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(I18nRoutingResolveService);
      service = TestBed.inject(I18nService);
      resultI18n = undefined;
    });

    describe('resolve', () => {
      it('should return II18n returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultI18n = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultI18n).toEqual({ id: 123 });
      });

      it('should return new II18n if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultI18n = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultI18n).toEqual(new I18n());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultI18n = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultI18n).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

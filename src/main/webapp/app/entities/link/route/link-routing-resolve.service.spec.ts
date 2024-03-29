jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILink, Link } from '../link.model';
import { LinkService } from '../service/link.service';

import { LinkRoutingResolveService } from './link-routing-resolve.service';

describe('Service Tests', () => {
  describe('Link routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: LinkRoutingResolveService;
    let service: LinkService;
    let resultLink: ILink | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(LinkRoutingResolveService);
      service = TestBed.inject(LinkService);
      resultLink = undefined;
    });

    describe('resolve', () => {
      it('should return ILink returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLink = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLink).toEqual({ id: 123 });
      });

      it('should return new ILink if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLink = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultLink).toEqual(new Link());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLink = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLink).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

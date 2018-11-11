import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';

import { Principal, User, UserService } from 'app/core';
import { FlightMgmtComponent } from './flight-management.component';
import { FlightMgmtDetailComponent } from './flight-management-detail.component';
import { FlightMgmtUpdateComponent } from './flight-management-update.component';

@Injectable({ providedIn: 'root' })
export class FlightResolve implements CanActivate {
    constructor(private principal: Principal) {}

    canActivate() {
        return this.principal.identity().then(account => this.principal.hasAnyAuthority(['ROLE_ADMIN']));
    }
}

@Injectable({ providedIn: 'root' })
export class FlightMgmtResolve implements Resolve<any> {
    constructor(private service: UserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['login'] ? route.params['login'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new User();
    }
}

export const userMgmtRoute: Routes = [
    {
        path: 'flight-management',
        component: FlightMgmtComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            pageTitle: 'flightManagement.home.title',
            defaultSort: 'id,asc'
        }
    },
    {
        path: 'flight-management/:login/view',
        component: FlightMgmtDetailComponent,
        resolve: {
            user: FlightMgmtResolve
        },
        data: {
            pageTitle: 'userManagement.home.title'
        }
    },
    {
        path: 'flight-management/new',
        component: FlightMgmtUpdateComponent,
        resolve: {
            user: FlightMgmtResolve
        }
    },
    {
        path: 'flight-management/:login/edit',
        component: FlightMgmtUpdateComponent,
        resolve: {
            user: FlightMgmtResolve
        }
    }
];

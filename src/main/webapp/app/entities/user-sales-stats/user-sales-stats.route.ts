import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserSalesStats } from 'app/shared/model/user-sales-stats.model';
import { UserSalesStatsService } from './user-sales-stats.service';
import { UserSalesStatsComponent } from './user-sales-stats.component';
import { UserSalesStatsDetailComponent } from './user-sales-stats-detail.component';
import { UserSalesStatsUpdateComponent } from './user-sales-stats-update.component';
import { UserSalesStatsDeletePopupComponent } from './user-sales-stats-delete-dialog.component';
import { IUserSalesStats } from 'app/shared/model/user-sales-stats.model';

@Injectable({ providedIn: 'root' })
export class UserSalesStatsResolve implements Resolve<IUserSalesStats> {
    constructor(private service: UserSalesStatsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserSalesStats> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserSalesStats>) => response.ok),
                map((userSalesStats: HttpResponse<UserSalesStats>) => userSalesStats.body)
            );
        }
        return of(new UserSalesStats());
    }
}

export const userSalesStatsRoute: Routes = [
    {
        path: 'user-sales-stats',
        component: UserSalesStatsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'exerciseApp.userSalesStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-sales-stats/:id/view',
        component: UserSalesStatsDetailComponent,
        resolve: {
            userSalesStats: UserSalesStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'exerciseApp.userSalesStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-sales-stats/new',
        component: UserSalesStatsUpdateComponent,
        resolve: {
            userSalesStats: UserSalesStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'exerciseApp.userSalesStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-sales-stats/:id/edit',
        component: UserSalesStatsUpdateComponent,
        resolve: {
            userSalesStats: UserSalesStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'exerciseApp.userSalesStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userSalesStatsPopupRoute: Routes = [
    {
        path: 'user-sales-stats/:id/delete',
        component: UserSalesStatsDeletePopupComponent,
        resolve: {
            userSalesStats: UserSalesStatsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'exerciseApp.userSalesStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

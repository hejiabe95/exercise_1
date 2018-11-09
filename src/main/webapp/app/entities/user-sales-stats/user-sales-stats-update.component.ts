import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUserSalesStats } from 'app/shared/model/user-sales-stats.model';
import { UserSalesStatsService } from './user-sales-stats.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-user-sales-stats-update',
    templateUrl: './user-sales-stats-update.component.html'
})
export class UserSalesStatsUpdateComponent implements OnInit {
    userSalesStats: IUserSalesStats;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private userSalesStatsService: UserSalesStatsService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userSalesStats }) => {
            this.userSalesStats = userSalesStats;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userSalesStats.id !== undefined) {
            this.subscribeToSaveResponse(this.userSalesStatsService.update(this.userSalesStats));
        } else {
            this.subscribeToSaveResponse(this.userSalesStatsService.create(this.userSalesStats));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserSalesStats>>) {
        result.subscribe((res: HttpResponse<IUserSalesStats>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}

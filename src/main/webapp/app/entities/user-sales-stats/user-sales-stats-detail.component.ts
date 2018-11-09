import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserSalesStats } from 'app/shared/model/user-sales-stats.model';

@Component({
    selector: 'jhi-user-sales-stats-detail',
    templateUrl: './user-sales-stats-detail.component.html'
})
export class UserSalesStatsDetailComponent implements OnInit {
    userSalesStats: IUserSalesStats;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userSalesStats }) => {
            this.userSalesStats = userSalesStats;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserSalesStats } from 'app/shared/model/user-sales-stats.model';
import { UserSalesStatsService } from './user-sales-stats.service';

@Component({
    selector: 'jhi-user-sales-stats-delete-dialog',
    templateUrl: './user-sales-stats-delete-dialog.component.html'
})
export class UserSalesStatsDeleteDialogComponent {
    userSalesStats: IUserSalesStats;

    constructor(
        private userSalesStatsService: UserSalesStatsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userSalesStatsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userSalesStatsListModification',
                content: 'Deleted an userSalesStats'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-sales-stats-delete-popup',
    template: ''
})
export class UserSalesStatsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userSalesStats }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserSalesStatsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userSalesStats = userSalesStats;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

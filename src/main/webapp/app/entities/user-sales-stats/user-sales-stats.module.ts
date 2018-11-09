import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ExerciseSharedModule } from 'app/shared';
import { ExerciseAdminModule } from 'app/admin/admin.module';
import {
    UserSalesStatsComponent,
    UserSalesStatsDetailComponent,
    UserSalesStatsUpdateComponent,
    UserSalesStatsDeletePopupComponent,
    UserSalesStatsDeleteDialogComponent,
    userSalesStatsRoute,
    userSalesStatsPopupRoute
} from './';

const ENTITY_STATES = [...userSalesStatsRoute, ...userSalesStatsPopupRoute];

@NgModule({
    imports: [ExerciseSharedModule, ExerciseAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserSalesStatsComponent,
        UserSalesStatsDetailComponent,
        UserSalesStatsUpdateComponent,
        UserSalesStatsDeleteDialogComponent,
        UserSalesStatsDeletePopupComponent
    ],
    entryComponents: [
        UserSalesStatsComponent,
        UserSalesStatsUpdateComponent,
        UserSalesStatsDeleteDialogComponent,
        UserSalesStatsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExerciseUserSalesStatsModule {}

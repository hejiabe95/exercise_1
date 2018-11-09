import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ExerciseFlightModule } from './flight/flight.module';
import { ExerciseTicketModule } from './ticket/ticket.module';
import { ExerciseUserSalesStatsModule } from './user-sales-stats/user-sales-stats.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ExerciseFlightModule,
        ExerciseTicketModule,
        ExerciseUserSalesStatsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExerciseEntityModule {}

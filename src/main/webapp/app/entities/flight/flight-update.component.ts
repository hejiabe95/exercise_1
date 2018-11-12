import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFlight } from 'app/shared/model/flight.model';
import { FlightService } from './flight.service';

@Component({
    selector: 'jhi-flight-update',
    templateUrl: './flight-update.component.html'
})
export class FlightUpdateComponent implements OnInit {
    flight: IFlight;
    isSaving: boolean;
    departureDate: string;

    constructor(private flightService: FlightService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ flight }) => {
            this.flight = flight;
            this.flight.departureDate = moment();
            this.departureDate = this.flight.departureDate != null ? this.flight.departureDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.flight.departureDate = this.departureDate != null ? moment(this.departureDate, DATE_TIME_FORMAT) : null;
        if (this.flight.id !== undefined) {
            this.subscribeToSaveResponse(this.flightService.update(this.flight));
        } else {
            this.subscribeToSaveResponse(this.flightService.create(this.flight));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFlight>>) {
        result.subscribe((res: HttpResponse<IFlight>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

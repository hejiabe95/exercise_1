import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ITicket } from 'app/shared/model/ticket.model';
import { TicketService } from './ticket.service';
import { IFlight } from 'app/shared/model/flight.model';
import { FlightService } from 'app/entities/flight';
import { Account, IUser, Principal, UserService } from 'app/core';

@Component({
    selector: 'jhi-ticket-update',
    templateUrl: './ticket-update.component.html'
})
export class TicketUpdateComponent implements OnInit {
    ticket: ITicket;
    isSaving: boolean;

    flights: IFlight[];

    users: IUser[];
    orderDate: string;
    account: Account;

    constructor(
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private ticketService: TicketService,
        private flightService: FlightService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.principal.identity().then(account => {
            this.account = account;
            this.ticket.user = account;
        });
        this.activatedRoute.data.subscribe(({ ticket }) => {
            this.ticket = ticket;
            this.ticket.orderDate = moment();
            this.orderDate = this.ticket.orderDate != null ? this.ticket.orderDate.format(DATE_TIME_FORMAT) : null;
        });
        this.flightService.query().subscribe(
            (res: HttpResponse<IFlight[]>) => {
                this.flights = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.ticket.orderDate = this.orderDate != null ? moment(this.orderDate, DATE_TIME_FORMAT) : null;
        if (this.ticket.id !== undefined) {
            this.subscribeToSaveResponse(this.ticketService.update(this.ticket));
        } else {
            this.subscribeToSaveResponse(this.ticketService.create(this.ticket));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITicket>>) {
        result.subscribe((res: HttpResponse<ITicket>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFlightById(index: number, item: IFlight) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}

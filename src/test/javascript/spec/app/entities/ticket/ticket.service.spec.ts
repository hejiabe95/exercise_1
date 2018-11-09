/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TicketService } from 'app/entities/ticket/ticket.service';
import { ITicket, Ticket, SeatLevel } from 'app/shared/model/ticket.model';

describe('Service Tests', () => {
    describe('Ticket Service', () => {
        let injector: TestBed;
        let service: TicketService;
        let httpMock: HttpTestingController;
        let elemDefault: ITicket;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TicketService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Ticket(0, 'AAAAAAA', currentDate, SeatLevel.FCS, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        orderDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Ticket', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        orderDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        orderDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Ticket(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Ticket', async () => {
                const returnedFromService = Object.assign(
                    {
                        passengerName: 'BBBBBB',
                        orderDate: currentDate.format(DATE_TIME_FORMAT),
                        seatLevel: 'BBBBBB',
                        ticketPrice: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        orderDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Ticket', async () => {
                const returnedFromService = Object.assign(
                    {
                        passengerName: 'BBBBBB',
                        orderDate: currentDate.format(DATE_TIME_FORMAT),
                        seatLevel: 'BBBBBB',
                        ticketPrice: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        orderDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Ticket', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});

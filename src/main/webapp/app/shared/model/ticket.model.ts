import { Moment } from 'moment';
import { IFlight } from 'app/shared/model//flight.model';
import { IUser } from 'app/core/user/user.model';

export const enum SeatLevel {
    FCS = 'FCS',
    ECS = 'ECS'
}

export interface ITicket {
    id?: number;
    passengerName?: string;
    orderDate?: Moment;
    seatLevel?: SeatLevel;
    ticketPrice?: number;
    flight?: IFlight;
    user?: IUser;
}

export class Ticket implements ITicket {
    constructor(
        public id?: number,
        public passengerName?: string,
        public orderDate?: Moment,
        public seatLevel?: SeatLevel,
        public ticketPrice?: number,
        public flight?: IFlight,
        public user?: IUser
    ) {}
}

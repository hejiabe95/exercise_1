import { Moment } from 'moment';

export interface IFlight {
    id?: number;
    flightCode?: string;
    departureDate?: Moment;
    fcsRemain?: number;
    ecsRemain?: number;
}

export class Flight implements IFlight {
    constructor(
        public id?: number,
        public flightCode?: string,
        public departureDate?: Moment,
        public fcsRemain?: number,
        public ecsRemain?: number
    ) {}
}

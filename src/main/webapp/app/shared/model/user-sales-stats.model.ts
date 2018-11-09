import { IUser } from 'app/core/user/user.model';

export interface IUserSalesStats {
    id?: number;
    salesAmout?: number;
    ticketAmout?: number;
    fcsAmout?: number;
    ecsAmout?: number;
    user?: IUser;
}

export class UserSalesStats implements IUserSalesStats {
    constructor(
        public id?: number,
        public salesAmout?: number,
        public ticketAmout?: number,
        public fcsAmout?: number,
        public ecsAmout?: number,
        public user?: IUser
    ) {}
}

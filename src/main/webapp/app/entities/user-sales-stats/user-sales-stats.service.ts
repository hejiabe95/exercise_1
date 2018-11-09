import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserSalesStats } from 'app/shared/model/user-sales-stats.model';

type EntityResponseType = HttpResponse<IUserSalesStats>;
type EntityArrayResponseType = HttpResponse<IUserSalesStats[]>;

@Injectable({ providedIn: 'root' })
export class UserSalesStatsService {
    public resourceUrl = SERVER_API_URL + 'api/user-sales-stats';

    constructor(private http: HttpClient) {}

    create(userSalesStats: IUserSalesStats): Observable<EntityResponseType> {
        return this.http.post<IUserSalesStats>(this.resourceUrl, userSalesStats, { observe: 'response' });
    }

    update(userSalesStats: IUserSalesStats): Observable<EntityResponseType> {
        return this.http.put<IUserSalesStats>(this.resourceUrl, userSalesStats, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserSalesStats>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserSalesStats[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

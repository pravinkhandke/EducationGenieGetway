import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserInfo } from 'app/shared/model/user-info.model';

type EntityResponseType = HttpResponse<IUserInfo>;
type EntityArrayResponseType = HttpResponse<IUserInfo[]>;

@Injectable()
export class UserInfoService {
    private resourceUrl = SERVER_API_URL + 'api/user-infos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/user-infos';

    constructor(private http: HttpClient) {}

    create(userInfo: IUserInfo): Observable<EntityResponseType> {
        return this.http.post<IUserInfo>(this.resourceUrl, userInfo, { observe: 'response' });
    }

    update(userInfo: IUserInfo): Observable<EntityResponseType> {
        return this.http.put<IUserInfo>(this.resourceUrl, userInfo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScore } from 'app/shared/model/score.model';

type EntityResponseType = HttpResponse<IScore>;
type EntityArrayResponseType = HttpResponse<IScore[]>;

@Injectable()
export class ScoreService {
    private resourceUrl = SERVER_API_URL + 'api/scores';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/scores';

    constructor(private http: HttpClient) {}

    create(score: IScore): Observable<EntityResponseType> {
        return this.http.post<IScore>(this.resourceUrl, score, { observe: 'response' });
    }

    update(score: IScore): Observable<EntityResponseType> {
        return this.http.put<IScore>(this.resourceUrl, score, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IScore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IScore[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IScore[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

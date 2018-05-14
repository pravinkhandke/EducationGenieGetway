import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContent } from 'app/shared/model/content.model';

type EntityResponseType = HttpResponse<IContent>;
type EntityArrayResponseType = HttpResponse<IContent[]>;

@Injectable()
export class ContentService {
    private resourceUrl = SERVER_API_URL + 'api/contents';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/contents';

    constructor(private http: HttpClient) {}

    create(content: IContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(content);
        return this.http
            .post<IContent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(content: IContent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(content);
        return this.http
            .put<IContent>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContent[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(content: IContent): IContent {
        const copy: IContent = Object.assign({}, content, {
            createdTime: content.createdTime != null && content.createdTime.isValid() ? content.createdTime.toJSON() : null,
            updatedTime: content.updatedTime != null && content.updatedTime.isValid() ? content.updatedTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
        res.body.updatedTime = res.body.updatedTime != null ? moment(res.body.updatedTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((content: IContent) => {
            content.createdTime = content.createdTime != null ? moment(content.createdTime) : null;
            content.updatedTime = content.updatedTime != null ? moment(content.updatedTime) : null;
        });
        return res;
    }
}

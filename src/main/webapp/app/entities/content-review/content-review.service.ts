import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContentReview } from 'app/shared/model/content-review.model';

type EntityResponseType = HttpResponse<IContentReview>;
type EntityArrayResponseType = HttpResponse<IContentReview[]>;

@Injectable()
export class ContentReviewService {
    private resourceUrl = SERVER_API_URL + 'api/content-reviews';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/content-reviews';

    constructor(private http: HttpClient) {}

    create(contentReview: IContentReview): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contentReview);
        return this.http
            .post<IContentReview>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(contentReview: IContentReview): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contentReview);
        return this.http
            .put<IContentReview>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContentReview>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContentReview[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContentReview[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(contentReview: IContentReview): IContentReview {
        const copy: IContentReview = Object.assign({}, contentReview, {
            createdTime:
                contentReview.createdTime != null && contentReview.createdTime.isValid() ? contentReview.createdTime.toJSON() : null,
            updatedTime:
                contentReview.updatedTime != null && contentReview.updatedTime.isValid() ? contentReview.updatedTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
        res.body.updatedTime = res.body.updatedTime != null ? moment(res.body.updatedTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((contentReview: IContentReview) => {
            contentReview.createdTime = contentReview.createdTime != null ? moment(contentReview.createdTime) : null;
            contentReview.updatedTime = contentReview.updatedTime != null ? moment(contentReview.updatedTime) : null;
        });
        return res;
    }
}

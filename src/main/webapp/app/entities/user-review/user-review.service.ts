import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserReview } from 'app/shared/model/user-review.model';

type EntityResponseType = HttpResponse<IUserReview>;
type EntityArrayResponseType = HttpResponse<IUserReview[]>;

@Injectable()
export class UserReviewService {
    private resourceUrl = SERVER_API_URL + 'api/user-reviews';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/user-reviews';

    constructor(private http: HttpClient) {}

    create(userReview: IUserReview): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userReview);
        return this.http
            .post<IUserReview>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(userReview: IUserReview): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(userReview);
        return this.http
            .put<IUserReview>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IUserReview>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserReview[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IUserReview[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(userReview: IUserReview): IUserReview {
        const copy: IUserReview = Object.assign({}, userReview, {
            createdTime: userReview.createdTime != null && userReview.createdTime.isValid() ? userReview.createdTime.toJSON() : null,
            updatedTime: userReview.updatedTime != null && userReview.updatedTime.isValid() ? userReview.updatedTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
        res.body.updatedTime = res.body.updatedTime != null ? moment(res.body.updatedTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((userReview: IUserReview) => {
            userReview.createdTime = userReview.createdTime != null ? moment(userReview.createdTime) : null;
            userReview.updatedTime = userReview.updatedTime != null ? moment(userReview.updatedTime) : null;
        });
        return res;
    }
}

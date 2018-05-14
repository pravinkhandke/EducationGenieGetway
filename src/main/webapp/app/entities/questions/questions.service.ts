import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestions } from 'app/shared/model/questions.model';

type EntityResponseType = HttpResponse<IQuestions>;
type EntityArrayResponseType = HttpResponse<IQuestions[]>;

@Injectable()
export class QuestionsService {
    private resourceUrl = SERVER_API_URL + 'api/questions';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/questions';

    constructor(private http: HttpClient) {}

    create(questions: IQuestions): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questions);
        return this.http
            .post<IQuestions>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(questions: IQuestions): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questions);
        return this.http
            .put<IQuestions>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuestions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestions[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestions[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(questions: IQuestions): IQuestions {
        const copy: IQuestions = Object.assign({}, questions, {
            createdTime: questions.createdTime != null && questions.createdTime.isValid() ? questions.createdTime.toJSON() : null,
            updatedTime: questions.updatedTime != null && questions.updatedTime.isValid() ? questions.updatedTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
        res.body.updatedTime = res.body.updatedTime != null ? moment(res.body.updatedTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((questions: IQuestions) => {
            questions.createdTime = questions.createdTime != null ? moment(questions.createdTime) : null;
            questions.updatedTime = questions.updatedTime != null ? moment(questions.updatedTime) : null;
        });
        return res;
    }
}

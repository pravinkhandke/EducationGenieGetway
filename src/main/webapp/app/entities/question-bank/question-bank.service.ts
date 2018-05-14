import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionBank } from 'app/shared/model/question-bank.model';

type EntityResponseType = HttpResponse<IQuestionBank>;
type EntityArrayResponseType = HttpResponse<IQuestionBank[]>;

@Injectable()
export class QuestionBankService {
    private resourceUrl = SERVER_API_URL + 'api/question-banks';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/question-banks';

    constructor(private http: HttpClient) {}

    create(questionBank: IQuestionBank): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionBank);
        return this.http
            .post<IQuestionBank>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(questionBank: IQuestionBank): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(questionBank);
        return this.http
            .put<IQuestionBank>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IQuestionBank>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestionBank[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IQuestionBank[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(questionBank: IQuestionBank): IQuestionBank {
        const copy: IQuestionBank = Object.assign({}, questionBank, {
            createdTime: questionBank.createdTime != null && questionBank.createdTime.isValid() ? questionBank.createdTime.toJSON() : null,
            updatedTime: questionBank.updatedTime != null && questionBank.updatedTime.isValid() ? questionBank.updatedTime.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createdTime = res.body.createdTime != null ? moment(res.body.createdTime) : null;
        res.body.updatedTime = res.body.updatedTime != null ? moment(res.body.updatedTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((questionBank: IQuestionBank) => {
            questionBank.createdTime = questionBank.createdTime != null ? moment(questionBank.createdTime) : null;
            questionBank.updatedTime = questionBank.updatedTime != null ? moment(questionBank.updatedTime) : null;
        });
        return res;
    }
}

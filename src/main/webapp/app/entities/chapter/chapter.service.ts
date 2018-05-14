import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChapter } from 'app/shared/model/chapter.model';

type EntityResponseType = HttpResponse<IChapter>;
type EntityArrayResponseType = HttpResponse<IChapter[]>;

@Injectable()
export class ChapterService {
    private resourceUrl = SERVER_API_URL + 'api/chapters';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/chapters';

    constructor(private http: HttpClient) {}

    create(chapter: IChapter): Observable<EntityResponseType> {
        return this.http.post<IChapter>(this.resourceUrl, chapter, { observe: 'response' });
    }

    update(chapter: IChapter): Observable<EntityResponseType> {
        return this.http.put<IChapter>(this.resourceUrl, chapter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChapter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChapter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChapter[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContentList } from 'app/shared/model/content-list.model';

type EntityResponseType = HttpResponse<IContentList>;
type EntityArrayResponseType = HttpResponse<IContentList[]>;

@Injectable()
export class ContentListService {
    private resourceUrl = SERVER_API_URL + 'api/content-lists';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/content-lists';

    constructor(private http: HttpClient) {}

    create(contentList: IContentList): Observable<EntityResponseType> {
        return this.http.post<IContentList>(this.resourceUrl, contentList, { observe: 'response' });
    }

    update(contentList: IContentList): Observable<EntityResponseType> {
        return this.http.put<IContentList>(this.resourceUrl, contentList, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IContentList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContentList[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IContentList[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

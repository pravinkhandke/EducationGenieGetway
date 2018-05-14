import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IContentList } from 'app/shared/model/content-list.model';
import { ContentListService } from './content-list.service';
import { IContent } from 'app/shared/model/content.model';
import { ContentService } from 'app/entities/content';

@Component({
    selector: 'jhi-content-list-update',
    templateUrl: './content-list-update.component.html'
})
export class ContentListUpdateComponent implements OnInit {
    private _contentList: IContentList;
    isSaving: boolean;

    contents: IContent[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private contentListService: ContentListService,
        private contentService: ContentService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ contentList }) => {
            this.contentList = contentList.body ? contentList.body : contentList;
        });
        this.contentService.query().subscribe(
            (res: HttpResponse<IContent[]>) => {
                this.contents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contentList.id !== undefined) {
            this.subscribeToSaveResponse(this.contentListService.update(this.contentList));
        } else {
            this.subscribeToSaveResponse(this.contentListService.create(this.contentList));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContentList>>) {
        result.subscribe((res: HttpResponse<IContentList>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackContentById(index: number, item: IContent) {
        return item.id;
    }
    get contentList() {
        return this._contentList;
    }

    set contentList(contentList: IContentList) {
        this._contentList = contentList;
    }
}

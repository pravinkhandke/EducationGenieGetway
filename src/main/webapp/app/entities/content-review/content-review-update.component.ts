import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IContentReview } from 'app/shared/model/content-review.model';
import { ContentReviewService } from './content-review.service';
import { IContent } from 'app/shared/model/content.model';
import { ContentService } from 'app/entities/content';

@Component({
    selector: 'jhi-content-review-update',
    templateUrl: './content-review-update.component.html'
})
export class ContentReviewUpdateComponent implements OnInit {
    private _contentReview: IContentReview;
    isSaving: boolean;

    contents: IContent[];
    createdTime: string;
    updatedTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private contentReviewService: ContentReviewService,
        private contentService: ContentService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ contentReview }) => {
            this.contentReview = contentReview.body ? contentReview.body : contentReview;
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
        this.contentReview.createdTime = moment(this.createdTime, DATE_TIME_FORMAT);
        this.contentReview.updatedTime = moment(this.updatedTime, DATE_TIME_FORMAT);
        if (this.contentReview.id !== undefined) {
            this.subscribeToSaveResponse(this.contentReviewService.update(this.contentReview));
        } else {
            this.subscribeToSaveResponse(this.contentReviewService.create(this.contentReview));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContentReview>>) {
        result.subscribe((res: HttpResponse<IContentReview>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get contentReview() {
        return this._contentReview;
    }

    set contentReview(contentReview: IContentReview) {
        this._contentReview = contentReview;
        this.createdTime = moment(contentReview.createdTime).format();
        this.updatedTime = moment(contentReview.updatedTime).format();
    }
}

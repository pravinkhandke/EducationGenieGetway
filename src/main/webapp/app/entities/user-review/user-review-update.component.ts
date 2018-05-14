import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserReview } from 'app/shared/model/user-review.model';
import { UserReviewService } from './user-review.service';

@Component({
    selector: 'jhi-user-review-update',
    templateUrl: './user-review-update.component.html'
})
export class UserReviewUpdateComponent implements OnInit {
    private _userReview: IUserReview;
    isSaving: boolean;
    createdTime: string;
    updatedTime: string;

    constructor(private userReviewService: UserReviewService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ userReview }) => {
            this.userReview = userReview.body ? userReview.body : userReview;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.userReview.createdTime = moment(this.createdTime, DATE_TIME_FORMAT);
        this.userReview.updatedTime = moment(this.updatedTime, DATE_TIME_FORMAT);
        if (this.userReview.id !== undefined) {
            this.subscribeToSaveResponse(this.userReviewService.update(this.userReview));
        } else {
            this.subscribeToSaveResponse(this.userReviewService.create(this.userReview));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserReview>>) {
        result.subscribe((res: HttpResponse<IUserReview>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get userReview() {
        return this._userReview;
    }

    set userReview(userReview: IUserReview) {
        this._userReview = userReview;
        this.createdTime = moment(userReview.createdTime).format();
        this.updatedTime = moment(userReview.updatedTime).format();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IUserInfo } from 'app/shared/model/user-info.model';
import { UserInfoService } from './user-info.service';
import { IUserReview } from 'app/shared/model/user-review.model';
import { UserReviewService } from 'app/entities/user-review';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade';

@Component({
    selector: 'jhi-user-info-update',
    templateUrl: './user-info-update.component.html'
})
export class UserInfoUpdateComponent implements OnInit {
    private _userInfo: IUserInfo;
    isSaving: boolean;

    userreviews: IUserReview[];

    grades: IGrade[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private userInfoService: UserInfoService,
        private userReviewService: UserReviewService,
        private gradeService: GradeService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ userInfo }) => {
            this.userInfo = userInfo.body ? userInfo.body : userInfo;
        });
        this.userReviewService.query({ filter: 'userinfo-is-null' }).subscribe(
            (res: HttpResponse<IUserReview[]>) => {
                if (!this.userInfo.userReviewId) {
                    this.userreviews = res.body;
                } else {
                    this.userReviewService.find(this.userInfo.userReviewId).subscribe(
                        (subRes: HttpResponse<IUserReview>) => {
                            this.userreviews = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.gradeService.query({ filter: 'userinfo-is-null' }).subscribe(
            (res: HttpResponse<IGrade[]>) => {
                if (!this.userInfo.gradeId) {
                    this.grades = res.body;
                } else {
                    this.gradeService.find(this.userInfo.gradeId).subscribe(
                        (subRes: HttpResponse<IGrade>) => {
                            this.grades = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.userInfoService.update(this.userInfo));
        } else {
            this.subscribeToSaveResponse(this.userInfoService.create(this.userInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserInfo>>) {
        result.subscribe((res: HttpResponse<IUserInfo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserReviewById(index: number, item: IUserReview) {
        return item.id;
    }

    trackGradeById(index: number, item: IGrade) {
        return item.id;
    }
    get userInfo() {
        return this._userInfo;
    }

    set userInfo(userInfo: IUserInfo) {
        this._userInfo = userInfo;
    }
}

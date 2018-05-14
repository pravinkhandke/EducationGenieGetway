import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IScore } from 'app/shared/model/score.model';
import { ScoreService } from './score.service';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { UserInfoService } from 'app/entities/user-info';

@Component({
    selector: 'jhi-score-update',
    templateUrl: './score-update.component.html'
})
export class ScoreUpdateComponent implements OnInit {
    private _score: IScore;
    isSaving: boolean;

    userinfos: IUserInfo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private scoreService: ScoreService,
        private userInfoService: UserInfoService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ score }) => {
            this.score = score.body ? score.body : score;
        });
        this.userInfoService.query().subscribe(
            (res: HttpResponse<IUserInfo[]>) => {
                this.userinfos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.score.id !== undefined) {
            this.subscribeToSaveResponse(this.scoreService.update(this.score));
        } else {
            this.subscribeToSaveResponse(this.scoreService.create(this.score));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IScore>>) {
        result.subscribe((res: HttpResponse<IScore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserInfoById(index: number, item: IUserInfo) {
        return item.id;
    }
    get score() {
        return this._score;
    }

    set score(score: IScore) {
        this._score = score;
    }
}

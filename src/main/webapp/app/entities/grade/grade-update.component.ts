import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from './grade.service';
import { IContent } from 'app/shared/model/content.model';
import { ContentService } from 'app/entities/content';

@Component({
    selector: 'jhi-grade-update',
    templateUrl: './grade-update.component.html'
})
export class GradeUpdateComponent implements OnInit {
    private _grade: IGrade;
    isSaving: boolean;

    contents: IContent[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private gradeService: GradeService,
        private contentService: ContentService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ grade }) => {
            this.grade = grade.body ? grade.body : grade;
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
        if (this.grade.id !== undefined) {
            this.subscribeToSaveResponse(this.gradeService.update(this.grade));
        } else {
            this.subscribeToSaveResponse(this.gradeService.create(this.grade));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGrade>>) {
        result.subscribe((res: HttpResponse<IGrade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get grade() {
        return this._grade;
    }

    set grade(grade: IGrade) {
        this._grade = grade;
    }
}

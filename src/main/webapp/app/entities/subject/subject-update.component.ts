import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from './subject.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade';

@Component({
    selector: 'jhi-subject-update',
    templateUrl: './subject-update.component.html'
})
export class SubjectUpdateComponent implements OnInit {
    private _subject: ISubject;
    isSaving: boolean;

    grades: IGrade[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private subjectService: SubjectService,
        private gradeService: GradeService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ subject }) => {
            this.subject = subject.body ? subject.body : subject;
        });
        this.gradeService.query().subscribe(
            (res: HttpResponse<IGrade[]>) => {
                this.grades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.subject.id !== undefined) {
            this.subscribeToSaveResponse(this.subjectService.update(this.subject));
        } else {
            this.subscribeToSaveResponse(this.subjectService.create(this.subject));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISubject>>) {
        result.subscribe((res: HttpResponse<ISubject>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGradeById(index: number, item: IGrade) {
        return item.id;
    }
    get subject() {
        return this._subject;
    }

    set subject(subject: ISubject) {
        this._subject = subject;
    }
}

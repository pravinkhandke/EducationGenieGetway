import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from './chapter.service';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject';

@Component({
    selector: 'jhi-chapter-update',
    templateUrl: './chapter-update.component.html'
})
export class ChapterUpdateComponent implements OnInit {
    private _chapter: IChapter;
    isSaving: boolean;

    subjects: ISubject[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private chapterService: ChapterService,
        private subjectService: SubjectService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ chapter }) => {
            this.chapter = chapter.body ? chapter.body : chapter;
        });
        this.subjectService.query().subscribe(
            (res: HttpResponse<ISubject[]>) => {
                this.subjects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chapter.id !== undefined) {
            this.subscribeToSaveResponse(this.chapterService.update(this.chapter));
        } else {
            this.subscribeToSaveResponse(this.chapterService.create(this.chapter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChapter>>) {
        result.subscribe((res: HttpResponse<IChapter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSubjectById(index: number, item: ISubject) {
        return item.id;
    }
    get chapter() {
        return this._chapter;
    }

    set chapter(chapter: IChapter) {
        this._chapter = chapter;
    }
}

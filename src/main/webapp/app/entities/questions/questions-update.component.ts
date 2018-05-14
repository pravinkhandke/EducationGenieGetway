import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestions } from 'app/shared/model/questions.model';
import { QuestionsService } from './questions.service';
import { IQuestionBank } from 'app/shared/model/question-bank.model';
import { QuestionBankService } from 'app/entities/question-bank';

@Component({
    selector: 'jhi-questions-update',
    templateUrl: './questions-update.component.html'
})
export class QuestionsUpdateComponent implements OnInit {
    private _questions: IQuestions;
    isSaving: boolean;

    questionbanks: IQuestionBank[];
    createdTime: string;
    updatedTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionsService: QuestionsService,
        private questionBankService: QuestionBankService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ questions }) => {
            this.questions = questions.body ? questions.body : questions;
        });
        this.questionBankService.query().subscribe(
            (res: HttpResponse<IQuestionBank[]>) => {
                this.questionbanks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.questions.createdTime = moment(this.createdTime, DATE_TIME_FORMAT);
        this.questions.updatedTime = moment(this.updatedTime, DATE_TIME_FORMAT);
        if (this.questions.id !== undefined) {
            this.subscribeToSaveResponse(this.questionsService.update(this.questions));
        } else {
            this.subscribeToSaveResponse(this.questionsService.create(this.questions));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestions>>) {
        result.subscribe((res: HttpResponse<IQuestions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackQuestionBankById(index: number, item: IQuestionBank) {
        return item.id;
    }
    get questions() {
        return this._questions;
    }

    set questions(questions: IQuestions) {
        this._questions = questions;
        this.createdTime = moment(questions.createdTime).format();
        this.updatedTime = moment(questions.updatedTime).format();
    }
}

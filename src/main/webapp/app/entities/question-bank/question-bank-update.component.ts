import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IQuestionBank } from 'app/shared/model/question-bank.model';
import { QuestionBankService } from './question-bank.service';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { UserInfoService } from 'app/entities/user-info';
import { IScore } from 'app/shared/model/score.model';
import { ScoreService } from 'app/entities/score';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from 'app/entities/topic';

@Component({
    selector: 'jhi-question-bank-update',
    templateUrl: './question-bank-update.component.html'
})
export class QuestionBankUpdateComponent implements OnInit {
    private _questionBank: IQuestionBank;
    isSaving: boolean;

    userinfos: IUserInfo[];

    scores: IScore[];

    subjects: ISubject[];

    grades: IGrade[];

    chapters: IChapter[];

    topics: ITopic[];
    createdTime: string;
    updatedTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private questionBankService: QuestionBankService,
        private userInfoService: UserInfoService,
        private scoreService: ScoreService,
        private subjectService: SubjectService,
        private gradeService: GradeService,
        private chapterService: ChapterService,
        private topicService: TopicService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ questionBank }) => {
            this.questionBank = questionBank.body ? questionBank.body : questionBank;
        });
        this.userInfoService.query().subscribe(
            (res: HttpResponse<IUserInfo[]>) => {
                this.userinfos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.scoreService.query({ filter: 'questionbank-is-null' }).subscribe(
            (res: HttpResponse<IScore[]>) => {
                if (!this.questionBank.scoreId) {
                    this.scores = res.body;
                } else {
                    this.scoreService.find(this.questionBank.scoreId).subscribe(
                        (subRes: HttpResponse<IScore>) => {
                            this.scores = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.subjectService.query({ filter: 'questionbank-is-null' }).subscribe(
            (res: HttpResponse<ISubject[]>) => {
                if (!this.questionBank.subjectId) {
                    this.subjects = res.body;
                } else {
                    this.subjectService.find(this.questionBank.subjectId).subscribe(
                        (subRes: HttpResponse<ISubject>) => {
                            this.subjects = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.gradeService.query({ filter: 'questionbank-is-null' }).subscribe(
            (res: HttpResponse<IGrade[]>) => {
                if (!this.questionBank.gradeId) {
                    this.grades = res.body;
                } else {
                    this.gradeService.find(this.questionBank.gradeId).subscribe(
                        (subRes: HttpResponse<IGrade>) => {
                            this.grades = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.chapterService.query({ filter: 'questionbank-is-null' }).subscribe(
            (res: HttpResponse<IChapter[]>) => {
                if (!this.questionBank.chapterId) {
                    this.chapters = res.body;
                } else {
                    this.chapterService.find(this.questionBank.chapterId).subscribe(
                        (subRes: HttpResponse<IChapter>) => {
                            this.chapters = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.topicService.query({ filter: 'questionbank-is-null' }).subscribe(
            (res: HttpResponse<ITopic[]>) => {
                if (!this.questionBank.topicId) {
                    this.topics = res.body;
                } else {
                    this.topicService.find(this.questionBank.topicId).subscribe(
                        (subRes: HttpResponse<ITopic>) => {
                            this.topics = [subRes.body].concat(res.body);
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
        this.questionBank.createdTime = moment(this.createdTime, DATE_TIME_FORMAT);
        this.questionBank.updatedTime = moment(this.updatedTime, DATE_TIME_FORMAT);
        if (this.questionBank.id !== undefined) {
            this.subscribeToSaveResponse(this.questionBankService.update(this.questionBank));
        } else {
            this.subscribeToSaveResponse(this.questionBankService.create(this.questionBank));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionBank>>) {
        result.subscribe((res: HttpResponse<IQuestionBank>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackScoreById(index: number, item: IScore) {
        return item.id;
    }

    trackSubjectById(index: number, item: ISubject) {
        return item.id;
    }

    trackGradeById(index: number, item: IGrade) {
        return item.id;
    }

    trackChapterById(index: number, item: IChapter) {
        return item.id;
    }

    trackTopicById(index: number, item: ITopic) {
        return item.id;
    }
    get questionBank() {
        return this._questionBank;
    }

    set questionBank(questionBank: IQuestionBank) {
        this._questionBank = questionBank;
        this.createdTime = moment(questionBank.createdTime).format();
        this.updatedTime = moment(questionBank.updatedTime).format();
    }
}

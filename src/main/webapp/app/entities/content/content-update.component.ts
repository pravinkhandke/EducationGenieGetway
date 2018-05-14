import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IContent } from 'app/shared/model/content.model';
import { ContentService } from './content.service';
import { ISubject } from 'app/shared/model/subject.model';
import { SubjectService } from 'app/entities/subject';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter';
import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from 'app/entities/topic';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { UserInfoService } from 'app/entities/user-info';

@Component({
    selector: 'jhi-content-update',
    templateUrl: './content-update.component.html'
})
export class ContentUpdateComponent implements OnInit {
    private _content: IContent;
    isSaving: boolean;

    subjects: ISubject[];

    chapters: IChapter[];

    topics: ITopic[];

    grades: IGrade[];

    userinfos: IUserInfo[];
    createdTime: string;
    updatedTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private contentService: ContentService,
        private subjectService: SubjectService,
        private chapterService: ChapterService,
        private topicService: TopicService,
        private gradeService: GradeService,
        private userInfoService: UserInfoService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ content }) => {
            this.content = content.body ? content.body : content;
        });
        this.subjectService.query({ filter: 'content-is-null' }).subscribe(
            (res: HttpResponse<ISubject[]>) => {
                if (!this.content.subjectId) {
                    this.subjects = res.body;
                } else {
                    this.subjectService.find(this.content.subjectId).subscribe(
                        (subRes: HttpResponse<ISubject>) => {
                            this.subjects = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.chapterService.query({ filter: 'content-is-null' }).subscribe(
            (res: HttpResponse<IChapter[]>) => {
                if (!this.content.chapterId) {
                    this.chapters = res.body;
                } else {
                    this.chapterService.find(this.content.chapterId).subscribe(
                        (subRes: HttpResponse<IChapter>) => {
                            this.chapters = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.topicService.query({ filter: 'content-is-null' }).subscribe(
            (res: HttpResponse<ITopic[]>) => {
                if (!this.content.topicId) {
                    this.topics = res.body;
                } else {
                    this.topicService.find(this.content.topicId).subscribe(
                        (subRes: HttpResponse<ITopic>) => {
                            this.topics = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.gradeService.query().subscribe(
            (res: HttpResponse<IGrade[]>) => {
                this.grades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.content.createdTime = moment(this.createdTime, DATE_TIME_FORMAT);
        this.content.updatedTime = moment(this.updatedTime, DATE_TIME_FORMAT);
        if (this.content.id !== undefined) {
            this.subscribeToSaveResponse(this.contentService.update(this.content));
        } else {
            this.subscribeToSaveResponse(this.contentService.create(this.content));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContent>>) {
        result.subscribe((res: HttpResponse<IContent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackChapterById(index: number, item: IChapter) {
        return item.id;
    }

    trackTopicById(index: number, item: ITopic) {
        return item.id;
    }

    trackGradeById(index: number, item: IGrade) {
        return item.id;
    }

    trackUserInfoById(index: number, item: IUserInfo) {
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
    get content() {
        return this._content;
    }

    set content(content: IContent) {
        this._content = content;
        this.createdTime = moment(content.createdTime).format();
        this.updatedTime = moment(content.updatedTime).format();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { ITopic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter';

@Component({
    selector: 'jhi-topic-update',
    templateUrl: './topic-update.component.html'
})
export class TopicUpdateComponent implements OnInit {
    private _topic: ITopic;
    isSaving: boolean;

    chapters: IChapter[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private topicService: TopicService,
        private chapterService: ChapterService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ topic }) => {
            this.topic = topic.body ? topic.body : topic;
        });
        this.chapterService.query().subscribe(
            (res: HttpResponse<IChapter[]>) => {
                this.chapters = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.topic.id !== undefined) {
            this.subscribeToSaveResponse(this.topicService.update(this.topic));
        } else {
            this.subscribeToSaveResponse(this.topicService.create(this.topic));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITopic>>) {
        result.subscribe((res: HttpResponse<ITopic>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackChapterById(index: number, item: IChapter) {
        return item.id;
    }
    get topic() {
        return this._topic;
    }

    set topic(topic: ITopic) {
        this._topic = topic;
    }
}

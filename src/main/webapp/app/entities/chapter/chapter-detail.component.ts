import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChapter } from 'app/shared/model/chapter.model';

@Component({
    selector: 'jhi-chapter-detail',
    templateUrl: './chapter-detail.component.html'
})
export class ChapterDetailComponent implements OnInit {
    chapter: IChapter;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ chapter }) => {
            this.chapter = chapter.body ? chapter.body : chapter;
        });
    }

    previousState() {
        window.history.back();
    }
}

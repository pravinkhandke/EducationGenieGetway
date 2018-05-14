import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentList } from 'app/shared/model/content-list.model';

@Component({
    selector: 'jhi-content-list-detail',
    templateUrl: './content-list-detail.component.html'
})
export class ContentListDetailComponent implements OnInit {
    contentList: IContentList;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ contentList }) => {
            this.contentList = contentList.body ? contentList.body : contentList;
        });
    }

    previousState() {
        window.history.back();
    }
}

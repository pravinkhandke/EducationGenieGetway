import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContent } from 'app/shared/model/content.model';

@Component({
    selector: 'jhi-content-detail',
    templateUrl: './content-detail.component.html'
})
export class ContentDetailComponent implements OnInit {
    content: IContent;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ content }) => {
            this.content = content.body ? content.body : content;
        });
    }

    previousState() {
        window.history.back();
    }
}

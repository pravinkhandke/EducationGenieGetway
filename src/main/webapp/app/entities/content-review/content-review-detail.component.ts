import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentReview } from 'app/shared/model/content-review.model';

@Component({
    selector: 'jhi-content-review-detail',
    templateUrl: './content-review-detail.component.html'
})
export class ContentReviewDetailComponent implements OnInit {
    contentReview: IContentReview;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ contentReview }) => {
            this.contentReview = contentReview.body ? contentReview.body : contentReview;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserReview } from 'app/shared/model/user-review.model';

@Component({
    selector: 'jhi-user-review-detail',
    templateUrl: './user-review-detail.component.html'
})
export class UserReviewDetailComponent implements OnInit {
    userReview: IUserReview;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ userReview }) => {
            this.userReview = userReview.body ? userReview.body : userReview;
        });
    }

    previousState() {
        window.history.back();
    }
}

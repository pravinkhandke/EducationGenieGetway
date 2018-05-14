import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScore } from 'app/shared/model/score.model';

@Component({
    selector: 'jhi-score-detail',
    templateUrl: './score-detail.component.html'
})
export class ScoreDetailComponent implements OnInit {
    score: IScore;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ score }) => {
            this.score = score.body ? score.body : score;
        });
    }

    previousState() {
        window.history.back();
    }
}

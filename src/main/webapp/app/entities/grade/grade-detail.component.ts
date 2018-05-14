import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrade } from 'app/shared/model/grade.model';

@Component({
    selector: 'jhi-grade-detail',
    templateUrl: './grade-detail.component.html'
})
export class GradeDetailComponent implements OnInit {
    grade: IGrade;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ grade }) => {
            this.grade = grade.body ? grade.body : grade;
        });
    }

    previousState() {
        window.history.back();
    }
}

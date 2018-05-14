import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubject } from 'app/shared/model/subject.model';

@Component({
    selector: 'jhi-subject-detail',
    templateUrl: './subject-detail.component.html'
})
export class SubjectDetailComponent implements OnInit {
    subject: ISubject;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ subject }) => {
            this.subject = subject.body ? subject.body : subject;
        });
    }

    previousState() {
        window.history.back();
    }
}

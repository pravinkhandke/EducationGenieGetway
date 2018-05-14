import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestions } from 'app/shared/model/questions.model';

@Component({
    selector: 'jhi-questions-detail',
    templateUrl: './questions-detail.component.html'
})
export class QuestionsDetailComponent implements OnInit {
    questions: IQuestions;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ questions }) => {
            this.questions = questions.body ? questions.body : questions;
        });
    }

    previousState() {
        window.history.back();
    }
}

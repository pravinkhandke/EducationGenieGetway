import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionBank } from 'app/shared/model/question-bank.model';

@Component({
    selector: 'jhi-question-bank-detail',
    templateUrl: './question-bank-detail.component.html'
})
export class QuestionBankDetailComponent implements OnInit {
    questionBank: IQuestionBank;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ questionBank }) => {
            this.questionBank = questionBank.body ? questionBank.body : questionBank;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { QuestionBank } from 'app/shared/model/question-bank.model';
import { QuestionBankService } from './question-bank.service';
import { QuestionBankComponent } from './question-bank.component';
import { QuestionBankDetailComponent } from './question-bank-detail.component';
import { QuestionBankUpdateComponent } from './question-bank-update.component';
import { QuestionBankDeletePopupComponent } from './question-bank-delete-dialog.component';

@Injectable()
export class QuestionBankResolve implements Resolve<any> {
    constructor(private service: QuestionBankService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new QuestionBank();
    }
}

export const questionBankRoute: Routes = [
    {
        path: 'question-bank',
        component: QuestionBankComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questionBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-bank/:id/view',
        component: QuestionBankDetailComponent,
        resolve: {
            questionBank: QuestionBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questionBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-bank/new',
        component: QuestionBankUpdateComponent,
        resolve: {
            questionBank: QuestionBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questionBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'question-bank/:id/edit',
        component: QuestionBankUpdateComponent,
        resolve: {
            questionBank: QuestionBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questionBank.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionBankPopupRoute: Routes = [
    {
        path: 'question-bank/:id/delete',
        component: QuestionBankDeletePopupComponent,
        resolve: {
            questionBank: QuestionBankResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questionBank.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

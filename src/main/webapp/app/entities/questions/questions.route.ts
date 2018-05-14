import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core';
import { Questions } from 'app/shared/model/questions.model';
import { QuestionsService } from './questions.service';
import { QuestionsComponent } from './questions.component';
import { QuestionsDetailComponent } from './questions-detail.component';
import { QuestionsUpdateComponent } from './questions-update.component';
import { QuestionsDeletePopupComponent } from './questions-delete-dialog.component';

@Injectable()
export class QuestionsResolvePagingParams implements Resolve<any> {
    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

@Injectable()
export class QuestionsResolve implements Resolve<any> {
    constructor(private service: QuestionsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Questions();
    }
}

export const questionsRoute: Routes = [
    {
        path: 'questions',
        component: QuestionsComponent,
        resolve: {
            pagingParams: QuestionsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questions/:id/view',
        component: QuestionsDetailComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questions/new',
        component: QuestionsUpdateComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'questions/:id/edit',
        component: QuestionsUpdateComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionsPopupRoute: Routes = [
    {
        path: 'questions/:id/delete',
        component: QuestionsDeletePopupComponent,
        resolve: {
            questions: QuestionsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.questions.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

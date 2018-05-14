import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Score } from 'app/shared/model/score.model';
import { ScoreService } from './score.service';
import { ScoreComponent } from './score.component';
import { ScoreDetailComponent } from './score-detail.component';
import { ScoreUpdateComponent } from './score-update.component';
import { ScoreDeletePopupComponent } from './score-delete-dialog.component';

@Injectable()
export class ScoreResolve implements Resolve<any> {
    constructor(private service: ScoreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Score();
    }
}

export const scoreRoute: Routes = [
    {
        path: 'score',
        component: ScoreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.score.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'score/:id/view',
        component: ScoreDetailComponent,
        resolve: {
            score: ScoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.score.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'score/new',
        component: ScoreUpdateComponent,
        resolve: {
            score: ScoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.score.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'score/:id/edit',
        component: ScoreUpdateComponent,
        resolve: {
            score: ScoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.score.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const scorePopupRoute: Routes = [
    {
        path: 'score/:id/delete',
        component: ScoreDeletePopupComponent,
        resolve: {
            score: ScoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.score.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

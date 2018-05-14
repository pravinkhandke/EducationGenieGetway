import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core';
import { UserReview } from 'app/shared/model/user-review.model';
import { UserReviewService } from './user-review.service';
import { UserReviewComponent } from './user-review.component';
import { UserReviewDetailComponent } from './user-review-detail.component';
import { UserReviewUpdateComponent } from './user-review-update.component';
import { UserReviewDeletePopupComponent } from './user-review-delete-dialog.component';

@Injectable()
export class UserReviewResolvePagingParams implements Resolve<any> {
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
export class UserReviewResolve implements Resolve<any> {
    constructor(private service: UserReviewService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new UserReview();
    }
}

export const userReviewRoute: Routes = [
    {
        path: 'user-review',
        component: UserReviewComponent,
        resolve: {
            pagingParams: UserReviewResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-review/:id/view',
        component: UserReviewDetailComponent,
        resolve: {
            userReview: UserReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-review/new',
        component: UserReviewUpdateComponent,
        resolve: {
            userReview: UserReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-review/:id/edit',
        component: UserReviewUpdateComponent,
        resolve: {
            userReview: UserReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userReviewPopupRoute: Routes = [
    {
        path: 'user-review/:id/delete',
        component: UserReviewDeletePopupComponent,
        resolve: {
            userReview: UserReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userReview.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

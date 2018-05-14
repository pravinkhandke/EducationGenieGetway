import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from 'app/core';
import { ContentReview } from 'app/shared/model/content-review.model';
import { ContentReviewService } from './content-review.service';
import { ContentReviewComponent } from './content-review.component';
import { ContentReviewDetailComponent } from './content-review-detail.component';
import { ContentReviewUpdateComponent } from './content-review-update.component';
import { ContentReviewDeletePopupComponent } from './content-review-delete-dialog.component';

@Injectable()
export class ContentReviewResolvePagingParams implements Resolve<any> {
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
export class ContentReviewResolve implements Resolve<any> {
    constructor(private service: ContentReviewService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new ContentReview();
    }
}

export const contentReviewRoute: Routes = [
    {
        path: 'content-review',
        component: ContentReviewComponent,
        resolve: {
            pagingParams: ContentReviewResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content-review/:id/view',
        component: ContentReviewDetailComponent,
        resolve: {
            contentReview: ContentReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content-review/new',
        component: ContentReviewUpdateComponent,
        resolve: {
            contentReview: ContentReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content-review/:id/edit',
        component: ContentReviewUpdateComponent,
        resolve: {
            contentReview: ContentReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentReview.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contentReviewPopupRoute: Routes = [
    {
        path: 'content-review/:id/delete',
        component: ContentReviewDeletePopupComponent,
        resolve: {
            contentReview: ContentReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentReview.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

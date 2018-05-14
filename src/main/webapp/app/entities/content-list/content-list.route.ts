import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ContentList } from 'app/shared/model/content-list.model';
import { ContentListService } from './content-list.service';
import { ContentListComponent } from './content-list.component';
import { ContentListDetailComponent } from './content-list-detail.component';
import { ContentListUpdateComponent } from './content-list-update.component';
import { ContentListDeletePopupComponent } from './content-list-delete-dialog.component';

@Injectable()
export class ContentListResolve implements Resolve<any> {
    constructor(private service: ContentListService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new ContentList();
    }
}

export const contentListRoute: Routes = [
    {
        path: 'content-list',
        component: ContentListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content-list/:id/view',
        component: ContentListDetailComponent,
        resolve: {
            contentList: ContentListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content-list/new',
        component: ContentListUpdateComponent,
        resolve: {
            contentList: ContentListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content-list/:id/edit',
        component: ContentListUpdateComponent,
        resolve: {
            contentList: ContentListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contentListPopupRoute: Routes = [
    {
        path: 'content-list/:id/delete',
        component: ContentListDeletePopupComponent,
        resolve: {
            contentList: ContentListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.contentList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

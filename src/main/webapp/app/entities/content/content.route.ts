import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Content } from 'app/shared/model/content.model';
import { ContentService } from './content.service';
import { ContentComponent } from './content.component';
import { ContentDetailComponent } from './content-detail.component';
import { ContentUpdateComponent } from './content-update.component';
import { ContentDeletePopupComponent } from './content-delete-dialog.component';

@Injectable()
export class ContentResolve implements Resolve<any> {
    constructor(private service: ContentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Content();
    }
}

export const contentRoute: Routes = [
    {
        path: 'content',
        component: ContentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.content.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content/:id/view',
        component: ContentDetailComponent,
        resolve: {
            content: ContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.content.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content/new',
        component: ContentUpdateComponent,
        resolve: {
            content: ContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.content.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'content/:id/edit',
        component: ContentUpdateComponent,
        resolve: {
            content: ContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.content.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contentPopupRoute: Routes = [
    {
        path: 'content/:id/delete',
        component: ContentDeletePopupComponent,
        resolve: {
            content: ContentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.content.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

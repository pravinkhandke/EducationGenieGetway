import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Topic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';
import { TopicComponent } from './topic.component';
import { TopicDetailComponent } from './topic-detail.component';
import { TopicUpdateComponent } from './topic-update.component';
import { TopicDeletePopupComponent } from './topic-delete-dialog.component';

@Injectable()
export class TopicResolve implements Resolve<any> {
    constructor(private service: TopicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Topic();
    }
}

export const topicRoute: Routes = [
    {
        path: 'topic',
        component: TopicComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'topic/:id/view',
        component: TopicDetailComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'topic/new',
        component: TopicUpdateComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'topic/:id/edit',
        component: TopicUpdateComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const topicPopupRoute: Routes = [
    {
        path: 'topic/:id/delete',
        component: TopicDeletePopupComponent,
        resolve: {
            topic: TopicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Chapter } from 'app/shared/model/chapter.model';
import { ChapterService } from './chapter.service';
import { ChapterComponent } from './chapter.component';
import { ChapterDetailComponent } from './chapter-detail.component';
import { ChapterUpdateComponent } from './chapter-update.component';
import { ChapterDeletePopupComponent } from './chapter-delete-dialog.component';

@Injectable()
export class ChapterResolve implements Resolve<any> {
    constructor(private service: ChapterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Chapter();
    }
}

export const chapterRoute: Routes = [
    {
        path: 'chapter',
        component: ChapterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.chapter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chapter/:id/view',
        component: ChapterDetailComponent,
        resolve: {
            chapter: ChapterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.chapter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chapter/new',
        component: ChapterUpdateComponent,
        resolve: {
            chapter: ChapterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.chapter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chapter/:id/edit',
        component: ChapterUpdateComponent,
        resolve: {
            chapter: ChapterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.chapter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chapterPopupRoute: Routes = [
    {
        path: 'chapter/:id/delete',
        component: ChapterDeletePopupComponent,
        resolve: {
            chapter: ChapterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.chapter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

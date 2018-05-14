import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Subject } from 'app/shared/model/subject.model';
import { SubjectService } from './subject.service';
import { SubjectComponent } from './subject.component';
import { SubjectDetailComponent } from './subject-detail.component';
import { SubjectUpdateComponent } from './subject-update.component';
import { SubjectDeletePopupComponent } from './subject-delete-dialog.component';

@Injectable()
export class SubjectResolve implements Resolve<any> {
    constructor(private service: SubjectService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Subject();
    }
}

export const subjectRoute: Routes = [
    {
        path: 'subject',
        component: SubjectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.subject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'subject/:id/view',
        component: SubjectDetailComponent,
        resolve: {
            subject: SubjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.subject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'subject/new',
        component: SubjectUpdateComponent,
        resolve: {
            subject: SubjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.subject.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'subject/:id/edit',
        component: SubjectUpdateComponent,
        resolve: {
            subject: SubjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.subject.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subjectPopupRoute: Routes = [
    {
        path: 'subject/:id/delete',
        component: SubjectDeletePopupComponent,
        resolve: {
            subject: SubjectResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.subject.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

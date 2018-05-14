import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { UserInfo } from 'app/shared/model/user-info.model';
import { UserInfoService } from './user-info.service';
import { UserInfoComponent } from './user-info.component';
import { UserInfoDetailComponent } from './user-info-detail.component';
import { UserInfoUpdateComponent } from './user-info-update.component';
import { UserInfoDeletePopupComponent } from './user-info-delete-dialog.component';

@Injectable()
export class UserInfoResolve implements Resolve<any> {
    constructor(private service: UserInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new UserInfo();
    }
}

export const userInfoRoute: Routes = [
    {
        path: 'user-info',
        component: UserInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-info/:id/view',
        component: UserInfoDetailComponent,
        resolve: {
            userInfo: UserInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-info/new',
        component: UserInfoUpdateComponent,
        resolve: {
            userInfo: UserInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-info/:id/edit',
        component: UserInfoUpdateComponent,
        resolve: {
            userInfo: UserInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userInfo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userInfoPopupRoute: Routes = [
    {
        path: 'user-info/:id/delete',
        component: UserInfoDeletePopupComponent,
        resolve: {
            userInfo: UserInfoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'educationGenieApp.userInfo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

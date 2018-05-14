import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    UserInfoService,
    UserInfoComponent,
    UserInfoDetailComponent,
    UserInfoUpdateComponent,
    UserInfoDeletePopupComponent,
    UserInfoDeleteDialogComponent,
    userInfoRoute,
    userInfoPopupRoute,
    UserInfoResolve
} from './';

const ENTITY_STATES = [...userInfoRoute, ...userInfoPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserInfoComponent,
        UserInfoDetailComponent,
        UserInfoUpdateComponent,
        UserInfoDeleteDialogComponent,
        UserInfoDeletePopupComponent
    ],
    entryComponents: [UserInfoComponent, UserInfoUpdateComponent, UserInfoDeleteDialogComponent, UserInfoDeletePopupComponent],
    providers: [UserInfoService, UserInfoResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieUserInfoModule {}

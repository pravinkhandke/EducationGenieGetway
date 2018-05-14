import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    UserReviewService,
    UserReviewComponent,
    UserReviewDetailComponent,
    UserReviewUpdateComponent,
    UserReviewDeletePopupComponent,
    UserReviewDeleteDialogComponent,
    userReviewRoute,
    userReviewPopupRoute,
    UserReviewResolve,
    UserReviewResolvePagingParams
} from './';

const ENTITY_STATES = [...userReviewRoute, ...userReviewPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserReviewComponent,
        UserReviewDetailComponent,
        UserReviewUpdateComponent,
        UserReviewDeleteDialogComponent,
        UserReviewDeletePopupComponent
    ],
    entryComponents: [UserReviewComponent, UserReviewUpdateComponent, UserReviewDeleteDialogComponent, UserReviewDeletePopupComponent],
    providers: [UserReviewService, UserReviewResolve, UserReviewResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieUserReviewModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    ContentReviewService,
    ContentReviewComponent,
    ContentReviewDetailComponent,
    ContentReviewUpdateComponent,
    ContentReviewDeletePopupComponent,
    ContentReviewDeleteDialogComponent,
    contentReviewRoute,
    contentReviewPopupRoute,
    ContentReviewResolve,
    ContentReviewResolvePagingParams
} from './';

const ENTITY_STATES = [...contentReviewRoute, ...contentReviewPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContentReviewComponent,
        ContentReviewDetailComponent,
        ContentReviewUpdateComponent,
        ContentReviewDeleteDialogComponent,
        ContentReviewDeletePopupComponent
    ],
    entryComponents: [
        ContentReviewComponent,
        ContentReviewUpdateComponent,
        ContentReviewDeleteDialogComponent,
        ContentReviewDeletePopupComponent
    ],
    providers: [ContentReviewService, ContentReviewResolve, ContentReviewResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieContentReviewModule {}

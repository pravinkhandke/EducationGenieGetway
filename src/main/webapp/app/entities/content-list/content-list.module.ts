import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    ContentListService,
    ContentListComponent,
    ContentListDetailComponent,
    ContentListUpdateComponent,
    ContentListDeletePopupComponent,
    ContentListDeleteDialogComponent,
    contentListRoute,
    contentListPopupRoute,
    ContentListResolve
} from './';

const ENTITY_STATES = [...contentListRoute, ...contentListPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContentListComponent,
        ContentListDetailComponent,
        ContentListUpdateComponent,
        ContentListDeleteDialogComponent,
        ContentListDeletePopupComponent
    ],
    entryComponents: [ContentListComponent, ContentListUpdateComponent, ContentListDeleteDialogComponent, ContentListDeletePopupComponent],
    providers: [ContentListService, ContentListResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieContentListModule {}

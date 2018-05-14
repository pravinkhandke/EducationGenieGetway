import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    ContentService,
    ContentComponent,
    ContentDetailComponent,
    ContentUpdateComponent,
    ContentDeletePopupComponent,
    ContentDeleteDialogComponent,
    contentRoute,
    contentPopupRoute,
    ContentResolve
} from './';

const ENTITY_STATES = [...contentRoute, ...contentPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContentComponent,
        ContentDetailComponent,
        ContentUpdateComponent,
        ContentDeleteDialogComponent,
        ContentDeletePopupComponent
    ],
    entryComponents: [ContentComponent, ContentUpdateComponent, ContentDeleteDialogComponent, ContentDeletePopupComponent],
    providers: [ContentService, ContentResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieContentModule {}

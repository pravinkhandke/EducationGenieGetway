import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    ChapterService,
    ChapterComponent,
    ChapterDetailComponent,
    ChapterUpdateComponent,
    ChapterDeletePopupComponent,
    ChapterDeleteDialogComponent,
    chapterRoute,
    chapterPopupRoute,
    ChapterResolve
} from './';

const ENTITY_STATES = [...chapterRoute, ...chapterPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChapterComponent,
        ChapterDetailComponent,
        ChapterUpdateComponent,
        ChapterDeleteDialogComponent,
        ChapterDeletePopupComponent
    ],
    entryComponents: [ChapterComponent, ChapterUpdateComponent, ChapterDeleteDialogComponent, ChapterDeletePopupComponent],
    providers: [ChapterService, ChapterResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieChapterModule {}

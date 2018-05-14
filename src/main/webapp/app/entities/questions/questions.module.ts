import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    QuestionsService,
    QuestionsComponent,
    QuestionsDetailComponent,
    QuestionsUpdateComponent,
    QuestionsDeletePopupComponent,
    QuestionsDeleteDialogComponent,
    questionsRoute,
    questionsPopupRoute,
    QuestionsResolve,
    QuestionsResolvePagingParams
} from './';

const ENTITY_STATES = [...questionsRoute, ...questionsPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionsComponent,
        QuestionsDetailComponent,
        QuestionsUpdateComponent,
        QuestionsDeleteDialogComponent,
        QuestionsDeletePopupComponent
    ],
    entryComponents: [QuestionsComponent, QuestionsUpdateComponent, QuestionsDeleteDialogComponent, QuestionsDeletePopupComponent],
    providers: [QuestionsService, QuestionsResolve, QuestionsResolvePagingParams],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieQuestionsModule {}

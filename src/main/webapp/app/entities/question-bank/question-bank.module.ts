import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    QuestionBankService,
    QuestionBankComponent,
    QuestionBankDetailComponent,
    QuestionBankUpdateComponent,
    QuestionBankDeletePopupComponent,
    QuestionBankDeleteDialogComponent,
    questionBankRoute,
    questionBankPopupRoute,
    QuestionBankResolve
} from './';

const ENTITY_STATES = [...questionBankRoute, ...questionBankPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionBankComponent,
        QuestionBankDetailComponent,
        QuestionBankUpdateComponent,
        QuestionBankDeleteDialogComponent,
        QuestionBankDeletePopupComponent
    ],
    entryComponents: [
        QuestionBankComponent,
        QuestionBankUpdateComponent,
        QuestionBankDeleteDialogComponent,
        QuestionBankDeletePopupComponent
    ],
    providers: [QuestionBankService, QuestionBankResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieQuestionBankModule {}

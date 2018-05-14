import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    SubjectService,
    SubjectComponent,
    SubjectDetailComponent,
    SubjectUpdateComponent,
    SubjectDeletePopupComponent,
    SubjectDeleteDialogComponent,
    subjectRoute,
    subjectPopupRoute,
    SubjectResolve
} from './';

const ENTITY_STATES = [...subjectRoute, ...subjectPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SubjectComponent,
        SubjectDetailComponent,
        SubjectUpdateComponent,
        SubjectDeleteDialogComponent,
        SubjectDeletePopupComponent
    ],
    entryComponents: [SubjectComponent, SubjectUpdateComponent, SubjectDeleteDialogComponent, SubjectDeletePopupComponent],
    providers: [SubjectService, SubjectResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieSubjectModule {}

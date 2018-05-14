import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    GradeService,
    GradeComponent,
    GradeDetailComponent,
    GradeUpdateComponent,
    GradeDeletePopupComponent,
    GradeDeleteDialogComponent,
    gradeRoute,
    gradePopupRoute,
    GradeResolve
} from './';

const ENTITY_STATES = [...gradeRoute, ...gradePopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GradeComponent, GradeDetailComponent, GradeUpdateComponent, GradeDeleteDialogComponent, GradeDeletePopupComponent],
    entryComponents: [GradeComponent, GradeUpdateComponent, GradeDeleteDialogComponent, GradeDeletePopupComponent],
    providers: [GradeService, GradeResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieGradeModule {}

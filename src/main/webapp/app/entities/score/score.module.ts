import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    ScoreService,
    ScoreComponent,
    ScoreDetailComponent,
    ScoreUpdateComponent,
    ScoreDeletePopupComponent,
    ScoreDeleteDialogComponent,
    scoreRoute,
    scorePopupRoute,
    ScoreResolve
} from './';

const ENTITY_STATES = [...scoreRoute, ...scorePopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ScoreComponent, ScoreDetailComponent, ScoreUpdateComponent, ScoreDeleteDialogComponent, ScoreDeletePopupComponent],
    entryComponents: [ScoreComponent, ScoreUpdateComponent, ScoreDeleteDialogComponent, ScoreDeletePopupComponent],
    providers: [ScoreService, ScoreResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieScoreModule {}

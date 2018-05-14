import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EducationGenieSharedModule } from 'app/shared';
import {
    TopicService,
    TopicComponent,
    TopicDetailComponent,
    TopicUpdateComponent,
    TopicDeletePopupComponent,
    TopicDeleteDialogComponent,
    topicRoute,
    topicPopupRoute,
    TopicResolve
} from './';

const ENTITY_STATES = [...topicRoute, ...topicPopupRoute];

@NgModule({
    imports: [EducationGenieSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TopicComponent, TopicDetailComponent, TopicUpdateComponent, TopicDeleteDialogComponent, TopicDeletePopupComponent],
    entryComponents: [TopicComponent, TopicUpdateComponent, TopicDeleteDialogComponent, TopicDeletePopupComponent],
    providers: [TopicService, TopicResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieTopicModule {}

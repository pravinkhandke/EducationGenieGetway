import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EducationGenieContentModule } from './content/content.module';
import { EducationGenieContentListModule } from './content-list/content-list.module';
import { EducationGenieGradeModule } from './grade/grade.module';
import { EducationGenieSubjectModule } from './subject/subject.module';
import { EducationGenieChapterModule } from './chapter/chapter.module';
import { EducationGenieTopicModule } from './topic/topic.module';
import { EducationGenieUserInfoModule } from './user-info/user-info.module';
import { EducationGenieQuestionBankModule } from './question-bank/question-bank.module';
import { EducationGenieQuestionsModule } from './questions/questions.module';
import { EducationGenieScoreModule } from './score/score.module';
import { EducationGenieUserReviewModule } from './user-review/user-review.module';
import { EducationGenieContentReviewModule } from './content-review/content-review.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        EducationGenieContentModule,
        EducationGenieContentListModule,
        EducationGenieGradeModule,
        EducationGenieSubjectModule,
        EducationGenieChapterModule,
        EducationGenieTopicModule,
        EducationGenieUserInfoModule,
        EducationGenieQuestionBankModule,
        EducationGenieQuestionsModule,
        EducationGenieScoreModule,
        EducationGenieUserReviewModule,
        EducationGenieContentReviewModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EducationGenieEntityModule {}

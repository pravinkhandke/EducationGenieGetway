import { Moment } from 'moment';
import { IQuestions } from './questions.model';

export const enum State {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE',
    DELETED = 'DELETED'
}

export interface IQuestionBank {
    id?: number;
    title?: string;
    duration?: number;
    state?: State;
    createBy?: string;
    createdTime?: Moment;
    updatedBy?: string;
    updatedTime?: Moment;
    userInfoId?: number;
    scoreId?: number;
    subjectId?: number;
    gradeId?: number;
    chapterId?: number;
    topicId?: number;
    questions?: IQuestions[];
}

export class QuestionBank implements IQuestionBank {
    constructor(
        public id?: number,
        public title?: string,
        public duration?: number,
        public state?: State,
        public createBy?: string,
        public createdTime?: Moment,
        public updatedBy?: string,
        public updatedTime?: Moment,
        public userInfoId?: number,
        public scoreId?: number,
        public subjectId?: number,
        public gradeId?: number,
        public chapterId?: number,
        public topicId?: number,
        public questions?: IQuestions[]
    ) {}
}

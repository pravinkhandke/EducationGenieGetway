import { Moment } from 'moment';

export interface IQuestions {
    id?: number;
    options?: string;
    answers?: boolean;
    createBy?: string;
    createdTime?: Moment;
    updatedBy?: string;
    updatedTime?: Moment;
    questionBankId?: number;
}

export class Questions implements IQuestions {
    constructor(
        public id?: number,
        public options?: string,
        public answers?: boolean,
        public createBy?: string,
        public createdTime?: Moment,
        public updatedBy?: string,
        public updatedTime?: Moment,
        public questionBankId?: number
    ) {
        this.answers = false;
    }
}

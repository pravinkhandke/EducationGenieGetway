import { IContent } from './content.model';
import { IScore } from './score.model';
import { IQuestionBank } from './question-bank.model';

export interface IUserInfo {
    id?: number;
    userType?: string;
    userReviewId?: number;
    gradeId?: number;
    contents?: IContent[];
    scores?: IScore[];
    questionBanks?: IQuestionBank[];
}

export class UserInfo implements IUserInfo {
    constructor(
        public id?: number,
        public userType?: string,
        public userReviewId?: number,
        public gradeId?: number,
        public contents?: IContent[],
        public scores?: IScore[],
        public questionBanks?: IQuestionBank[]
    ) {}
}

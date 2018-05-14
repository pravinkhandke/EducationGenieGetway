import { Moment } from 'moment';

export interface IUserReview {
    id?: number;
    raiting?: number;
    review?: string;
    createBy?: string;
    createdTime?: Moment;
    updatedBy?: string;
    updatedTime?: Moment;
}

export class UserReview implements IUserReview {
    constructor(
        public id?: number,
        public raiting?: number,
        public review?: string,
        public createBy?: string,
        public createdTime?: Moment,
        public updatedBy?: string,
        public updatedTime?: Moment
    ) {}
}

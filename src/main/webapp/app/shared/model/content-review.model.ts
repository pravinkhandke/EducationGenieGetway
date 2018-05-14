import { Moment } from 'moment';

export interface IContentReview {
    id?: number;
    raiting?: number;
    review?: string;
    createBy?: string;
    createdTime?: Moment;
    updatedBy?: string;
    updatedTime?: Moment;
    contentId?: number;
}

export class ContentReview implements IContentReview {
    constructor(
        public id?: number,
        public raiting?: number,
        public review?: string,
        public createBy?: string,
        public createdTime?: Moment,
        public updatedBy?: string,
        public updatedTime?: Moment,
        public contentId?: number
    ) {}
}

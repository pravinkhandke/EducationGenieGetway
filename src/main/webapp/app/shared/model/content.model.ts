import { Moment } from 'moment';
import { IContentList } from './content-list.model';
import { IContentReview } from './content-review.model';
import { IGrade } from './grade.model';

export const enum Language {
    ENGLISH = 'ENGLISH',
    HINDI = 'HINDI'
}

export const enum State {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE',
    DELETED = 'DELETED'
}

export interface IContent {
    id?: number;
    contentType?: string;
    url?: string;
    mediaType?: string;
    language?: Language;
    state?: State;
    createBy?: string;
    createdTime?: Moment;
    updatedBy?: string;
    updatedTime?: Moment;
    subjectId?: number;
    chapterId?: number;
    topicId?: number;
    contentLists?: IContentList[];
    contentreviews?: IContentReview[];
    grades?: IGrade[];
    userInfoId?: number;
}

export class Content implements IContent {
    constructor(
        public id?: number,
        public contentType?: string,
        public url?: string,
        public mediaType?: string,
        public language?: Language,
        public state?: State,
        public createBy?: string,
        public createdTime?: Moment,
        public updatedBy?: string,
        public updatedTime?: Moment,
        public subjectId?: number,
        public chapterId?: number,
        public topicId?: number,
        public contentLists?: IContentList[],
        public contentreviews?: IContentReview[],
        public grades?: IGrade[],
        public userInfoId?: number
    ) {}
}

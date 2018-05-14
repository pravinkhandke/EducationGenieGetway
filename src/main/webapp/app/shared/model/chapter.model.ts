import { ITopic } from './topic.model';

export interface IChapter {
    id?: number;
    name?: string;
    subjectId?: number;
    topics?: ITopic[];
}

export class Chapter implements IChapter {
    constructor(public id?: number, public name?: string, public subjectId?: number, public topics?: ITopic[]) {}
}

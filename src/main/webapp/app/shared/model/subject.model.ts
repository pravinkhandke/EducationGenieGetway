import { IChapter } from './chapter.model';

export interface ISubject {
    id?: number;
    name?: string;
    gradeId?: number;
    chapters?: IChapter[];
}

export class Subject implements ISubject {
    constructor(public id?: number, public name?: string, public gradeId?: number, public chapters?: IChapter[]) {}
}

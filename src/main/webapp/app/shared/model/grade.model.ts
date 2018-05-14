import { ISubject } from './subject.model';
import { IContent } from './content.model';

export interface IGrade {
    id?: number;
    grade?: number;
    subjects?: ISubject[];
    contents?: IContent[];
}

export class Grade implements IGrade {
    constructor(public id?: number, public grade?: number, public subjects?: ISubject[], public contents?: IContent[]) {}
}

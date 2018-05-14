export interface ITopic {
    id?: number;
    name?: string;
    chapterId?: number;
}

export class Topic implements ITopic {
    constructor(public id?: number, public name?: string, public chapterId?: number) {}
}

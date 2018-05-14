export interface IContentList {
    id?: number;
    listTitle?: string;
    contentId?: number;
}

export class ContentList implements IContentList {
    constructor(public id?: number, public listTitle?: string, public contentId?: number) {}
}

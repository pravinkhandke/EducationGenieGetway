export interface IScore {
    id?: number;
    score?: number;
    userInfoId?: number;
}

export class Score implements IScore {
    constructor(public id?: number, public score?: number, public userInfoId?: number) {}
}

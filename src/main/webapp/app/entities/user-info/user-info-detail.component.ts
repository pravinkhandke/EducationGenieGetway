import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserInfo } from 'app/shared/model/user-info.model';

@Component({
    selector: 'jhi-user-info-detail',
    templateUrl: './user-info-detail.component.html'
})
export class UserInfoDetailComponent implements OnInit {
    userInfo: IUserInfo;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ userInfo }) => {
            this.userInfo = userInfo.body ? userInfo.body : userInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITopic } from 'app/shared/model/topic.model';

@Component({
    selector: 'jhi-topic-detail',
    templateUrl: './topic-detail.component.html'
})
export class TopicDetailComponent implements OnInit {
    topic: ITopic;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ topic }) => {
            this.topic = topic.body ? topic.body : topic;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentReview } from 'app/shared/model/content-review.model';
import { ContentReviewService } from './content-review.service';

@Component({
    selector: 'jhi-content-review-delete-dialog',
    templateUrl: './content-review-delete-dialog.component.html'
})
export class ContentReviewDeleteDialogComponent {
    contentReview: IContentReview;

    constructor(
        private contentReviewService: ContentReviewService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contentReviewService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contentReviewListModification',
                content: 'Deleted an contentReview'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-content-review-delete-popup',
    template: ''
})
export class ContentReviewDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ contentReview }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContentReviewDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contentReview = contentReview.body;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

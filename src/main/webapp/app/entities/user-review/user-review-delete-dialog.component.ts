import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserReview } from 'app/shared/model/user-review.model';
import { UserReviewService } from './user-review.service';

@Component({
    selector: 'jhi-user-review-delete-dialog',
    templateUrl: './user-review-delete-dialog.component.html'
})
export class UserReviewDeleteDialogComponent {
    userReview: IUserReview;

    constructor(private userReviewService: UserReviewService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userReviewService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userReviewListModification',
                content: 'Deleted an userReview'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-review-delete-popup',
    template: ''
})
export class UserReviewDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ userReview }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserReviewDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.userReview = userReview.body;
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

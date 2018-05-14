import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentList } from 'app/shared/model/content-list.model';
import { ContentListService } from './content-list.service';

@Component({
    selector: 'jhi-content-list-delete-dialog',
    templateUrl: './content-list-delete-dialog.component.html'
})
export class ContentListDeleteDialogComponent {
    contentList: IContentList;

    constructor(
        private contentListService: ContentListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contentListService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'contentListListModification',
                content: 'Deleted an contentList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-content-list-delete-popup',
    template: ''
})
export class ContentListDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ contentList }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ContentListDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.contentList = contentList.body;
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

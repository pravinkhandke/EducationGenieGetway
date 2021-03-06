import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionBank } from 'app/shared/model/question-bank.model';
import { QuestionBankService } from './question-bank.service';

@Component({
    selector: 'jhi-question-bank-delete-dialog',
    templateUrl: './question-bank-delete-dialog.component.html'
})
export class QuestionBankDeleteDialogComponent {
    questionBank: IQuestionBank;

    constructor(
        private questionBankService: QuestionBankService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.questionBankService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'questionBankListModification',
                content: 'Deleted an questionBank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-question-bank-delete-popup',
    template: ''
})
export class QuestionBankDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ questionBank }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuestionBankDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.questionBank = questionBank.body;
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

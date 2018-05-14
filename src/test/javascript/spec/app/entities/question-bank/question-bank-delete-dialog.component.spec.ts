/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EducationGenieTestModule } from '../../../test.module';
import { QuestionBankDeleteDialogComponent } from 'app/entities/question-bank/question-bank-delete-dialog.component';
import { QuestionBankService } from 'app/entities/question-bank/question-bank.service';

describe('Component Tests', () => {
    describe('QuestionBank Management Delete Component', () => {
        let comp: QuestionBankDeleteDialogComponent;
        let fixture: ComponentFixture<QuestionBankDeleteDialogComponent>;
        let service: QuestionBankService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [QuestionBankDeleteDialogComponent],
                providers: [QuestionBankService]
            })
                .overrideTemplate(QuestionBankDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuestionBankDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionBankService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});

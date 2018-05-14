/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentReviewDeleteDialogComponent } from 'app/entities/content-review/content-review-delete-dialog.component';
import { ContentReviewService } from 'app/entities/content-review/content-review.service';

describe('Component Tests', () => {
    describe('ContentReview Management Delete Component', () => {
        let comp: ContentReviewDeleteDialogComponent;
        let fixture: ComponentFixture<ContentReviewDeleteDialogComponent>;
        let service: ContentReviewService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentReviewDeleteDialogComponent],
                providers: [ContentReviewService]
            })
                .overrideTemplate(ContentReviewDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContentReviewDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentReviewService);
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentListDeleteDialogComponent } from 'app/entities/content-list/content-list-delete-dialog.component';
import { ContentListService } from 'app/entities/content-list/content-list.service';

describe('Component Tests', () => {
    describe('ContentList Management Delete Component', () => {
        let comp: ContentListDeleteDialogComponent;
        let fixture: ComponentFixture<ContentListDeleteDialogComponent>;
        let service: ContentListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentListDeleteDialogComponent],
                providers: [ContentListService]
            })
                .overrideTemplate(ContentListDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContentListDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentListService);
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

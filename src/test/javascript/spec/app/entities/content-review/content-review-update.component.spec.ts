/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentReviewUpdateComponent } from 'app/entities/content-review/content-review-update.component';
import { ContentReviewService } from 'app/entities/content-review/content-review.service';
import { ContentReview } from 'app/shared/model/content-review.model';

import { ContentService } from 'app/entities/content';

describe('Component Tests', () => {
    describe('ContentReview Management Update Component', () => {
        let comp: ContentReviewUpdateComponent;
        let fixture: ComponentFixture<ContentReviewUpdateComponent>;
        let service: ContentReviewService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentReviewUpdateComponent],
                providers: [ContentService, ContentReviewService]
            })
                .overrideTemplate(ContentReviewUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContentReviewUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentReviewService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContentReview(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.contentReview = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContentReview();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.contentReview = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

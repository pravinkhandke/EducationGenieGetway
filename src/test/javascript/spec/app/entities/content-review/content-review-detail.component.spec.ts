/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentReviewDetailComponent } from 'app/entities/content-review/content-review-detail.component';
import { ContentReview } from 'app/shared/model/content-review.model';

describe('Component Tests', () => {
    describe('ContentReview Management Detail Component', () => {
        let comp: ContentReviewDetailComponent;
        let fixture: ComponentFixture<ContentReviewDetailComponent>;
        const route = ({ data: of({ contentReview: new ContentReview(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentReviewDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContentReviewDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContentReviewDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contentReview).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

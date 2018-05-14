/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentListDetailComponent } from 'app/entities/content-list/content-list-detail.component';
import { ContentList } from 'app/shared/model/content-list.model';

describe('Component Tests', () => {
    describe('ContentList Management Detail Component', () => {
        let comp: ContentListDetailComponent;
        let fixture: ComponentFixture<ContentListDetailComponent>;
        const route = ({ data: of({ contentList: new ContentList(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentListDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ContentListDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ContentListDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.contentList).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

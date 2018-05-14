/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentListComponent } from 'app/entities/content-list/content-list.component';
import { ContentListService } from 'app/entities/content-list/content-list.service';
import { ContentList } from 'app/shared/model/content-list.model';

describe('Component Tests', () => {
    describe('ContentList Management Component', () => {
        let comp: ContentListComponent;
        let fixture: ComponentFixture<ContentListComponent>;
        let service: ContentListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentListComponent],
                providers: [ContentListService]
            })
                .overrideTemplate(ContentListComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContentListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentListService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new ContentList(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.contentLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

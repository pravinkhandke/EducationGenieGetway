/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EducationGenieTestModule } from '../../../test.module';
import { TopicComponent } from 'app/entities/topic/topic.component';
import { TopicService } from 'app/entities/topic/topic.service';
import { Topic } from 'app/shared/model/topic.model';

describe('Component Tests', () => {
    describe('Topic Management Component', () => {
        let comp: TopicComponent;
        let fixture: ComponentFixture<TopicComponent>;
        let service: TopicService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [TopicComponent],
                providers: [TopicService]
            })
                .overrideTemplate(TopicComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TopicComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopicService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new Topic(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.topics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EducationGenieTestModule } from '../../../test.module';
import { SubjectComponent } from 'app/entities/subject/subject.component';
import { SubjectService } from 'app/entities/subject/subject.service';
import { Subject } from 'app/shared/model/subject.model';

describe('Component Tests', () => {
    describe('Subject Management Component', () => {
        let comp: SubjectComponent;
        let fixture: ComponentFixture<SubjectComponent>;
        let service: SubjectService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [SubjectComponent],
                providers: [SubjectService]
            })
                .overrideTemplate(SubjectComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SubjectComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubjectService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new Subject(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.subjects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentUpdateComponent } from 'app/entities/content/content-update.component';
import { ContentService } from 'app/entities/content/content.service';
import { Content } from 'app/shared/model/content.model';

import { SubjectService } from 'app/entities/subject';
import { ChapterService } from 'app/entities/chapter';
import { TopicService } from 'app/entities/topic';
import { GradeService } from 'app/entities/grade';
import { UserInfoService } from 'app/entities/user-info';

describe('Component Tests', () => {
    describe('Content Management Update Component', () => {
        let comp: ContentUpdateComponent;
        let fixture: ComponentFixture<ContentUpdateComponent>;
        let service: ContentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentUpdateComponent],
                providers: [SubjectService, ChapterService, TopicService, GradeService, UserInfoService, ContentService]
            })
                .overrideTemplate(ContentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Content(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.content = entity;
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
                    const entity = new Content();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.content = entity;
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

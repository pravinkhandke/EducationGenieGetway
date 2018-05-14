/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EducationGenieTestModule } from '../../../test.module';
import { ChapterUpdateComponent } from 'app/entities/chapter/chapter-update.component';
import { ChapterService } from 'app/entities/chapter/chapter.service';
import { Chapter } from 'app/shared/model/chapter.model';

import { SubjectService } from 'app/entities/subject';

describe('Component Tests', () => {
    describe('Chapter Management Update Component', () => {
        let comp: ChapterUpdateComponent;
        let fixture: ComponentFixture<ChapterUpdateComponent>;
        let service: ChapterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ChapterUpdateComponent],
                providers: [SubjectService, ChapterService]
            })
                .overrideTemplate(ChapterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChapterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChapterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Chapter(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.chapter = entity;
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
                    const entity = new Chapter();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.chapter = entity;
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

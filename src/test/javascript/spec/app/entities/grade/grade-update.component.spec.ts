/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EducationGenieTestModule } from '../../../test.module';
import { GradeUpdateComponent } from 'app/entities/grade/grade-update.component';
import { GradeService } from 'app/entities/grade/grade.service';
import { Grade } from 'app/shared/model/grade.model';

import { ContentService } from 'app/entities/content';

describe('Component Tests', () => {
    describe('Grade Management Update Component', () => {
        let comp: GradeUpdateComponent;
        let fixture: ComponentFixture<GradeUpdateComponent>;
        let service: GradeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [GradeUpdateComponent],
                providers: [ContentService, GradeService]
            })
                .overrideTemplate(GradeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GradeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GradeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Grade(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.grade = entity;
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
                    const entity = new Grade();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.grade = entity;
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

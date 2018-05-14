/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EducationGenieTestModule } from '../../../test.module';
import { QuestionsUpdateComponent } from 'app/entities/questions/questions-update.component';
import { QuestionsService } from 'app/entities/questions/questions.service';
import { Questions } from 'app/shared/model/questions.model';

import { QuestionBankService } from 'app/entities/question-bank';

describe('Component Tests', () => {
    describe('Questions Management Update Component', () => {
        let comp: QuestionsUpdateComponent;
        let fixture: ComponentFixture<QuestionsUpdateComponent>;
        let service: QuestionsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [QuestionsUpdateComponent],
                providers: [QuestionBankService, QuestionsService]
            })
                .overrideTemplate(QuestionsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuestionsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuestionsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Questions(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.questions = entity;
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
                    const entity = new Questions();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.questions = entity;
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

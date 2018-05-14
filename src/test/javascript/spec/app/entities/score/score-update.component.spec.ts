/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EducationGenieTestModule } from '../../../test.module';
import { ScoreUpdateComponent } from 'app/entities/score/score-update.component';
import { ScoreService } from 'app/entities/score/score.service';
import { Score } from 'app/shared/model/score.model';

import { UserInfoService } from 'app/entities/user-info';

describe('Component Tests', () => {
    describe('Score Management Update Component', () => {
        let comp: ScoreUpdateComponent;
        let fixture: ComponentFixture<ScoreUpdateComponent>;
        let service: ScoreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ScoreUpdateComponent],
                providers: [UserInfoService, ScoreService]
            })
                .overrideTemplate(ScoreUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ScoreUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScoreService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Score(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.score = entity;
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
                    const entity = new Score();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.score = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EducationGenieTestModule } from '../../../test.module';
import { ContentListUpdateComponent } from 'app/entities/content-list/content-list-update.component';
import { ContentListService } from 'app/entities/content-list/content-list.service';
import { ContentList } from 'app/shared/model/content-list.model';

import { ContentService } from 'app/entities/content';

describe('Component Tests', () => {
    describe('ContentList Management Update Component', () => {
        let comp: ContentListUpdateComponent;
        let fixture: ComponentFixture<ContentListUpdateComponent>;
        let service: ContentListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EducationGenieTestModule],
                declarations: [ContentListUpdateComponent],
                providers: [ContentService, ContentListService]
            })
                .overrideTemplate(ContentListUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ContentListUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentListService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ContentList(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.contentList = entity;
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
                    const entity = new ContentList();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.contentList = entity;
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

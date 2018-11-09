/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ExerciseTestModule } from '../../../test.module';
import { UserSalesStatsUpdateComponent } from 'app/entities/user-sales-stats/user-sales-stats-update.component';
import { UserSalesStatsService } from 'app/entities/user-sales-stats/user-sales-stats.service';
import { UserSalesStats } from 'app/shared/model/user-sales-stats.model';

describe('Component Tests', () => {
    describe('UserSalesStats Management Update Component', () => {
        let comp: UserSalesStatsUpdateComponent;
        let fixture: ComponentFixture<UserSalesStatsUpdateComponent>;
        let service: UserSalesStatsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ExerciseTestModule],
                declarations: [UserSalesStatsUpdateComponent]
            })
                .overrideTemplate(UserSalesStatsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserSalesStatsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserSalesStatsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserSalesStats(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userSalesStats = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserSalesStats();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userSalesStats = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

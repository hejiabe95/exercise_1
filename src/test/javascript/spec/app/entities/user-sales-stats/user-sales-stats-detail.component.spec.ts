/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExerciseTestModule } from '../../../test.module';
import { UserSalesStatsDetailComponent } from 'app/entities/user-sales-stats/user-sales-stats-detail.component';
import { UserSalesStats } from 'app/shared/model/user-sales-stats.model';

describe('Component Tests', () => {
    describe('UserSalesStats Management Detail Component', () => {
        let comp: UserSalesStatsDetailComponent;
        let fixture: ComponentFixture<UserSalesStatsDetailComponent>;
        const route = ({ data: of({ userSalesStats: new UserSalesStats(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ExerciseTestModule],
                declarations: [UserSalesStatsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserSalesStatsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserSalesStatsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userSalesStats).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

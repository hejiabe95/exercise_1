/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ExerciseTestModule } from '../../../test.module';
import { UserSalesStatsDeleteDialogComponent } from 'app/entities/user-sales-stats/user-sales-stats-delete-dialog.component';
import { UserSalesStatsService } from 'app/entities/user-sales-stats/user-sales-stats.service';

describe('Component Tests', () => {
    describe('UserSalesStats Management Delete Component', () => {
        let comp: UserSalesStatsDeleteDialogComponent;
        let fixture: ComponentFixture<UserSalesStatsDeleteDialogComponent>;
        let service: UserSalesStatsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ExerciseTestModule],
                declarations: [UserSalesStatsDeleteDialogComponent]
            })
                .overrideTemplate(UserSalesStatsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserSalesStatsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserSalesStatsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

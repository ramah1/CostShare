/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CostshareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserCostCsDetailComponent } from '../../../../../../main/webapp/app/entities/user-cost/user-cost-cs-detail.component';
import { UserCostCsService } from '../../../../../../main/webapp/app/entities/user-cost/user-cost-cs.service';
import { UserCostCs } from '../../../../../../main/webapp/app/entities/user-cost/user-cost-cs.model';

describe('Component Tests', () => {

    describe('UserCostCs Management Detail Component', () => {
        let comp: UserCostCsDetailComponent;
        let fixture: ComponentFixture<UserCostCsDetailComponent>;
        let service: UserCostCsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CostshareTestModule],
                declarations: [UserCostCsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserCostCsService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserCostCsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserCostCsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserCostCsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserCostCs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userCost).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

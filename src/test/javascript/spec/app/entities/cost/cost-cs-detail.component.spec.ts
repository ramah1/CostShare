/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CostshareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CostCsDetailComponent } from '../../../../../../main/webapp/app/entities/cost/cost-cs-detail.component';
import { CostCsService } from '../../../../../../main/webapp/app/entities/cost/cost-cs.service';
import { CostCs } from '../../../../../../main/webapp/app/entities/cost/cost-cs.model';

describe('Component Tests', () => {

    describe('CostCs Management Detail Component', () => {
        let comp: CostCsDetailComponent;
        let fixture: ComponentFixture<CostCsDetailComponent>;
        let service: CostCsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CostshareTestModule],
                declarations: [CostCsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CostCsService,
                    JhiEventManager
                ]
            }).overrideTemplate(CostCsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostCsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostCsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CostCs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cost).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

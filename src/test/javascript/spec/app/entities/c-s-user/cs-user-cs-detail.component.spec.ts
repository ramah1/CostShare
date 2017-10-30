/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CostshareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CSUserCsDetailComponent } from '../../../../../../main/webapp/app/entities/c-s-user/cs-user-cs-detail.component';
import { CSUserCsService } from '../../../../../../main/webapp/app/entities/c-s-user/cs-user-cs.service';
import { CSUserCs } from '../../../../../../main/webapp/app/entities/c-s-user/cs-user-cs.model';

describe('Component Tests', () => {

    describe('CSUserCs Management Detail Component', () => {
        let comp: CSUserCsDetailComponent;
        let fixture: ComponentFixture<CSUserCsDetailComponent>;
        let service: CSUserCsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CostshareTestModule],
                declarations: [CSUserCsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CSUserCsService,
                    JhiEventManager
                ]
            }).overrideTemplate(CSUserCsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CSUserCsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CSUserCsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CSUserCs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cSUser).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

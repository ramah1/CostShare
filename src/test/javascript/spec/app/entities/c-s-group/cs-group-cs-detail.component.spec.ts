/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CostshareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CSGroupCsDetailComponent } from '../../../../../../main/webapp/app/entities/c-s-group/cs-group-cs-detail.component';
import { CSGroupCsService } from '../../../../../../main/webapp/app/entities/c-s-group/cs-group-cs.service';
import { CSGroupCs } from '../../../../../../main/webapp/app/entities/c-s-group/cs-group-cs.model';

describe('Component Tests', () => {

    describe('CSGroupCs Management Detail Component', () => {
        let comp: CSGroupCsDetailComponent;
        let fixture: ComponentFixture<CSGroupCsDetailComponent>;
        let service: CSGroupCsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CostshareTestModule],
                declarations: [CSGroupCsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CSGroupCsService,
                    JhiEventManager
                ]
            }).overrideTemplate(CSGroupCsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CSGroupCsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CSGroupCsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CSGroupCs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cSGroup).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

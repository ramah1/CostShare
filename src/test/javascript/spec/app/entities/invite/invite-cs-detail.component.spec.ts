/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CostshareTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InviteCsDetailComponent } from '../../../../../../main/webapp/app/entities/invite/invite-cs-detail.component';
import { InviteCsService } from '../../../../../../main/webapp/app/entities/invite/invite-cs.service';
import { InviteCs } from '../../../../../../main/webapp/app/entities/invite/invite-cs.model';

describe('Component Tests', () => {

    describe('InviteCs Management Detail Component', () => {
        let comp: InviteCsDetailComponent;
        let fixture: ComponentFixture<InviteCsDetailComponent>;
        let service: InviteCsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CostshareTestModule],
                declarations: [InviteCsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InviteCsService,
                    JhiEventManager
                ]
            }).overrideTemplate(InviteCsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InviteCsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InviteCsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new InviteCs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.invite).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});

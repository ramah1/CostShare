import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserCostCs } from './user-cost-cs.model';
import { UserCostCsPopupService } from './user-cost-cs-popup.service';
import { UserCostCsService } from './user-cost-cs.service';
import { CostCs, CostCsService } from '../cost';
import { CSUserCs, CSUserCsService } from '../c-s-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-cost-cs-dialog',
    templateUrl: './user-cost-cs-dialog.component.html'
})
export class UserCostCsDialogComponent implements OnInit {

    userCost: UserCostCs;
    isSaving: boolean;

    costs: CostCs[];

    csusers: CSUserCs[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userCostService: UserCostCsService,
        private costService: CostCsService,
        private cSUserService: CSUserCsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.costService.query()
            .subscribe((res: ResponseWrapper) => { this.costs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.cSUserService.query()
            .subscribe((res: ResponseWrapper) => { this.csusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userCost.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userCostService.update(this.userCost));
        } else {
            this.subscribeToSaveResponse(
                this.userCostService.create(this.userCost));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserCostCs>) {
        result.subscribe((res: UserCostCs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserCostCs) {
        this.eventManager.broadcast({ name: 'userCostListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCostById(index: number, item: CostCs) {
        return item.id;
    }

    trackCSUserById(index: number, item: CSUserCs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-cost-cs-popup',
    template: ''
})
export class UserCostCsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userCostPopupService: UserCostCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userCostPopupService
                    .open(UserCostCsDialogComponent as Component, params['id']);
            } else {
                this.userCostPopupService
                    .open(UserCostCsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CostCs } from './cost-cs.model';
import { CostCsPopupService } from './cost-cs-popup.service';
import { CostCsService } from './cost-cs.service';
import { CSGroupCs, CSGroupCsService } from '../c-s-group';
import { ResponseWrapper } from '../../shared';
import {Principal} from "../../shared/auth/principal.service";
import {CSUserCsService} from "../c-s-user/cs-user-cs.service";
import {CSUserCs} from "../c-s-user/cs-user-cs.model";

@Component({
    selector: 'jhi-cost-cs-dialog',
    templateUrl: './cost-cs-dialog.component.html'
})
export class CostCsDialogComponent implements OnInit {

    cost: CostCs;
    isSaving: boolean;
    currentAccount: any;

    csgroups: CSGroupCs[];
    csusers: CSUserCs [];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private costService: CostCsService,
        private cSGroupService: CSGroupCsService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private userService: CSUserCsService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cSGroupService.query()
            .subscribe((res: ResponseWrapper) => { this.csgroups = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        //this.principal.identity().then((account) => {
          //  this.currentAccount = account;
        //});
        //this.userService.find(this.currentAccount.id);
        this.userService.query().subscribe((res: ResponseWrapper) => { this.csusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cost.id !== undefined) {
            this.subscribeToSaveResponse(
                //this.cost.paidBies.push(this.currentAccount.id);
                this.costService.update(this.cost));
        } else {
            this.subscribeToSaveResponse(
                this.costService.create(this.cost));
        }
    }

    private subscribeToSaveResponse(result: Observable<CostCs>) {
        result.subscribe((res: CostCs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CostCs) {
        this.eventManager.broadcast({ name: 'costListModification', content: 'OK'});
        this.isSaving = false;
        window.location.reload();
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCSGroupById(index: number, item: CSGroupCs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cost-cs-popup',
    template: ''
})
export class CostCsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costPopupService: CostCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.costPopupService
                    .open(CostCsDialogComponent as Component, params['id']);
            } else {
                this.costPopupService
                    .open(CostCsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

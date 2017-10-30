import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CSUserCs } from './cs-user-cs.model';
import { CSUserCsPopupService } from './cs-user-cs-popup.service';
import { CSUserCsService } from './cs-user-cs.service';
import { User, UserService } from '../../shared';
import { CostCs, CostCsService } from '../cost';
import { CSGroupCs, CSGroupCsService } from '../c-s-group';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cs-user-cs-dialog',
    templateUrl: './cs-user-cs-dialog.component.html'
})
export class CSUserCsDialogComponent implements OnInit {

    cSUser: CSUserCs;
    isSaving: boolean;

    users: User[];

    costs: CostCs[];

    csgroups: CSGroupCs[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cSUserService: CSUserCsService,
        private userService: UserService,
        private costService: CostCsService,
        private cSGroupService: CSGroupCsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.costService.query()
            .subscribe((res: ResponseWrapper) => { this.costs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.cSGroupService.query()
            .subscribe((res: ResponseWrapper) => { this.csgroups = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cSUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cSUserService.update(this.cSUser));
        } else {
            this.subscribeToSaveResponse(
                this.cSUserService.create(this.cSUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<CSUserCs>) {
        result.subscribe((res: CSUserCs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CSUserCs) {
        this.eventManager.broadcast({ name: 'cSUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCostById(index: number, item: CostCs) {
        return item.id;
    }

    trackCSGroupById(index: number, item: CSGroupCs) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-cs-user-cs-popup',
    template: ''
})
export class CSUserCsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cSUserPopupService: CSUserCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cSUserPopupService
                    .open(CSUserCsDialogComponent as Component, params['id']);
            } else {
                this.cSUserPopupService
                    .open(CSUserCsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

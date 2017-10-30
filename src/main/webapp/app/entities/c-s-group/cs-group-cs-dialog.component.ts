import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CSGroupCs } from './cs-group-cs.model';
import { CSGroupCsPopupService } from './cs-group-cs-popup.service';
import { CSGroupCsService } from './cs-group-cs.service';
import { CSUserCs, CSUserCsService } from '../c-s-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cs-group-cs-dialog',
    templateUrl: './cs-group-cs-dialog.component.html'
})
export class CSGroupCsDialogComponent implements OnInit {

    cSGroup: CSGroupCs;
    isSaving: boolean;

    csusers: CSUserCs[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cSGroupService: CSGroupCsService,
        private cSUserService: CSUserCsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cSUserService.query()
            .subscribe((res: ResponseWrapper) => { this.csusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cSGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cSGroupService.update(this.cSGroup));
        } else {
            this.subscribeToSaveResponse(
                this.cSGroupService.create(this.cSGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<CSGroupCs>) {
        result.subscribe((res: CSGroupCs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CSGroupCs) {
        this.eventManager.broadcast({ name: 'cSGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCSUserById(index: number, item: CSUserCs) {
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
    selector: 'jhi-cs-group-cs-popup',
    template: ''
})
export class CSGroupCsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cSGroupPopupService: CSGroupCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cSGroupPopupService
                    .open(CSGroupCsDialogComponent as Component, params['id']);
            } else {
                this.cSGroupPopupService
                    .open(CSGroupCsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { InviteCs } from './invite-cs.model';
import { InviteCsPopupService } from './invite-cs-popup.service';
import { InviteCsService } from './invite-cs.service';
import { CSGroupCs, CSGroupCsService } from '../c-s-group';
import { CSUserCs, CSUserCsService } from '../c-s-user';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-invite-cs-dialog',
    templateUrl: './invite-cs-dialog.component.html'
})
export class InviteCsDialogComponent implements OnInit {

    invite: InviteCs;
    isSaving: boolean;

    csgroups: CSGroupCs[];

    csusers: CSUserCs[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private inviteService: InviteCsService,
        private cSGroupService: CSGroupCsService,
        private cSUserService: CSUserCsService,
        private eventManager: JhiEventManager,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cSGroupService.query()
            .subscribe((res: ResponseWrapper) => { this.csgroups = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.cSUserService.query()
            .subscribe((res: ResponseWrapper) => { this.csusers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.cSUserService.findCurrentUser().subscribe(res => this.invite.sentFromId = res.id);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.invite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inviteService.update(this.invite));
        } else {
            this.subscribeToSaveResponse(
                this.inviteService.create(this.invite));
        }
    }

    private subscribeToSaveResponse(result: Observable<InviteCs>) {
        result.subscribe((res: InviteCs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InviteCs) {
        this.eventManager.broadcast({ name: 'inviteListModification', content: 'OK'});
        this.isSaving = false;
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

    trackCSUserById(index: number, item: CSUserCs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-invite-cs-popup',
    template: ''
})
export class InviteCsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invitePopupService: InviteCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.invitePopupService
                    .open(InviteCsDialogComponent as Component, params['id']);
            } else {
                this.invitePopupService
                    .open(InviteCsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

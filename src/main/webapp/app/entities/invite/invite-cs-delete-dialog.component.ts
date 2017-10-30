import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InviteCs } from './invite-cs.model';
import { InviteCsPopupService } from './invite-cs-popup.service';
import { InviteCsService } from './invite-cs.service';

@Component({
    selector: 'jhi-invite-cs-delete-dialog',
    templateUrl: './invite-cs-delete-dialog.component.html'
})
export class InviteCsDeleteDialogComponent {

    invite: InviteCs;

    constructor(
        private inviteService: InviteCsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inviteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inviteListModification',
                content: 'Deleted an invite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-invite-cs-delete-popup',
    template: ''
})
export class InviteCsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private invitePopupService: InviteCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.invitePopupService
                .open(InviteCsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

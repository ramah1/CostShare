import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CSUserCs } from './cs-user-cs.model';
import { CSUserCsPopupService } from './cs-user-cs-popup.service';
import { CSUserCsService } from './cs-user-cs.service';

@Component({
    selector: 'jhi-cs-user-cs-delete-dialog',
    templateUrl: './cs-user-cs-delete-dialog.component.html'
})
export class CSUserCsDeleteDialogComponent {

    cSUser: CSUserCs;

    constructor(
        private cSUserService: CSUserCsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cSUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cSUserListModification',
                content: 'Deleted an cSUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cs-user-cs-delete-popup',
    template: ''
})
export class CSUserCsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cSUserPopupService: CSUserCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cSUserPopupService
                .open(CSUserCsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CSGroupCs } from './cs-group-cs.model';
import { CSGroupCsPopupService } from './cs-group-cs-popup.service';
import { CSGroupCsService } from './cs-group-cs.service';

@Component({
    selector: 'jhi-cs-group-cs-delete-dialog',
    templateUrl: './cs-group-cs-delete-dialog.component.html'
})
export class CSGroupCsDeleteDialogComponent {

    cSGroup: CSGroupCs;

    constructor(
        private cSGroupService: CSGroupCsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cSGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cSGroupListModification',
                content: 'Deleted an cSGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cs-group-cs-delete-popup',
    template: ''
})
export class CSGroupCsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cSGroupPopupService: CSGroupCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cSGroupPopupService
                .open(CSGroupCsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CostCs } from './cost-cs.model';
import { CostCsPopupService } from './cost-cs-popup.service';
import { CostCsService } from './cost-cs.service';

@Component({
    selector: 'jhi-cost-cs-delete-dialog',
    templateUrl: './cost-cs-delete-dialog.component.html'
})
export class CostCsDeleteDialogComponent {

    cost: CostCs;

    constructor(
        private costService: CostCsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.costService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'costListModification',
                content: 'Deleted an cost'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cost-cs-delete-popup',
    template: ''
})
export class CostCsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costPopupService: CostCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.costPopupService
                .open(CostCsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

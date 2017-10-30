import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserCostCs } from './user-cost-cs.model';
import { UserCostCsPopupService } from './user-cost-cs-popup.service';
import { UserCostCsService } from './user-cost-cs.service';

@Component({
    selector: 'jhi-user-cost-cs-delete-dialog',
    templateUrl: './user-cost-cs-delete-dialog.component.html'
})
export class UserCostCsDeleteDialogComponent {

    userCost: UserCostCs;

    constructor(
        private userCostService: UserCostCsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userCostService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userCostListModification',
                content: 'Deleted an userCost'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-cost-cs-delete-popup',
    template: ''
})
export class UserCostCsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userCostPopupService: UserCostCsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userCostPopupService
                .open(UserCostCsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

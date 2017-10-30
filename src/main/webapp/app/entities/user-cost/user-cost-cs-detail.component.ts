import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UserCostCs } from './user-cost-cs.model';
import { UserCostCsService } from './user-cost-cs.service';

@Component({
    selector: 'jhi-user-cost-cs-detail',
    templateUrl: './user-cost-cs-detail.component.html'
})
export class UserCostCsDetailComponent implements OnInit, OnDestroy {

    userCost: UserCostCs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userCostService: UserCostCsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserCosts();
    }

    load(id) {
        this.userCostService.find(id).subscribe((userCost) => {
            this.userCost = userCost;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserCosts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userCostListModification',
            (response) => this.load(this.userCost.id)
        );
    }
}

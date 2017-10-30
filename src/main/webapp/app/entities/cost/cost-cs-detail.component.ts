import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CostCs } from './cost-cs.model';
import { CostCsService } from './cost-cs.service';

@Component({
    selector: 'jhi-cost-cs-detail',
    templateUrl: './cost-cs-detail.component.html'
})
export class CostCsDetailComponent implements OnInit, OnDestroy {

    cost: CostCs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private costService: CostCsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCosts();
    }

    load(id) {
        this.costService.find(id).subscribe((cost) => {
            this.cost = cost;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCosts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'costListModification',
            (response) => this.load(this.cost.id)
        );
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { CostCs } from './cost-cs.model';
import { CostCsService } from './cost-cs.service';
import {UserCostCs} from "../user-cost/user-cost-cs.model";
import {UserCostCsService} from "../user-cost/user-cost-cs.service";
import {ResponseWrapper} from "../../shared/model/response-wrapper.model";

@Component({
    selector: 'jhi-cost-cs-detail',
    templateUrl: './cost-cs-detail.component.html'
})
export class CostCsDetailComponent implements OnInit, OnDestroy {

    cost: CostCs;
    userCosts: UserCostCs[];
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private costService: CostCsService,
        private route: ActivatedRoute,
        private userCostService: UserCostCsService,
        private jhiAlertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(parseInt(params['id']));
        });
        this.registerChangeInCosts();
    }

    load(id) {
        this.costService.find(id).subscribe((cost) => {
            this.cost = cost;
            this.userCostService.findByCostId(this.cost.id).subscribe((res) =>
               /* (res: ResponseWrapper) => this.onSuccess(res.json),
                (res: ResponseWrapper) => this.onError(res.json)*/
               this.userCosts = res.json());
        });
    }

    private onSuccess(data) {
        for (let i = 0; i < data.length; i++) {
            this.userCosts.push(data[i]);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
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

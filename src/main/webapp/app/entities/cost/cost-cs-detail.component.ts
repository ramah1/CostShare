import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {CostCs} from './cost-cs.model';
import {CostCsService} from './cost-cs.service';
import {UserCostCs} from "../user-cost/user-cost-cs.model";
import {UserCostCsService} from "../user-cost/user-cost-cs.service";
import {ResponseWrapper} from "../../shared/model/response-wrapper.model";
import {User} from "../../shared/user/user.model";
import {CSUserCs} from "../c-s-user/cs-user-cs.model";
import {CSUserCsService} from "../c-s-user/cs-user-cs.service";
import {AccountService} from "../../shared/auth/account.service";
import {Principal} from "../../shared/auth/principal.service";

@Component({
    selector: 'jhi-cost-cs-detail',
    templateUrl: './cost-cs-detail.component.html'
})
export class CostCsDetailComponent implements OnInit, OnDestroy {

    cost: CostCs;
    userCosts: UserCostCs[] = [];
    currentAccount: any;
    myParts = [];
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(private eventManager: JhiEventManager,
                private costService: CostCsService,
                private route: ActivatedRoute,
                private userCostService: UserCostCsService,
                private jhiAlertService: JhiAlertService,
                private principal: Principal) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCosts();
    }

    load(id) {
        this.costService.find(id).subscribe((cost) => {
            this.cost = cost;
            this.userCostService.findByCostId(this.cost.id).subscribe(
                (res: ResponseWrapper) => {
                    this.onSuccess(res.json);
                },
                (res: ResponseWrapper) => this.onError(res.json));
        });
    }

    private onSuccess(data) {
        let dataLength = data.length;
        let multipliers: number = 0;
        for (let i = 0; i < dataLength; i++) {
            this.userCosts.push(data[i]);
            multipliers += parseInt(data[i].multiplier);
        }
        for(let i = 0; i < dataLength; i++){
            this.myParts.push((this.cost.sum * (parseInt(data[i].multiplier) / multipliers)));
            this.userCosts[i].myPart = Math.round(this.myParts[i]);
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

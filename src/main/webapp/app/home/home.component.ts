import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';
/*import {CSGroupCsComponent} from '../entities/c-s-group';*/

import {Account, LoginModalService, Principal, ResponseWrapper,} from '../shared';
import {CostCs} from "../entities/cost/cost-cs.model";
import {CostCsService} from "../entities/cost/cost-cs.service";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    costs: CostCs[];
    predicate: any;
    reverse: any;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private costService: CostCsService,
        private jhiAlertService: JhiAlertService,
    ) {
        this.costs = [];
        this.predicate = 'id';
        this.reverse = true;
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    trackId(index: number, item: CostCs) {
        return item.id;
    }

    private onSuccess(data, headers) {
        data.forEach(result => {
            if (result.paidByName === this.account.login) {
                this.costs.push(result);
            }
        });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    loadAll() {
        this.costService.query({
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }


    login() {
        this.modalRef = this.loginModalService.open();
    }
}

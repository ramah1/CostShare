import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { CSUserCs } from './cs-user-cs.model';
import { CSUserCsService } from './cs-user-cs.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cs-user-cs',
    templateUrl: './cs-user-cs.component.html'
})
export class CSUserCsComponent implements OnInit, OnDestroy {
cSUsers: CSUserCs[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cSUserService: CSUserCsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.cSUserService.query().subscribe(
            (res: ResponseWrapper) => {
                this.cSUsers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCSUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CSUserCs) {
        return item.id;
    }
    registerChangeInCSUsers() {
        this.eventSubscriber = this.eventManager.subscribe('cSUserListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

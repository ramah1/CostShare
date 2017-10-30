import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { InviteCs } from './invite-cs.model';
import { InviteCsService } from './invite-cs.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-invite-cs',
    templateUrl: './invite-cs.component.html'
})
export class InviteCsComponent implements OnInit, OnDestroy {
invites: InviteCs[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inviteService: InviteCsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inviteService.query().subscribe(
            (res: ResponseWrapper) => {
                this.invites = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInvites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: InviteCs) {
        return item.id;
    }
    registerChangeInInvites() {
        this.eventSubscriber = this.eventManager.subscribe('inviteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

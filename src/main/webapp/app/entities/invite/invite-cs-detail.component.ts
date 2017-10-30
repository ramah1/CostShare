import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InviteCs } from './invite-cs.model';
import { InviteCsService } from './invite-cs.service';

@Component({
    selector: 'jhi-invite-cs-detail',
    templateUrl: './invite-cs-detail.component.html'
})
export class InviteCsDetailComponent implements OnInit, OnDestroy {

    invite: InviteCs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inviteService: InviteCsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInvites();
    }

    load(id) {
        this.inviteService.find(id).subscribe((invite) => {
            this.invite = invite;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInvites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inviteListModification',
            (response) => this.load(this.invite.id)
        );
    }
}

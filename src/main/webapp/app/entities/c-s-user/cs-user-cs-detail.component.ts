import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CSUserCs } from './cs-user-cs.model';
import { CSUserCsService } from './cs-user-cs.service';

@Component({
    selector: 'jhi-cs-user-cs-detail',
    templateUrl: './cs-user-cs-detail.component.html'
})
export class CSUserCsDetailComponent implements OnInit, OnDestroy {

    cSUser: CSUserCs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cSUserService: CSUserCsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCSUsers();
    }

    load(id) {
        this.cSUserService.find(id).subscribe((cSUser) => {
            this.cSUser = cSUser;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCSUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cSUserListModification',
            (response) => this.load(this.cSUser.id)
        );
    }
}

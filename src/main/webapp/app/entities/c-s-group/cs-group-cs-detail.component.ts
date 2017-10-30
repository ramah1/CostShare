import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CSGroupCs } from './cs-group-cs.model';
import { CSGroupCsService } from './cs-group-cs.service';

@Component({
    selector: 'jhi-cs-group-cs-detail',
    templateUrl: './cs-group-cs-detail.component.html'
})
export class CSGroupCsDetailComponent implements OnInit, OnDestroy {

    cSGroup: CSGroupCs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cSGroupService: CSGroupCsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCSGroups();
    }

    load(id) {
        this.cSGroupService.find(id).subscribe((cSGroup) => {
            this.cSGroup = cSGroup;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCSGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cSGroupListModification',
            (response) => this.load(this.cSGroup.id)
        );
    }
}

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CSGroupCsComponent } from './cs-group-cs.component';
import { CSGroupCsDetailComponent } from './cs-group-cs-detail.component';
import { CSGroupCsPopupComponent } from './cs-group-cs-dialog.component';
import { CSGroupCsDeletePopupComponent } from './cs-group-cs-delete-dialog.component';

export const cSGroupRoute: Routes = [
    {
        path: 'cs-group-cs',
        component: CSGroupCsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cs-group-cs/:id',
        component: CSGroupCsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cSGroupPopupRoute: Routes = [
    {
        path: 'cs-group-cs-new',
        component: CSGroupCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cs-group-cs/:id/edit',
        component: CSGroupCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cs-group-cs/:id/delete',
        component: CSGroupCsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cSGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

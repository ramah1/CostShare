import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CostCsComponent } from './cost-cs.component';
import { CostCsDetailComponent } from './cost-cs-detail.component';
import { CostCsPopupComponent } from './cost-cs-dialog.component';
import { CostCsDeletePopupComponent } from './cost-cs-delete-dialog.component';

export const costRoute: Routes = [
    {
        path: 'cost-cs',
        component: CostCsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cost.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cost-cs/:id',
        component: CostCsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cost.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const costPopupRoute: Routes = [
    {
        path: 'cost-cs-new',
        component: CostCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cost-cs/:id/edit',
        component: CostCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cost-cs/:id/delete',
        component: CostCsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.cost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserCostCsComponent } from './user-cost-cs.component';
import { UserCostCsDetailComponent } from './user-cost-cs-detail.component';
import { UserCostCsPopupComponent } from './user-cost-cs-dialog.component';
import { UserCostCsDeletePopupComponent } from './user-cost-cs-delete-dialog.component';

export const userCostRoute: Routes = [
    {
        path: 'user-cost-cs',
        component: UserCostCsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.userCost.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-cost-cs/:id',
        component: UserCostCsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.userCost.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userCostPopupRoute: Routes = [
    {
        path: 'user-cost-cs-new',
        component: UserCostCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.userCost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-cost-cs/:id/edit',
        component: UserCostCsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.userCost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-cost-cs/:id/delete',
        component: UserCostCsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'costshareApp.userCost.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

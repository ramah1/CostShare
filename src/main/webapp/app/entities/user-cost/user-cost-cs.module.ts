import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CostshareSharedModule } from '../../shared';
import {
    UserCostCsService,
    UserCostCsPopupService,
    UserCostCsComponent,
    UserCostCsDetailComponent,
    UserCostCsDialogComponent,
    UserCostCsPopupComponent,
    UserCostCsDeletePopupComponent,
    UserCostCsDeleteDialogComponent,
    userCostRoute,
    userCostPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userCostRoute,
    ...userCostPopupRoute,
];

@NgModule({
    imports: [
        CostshareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserCostCsComponent,
        UserCostCsDetailComponent,
        UserCostCsDialogComponent,
        UserCostCsDeleteDialogComponent,
        UserCostCsPopupComponent,
        UserCostCsDeletePopupComponent,
    ],
    entryComponents: [
        UserCostCsComponent,
        UserCostCsDialogComponent,
        UserCostCsPopupComponent,
        UserCostCsDeleteDialogComponent,
        UserCostCsDeletePopupComponent,
    ],
    providers: [
        UserCostCsService,
        UserCostCsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CostshareUserCostCsModule {}

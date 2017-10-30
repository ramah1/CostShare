import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CostshareSharedModule } from '../../shared';
import {
    CostCsService,
    CostCsPopupService,
    CostCsComponent,
    CostCsDetailComponent,
    CostCsDialogComponent,
    CostCsPopupComponent,
    CostCsDeletePopupComponent,
    CostCsDeleteDialogComponent,
    costRoute,
    costPopupRoute,
} from './';

const ENTITY_STATES = [
    ...costRoute,
    ...costPopupRoute,
];

@NgModule({
    imports: [
        CostshareSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CostCsComponent,
        CostCsDetailComponent,
        CostCsDialogComponent,
        CostCsDeleteDialogComponent,
        CostCsPopupComponent,
        CostCsDeletePopupComponent,
    ],
    entryComponents: [
        CostCsComponent,
        CostCsDialogComponent,
        CostCsPopupComponent,
        CostCsDeleteDialogComponent,
        CostCsDeletePopupComponent,
    ],
    providers: [
        CostCsService,
        CostCsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CostshareCostCsModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CostshareCostCsModule } from './cost/cost-cs.module';
import { CostshareCSGroupCsModule } from './c-s-group/cs-group-cs.module';
import { CostshareCSUserCsModule } from './c-s-user/cs-user-cs.module';
import { CostshareInviteCsModule } from './invite/invite-cs.module';
import { CostshareUserCostCsModule } from './user-cost/user-cost-cs.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CostshareCostCsModule,
        CostshareCSGroupCsModule,
        CostshareCSUserCsModule,
        CostshareInviteCsModule,
        CostshareUserCostCsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CostshareEntityModule {}

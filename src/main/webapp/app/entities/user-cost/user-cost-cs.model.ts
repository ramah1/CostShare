import { BaseEntity } from './../../shared';

export class UserCostCs implements BaseEntity {
    constructor(
        public id?: number,
        public multiplier?: number,
        public baseCostId?: number,
        public userId?: number,
    ) {
    }
}

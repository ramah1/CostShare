import { BaseEntity } from './../../shared';

export class CostCs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public sum?: number,
        public paidBies?: BaseEntity[],
        public userCosts?: BaseEntity[],
        public groupId?: number,
    ) {
    }
}

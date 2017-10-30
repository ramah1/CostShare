import { BaseEntity } from './../../shared';

export class CSGroupCs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public costs?: BaseEntity[],
        public members?: BaseEntity[],
        public admins?: BaseEntity[],
    ) {
    }
}

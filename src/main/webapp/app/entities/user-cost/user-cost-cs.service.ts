import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { UserCostCs } from './user-cost-cs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserCostCsService {

    private resourceUrl = SERVER_API_URL + 'api/user-costs';

    constructor(private http: Http) { }

    create(userCost: UserCostCs): Observable<UserCostCs> {
        const copy = this.convert(userCost);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(userCost: UserCostCs): Observable<UserCostCs> {
        const copy = this.convert(userCost);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<UserCostCs> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findByCostId(id: number): Observable<ResponseWrapper> {
        return this.http.get(SERVER_API_URL + 'api/costs/'+id+'/user-costs')
            .map((res: ResponseWrapper) => this.convertResponse(res.json()));
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to UserCostCs.
     */
    private convertItemFromServer(json: any): UserCostCs {
        const entity: UserCostCs = Object.assign(new UserCostCs(), json);
        return entity;
    }

    /**
     * Convert a UserCostCs to a JSON which can be sent to the server.
     */
    private convert(userCost: UserCostCs): UserCostCs {
        const copy: UserCostCs = Object.assign({}, userCost);
        return copy;
    }
}

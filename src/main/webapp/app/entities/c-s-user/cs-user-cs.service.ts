import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CSUserCs } from './cs-user-cs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CSUserCsService {

    private resourceUrl = SERVER_API_URL + 'api/c-s-users';

    constructor(private http: Http) { }

    create(cSUser: CSUserCs): Observable<CSUserCs> {
        const copy = this.convert(cSUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cSUser: CSUserCs): Observable<CSUserCs> {
        const copy = this.convert(cSUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CSUserCs> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
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
     * Convert a returned JSON object to CSUserCs.
     */
    private convertItemFromServer(json: any): CSUserCs {
        const entity: CSUserCs = Object.assign(new CSUserCs(), json);
        return entity;
    }

    /**
     * Convert a CSUserCs to a JSON which can be sent to the server.
     */
    private convert(cSUser: CSUserCs): CSUserCs {
        const copy: CSUserCs = Object.assign({}, cSUser);
        return copy;
    }
}

import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CSGroupCs } from './cs-group-cs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CSGroupCsService {

    private resourceUrl = SERVER_API_URL + 'api/c-s-groups';

    constructor(private http: Http) { }

    create(cSGroup: CSGroupCs): Observable<CSGroupCs> {
        const copy = this.convert(cSGroup);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cSGroup: CSGroupCs): Observable<CSGroupCs> {
        const copy = this.convert(cSGroup);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CSGroupCs> {
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
     * Convert a returned JSON object to CSGroupCs.
     */
    private convertItemFromServer(json: any): CSGroupCs {
        const entity: CSGroupCs = Object.assign(new CSGroupCs(), json);
        return entity;
    }

    /**
     * Convert a CSGroupCs to a JSON which can be sent to the server.
     */
    private convert(cSGroup: CSGroupCs): CSGroupCs {
        const copy: CSGroupCs = Object.assign({}, cSGroup);
        return copy;
    }
}

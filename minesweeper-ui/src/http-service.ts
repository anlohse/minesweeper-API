import PubSub from 'pubsub-js';

const TOPIC_NAME = 'minesweeper.credentials';
const LOCAL_STORE_KEY = 'minesweeper.data.Iamback';

interface CredentialTypes {

	authToken: string;

	refreshToken: string;

};

const credentials : CredentialTypes = JSON.parse(localStorage.getItem(LOCAL_STORE_KEY) || '{ "authToken": null, "refreshToken": null }');
setTimeout(function(){
	PubSub.publish(TOPIC_NAME, credentials);
}, 1);

const httpheaders = {
    /**
     * The HTTP Accept header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.2">Section 5.3.2 of RFC 7231</a>
     */
    ACCEPT: 'Accept',
    /**
     * The HTTP Accept-Charset header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.3">Section 5.3.3 of RFC 7231</a>
     */
    ACCEPT_CHARSET: 'Accept-Charset',
    /**
     * The HTTP Accept-Encoding header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.4">Section 5.3.4 of RFC 7231</a>
     */
    ACCEPT_ENCODING: 'Accept-Encoding',
    /**
     * The HTTP Accept-Language header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.5">Section 5.3.5 of RFC 7231</a>
     */
    ACCEPT_LANGUAGE: 'Accept-Language',
    /**
     * The HTTP Accept-Ranges header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-2.3">Section 5.3.5 of RFC 7233</a>
     */
    ACCEPT_RANGES: 'Accept-Ranges',
    /**
     * The CORS Access-Control-Allow-Credentials response header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_ALLOW_CREDENTIALS: 'Access-Control-Allow-Credentials',
    /**
     * The CORS Access-Control-Allow-Headers response header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_ALLOW_HEADERS: 'Access-Control-Allow-Headers',
    /**
     * The CORS Access-Control-Allow-Methods response header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_ALLOW_METHODS: 'Access-Control-Allow-Methods',
    /**
     * The CORS Access-Control-Allow-Origin response header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_ALLOW_ORIGIN: 'Access-Control-Allow-Origin',
    /**
     * The CORS Access-Control-Expose-Headers response header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_EXPOSE_HEADERS: 'Access-Control-Expose-Headers',
    /**
     * The CORS Access-Control-Max-Age response header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_MAX_AGE: 'Access-Control-Max-Age',
    /**
     * The CORS Access-Control-Request-Headers request header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_REQUEST_HEADERS: 'Access-Control-Request-Headers',
    /**
     * The CORS Access-Control-Request-Method request header field name.
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    ACCESS_CONTROL_REQUEST_METHOD: 'Access-Control-Request-Method',
    /**
     * The HTTP Age header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.1">Section 5.1 of RFC 7234</a>
     */
    AGE: 'Age',
    /**
     * The HTTP Allow header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.4.1">Section 7.4.1 of RFC 7231</a>
     */
    ALLOW: 'Allow',
    /**
     * The HTTP Authorization header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.2">Section 4.2 of RFC 7235</a>
     */
    AUTHORIZATION: 'Authorization',
    /**
     * The HTTP Cache-Control header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.2">Section 5.2 of RFC 7234</a>
     */
    CACHE_CONTROL: 'Cache-Control',
    /**
     * The HTTP Connection header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-6.1">Section 6.1 of RFC 7230</a>
     */
    CONNECTION: 'Connection',
    /**
     * The HTTP Content-Encoding header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.2.2">Section 3.1.2.2 of RFC 7231</a>
     */
    CONTENT_ENCODING: 'Content-Encoding',
    /**
     * The HTTP Content-Disposition header field name.
     * @see <a href="https://tools.ietf.org/html/rfc6266">RFC 6266</a>
     */
    CONTENT_DISPOSITION: 'Content-Disposition',
    /**
     * The HTTP Content-Language header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.3.2">Section 3.1.3.2 of RFC 7231</a>
     */
    CONTENT_LANGUAGE: 'Content-Language',
    /**
     * The HTTP Content-Length header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-3.3.2">Section 3.3.2 of RFC 7230</a>
     */
    CONTENT_LENGTH: 'Content-Length',
    /**
     * The HTTP Content-Location header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.4.2">Section 3.1.4.2 of RFC 7231</a>
     */
    CONTENT_LOCATION: 'Content-Location',
    /**
     * The HTTP Content-Range header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-4.2">Section 4.2 of RFC 7233</a>
     */
    CONTENT_RANGE: 'Content-Range',
    /**
     * The HTTP Content-Type header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.1.5">Section 3.1.1.5 of RFC 7231</a>
     */
    CONTENT_TYPE: 'Content-Type',
    /**
     * The HTTP Cookie header field name.
     * @see <a href="https://tools.ietf.org/html/rfc2109#section-4.3.4">Section 4.3.4 of RFC 2109</a>
     */
    COOKIE: 'Cookie',
    /**
     * The HTTP Date header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.2">Section 7.1.1.2 of RFC 7231</a>
     */
    DATE: 'Date',
    /**
     * The HTTP ETag header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.3">Section 2.3 of RFC 7232</a>
     */
    ETAG: 'ETag',
    /**
     * The HTTP Expect header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.1.1">Section 5.1.1 of RFC 7231</a>
     */
    EXPECT: 'Expect',
    /**
     * The HTTP Expires header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.3">Section 5.3 of RFC 7234</a>
     */
    EXPIRES: 'Expires',
    /**
     * The HTTP From header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.5.1">Section 5.5.1 of RFC 7231</a>
     */
    FROM: 'From',
    /**
     * The HTTP Host header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-5.4">Section 5.4 of RFC 7230</a>
     */
    HOST: 'Host',
    /**
     * The HTTP If-Match header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.1">Section 3.1 of RFC 7232</a>
     */
    IF_MATCH: 'If-Match',
    /**
     * The HTTP If-Modified-Since header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.3">Section 3.3 of RFC 7232</a>
     */
    IF_MODIFIED_SINCE: 'If-Modified-Since',
    /**
     * The HTTP If-None-Match header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.2">Section 3.2 of RFC 7232</a>
     */
    IF_NONE_MATCH: 'If-None-Match',
    /**
     * The HTTP If-Range header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-3.2">Section 3.2 of RFC 7233</a>
     */
    IF_RANGE: 'If-Range',
    /**
     * The HTTP If-Unmodified-Since header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.4">Section 3.4 of RFC 7232</a>
     */
    IF_UNMODIFIED_SINCE: 'If-Unmodified-Since',
    /**
     * The HTTP Last-Modified header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.2">Section 2.2 of RFC 7232</a>
     */
    LAST_MODIFIED: 'Last-Modified',
    /**
     * The HTTP Link header field name.
     * @see <a href="https://tools.ietf.org/html/rfc5988">RFC 5988</a>
     */
    LINK: 'Link',
    /**
     * The HTTP Location header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.2">Section 7.1.2 of RFC 7231</a>
     */
    LOCATION: 'Location',
    /**
     * The HTTP Max-Forwards header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.1.2">Section 5.1.2 of RFC 7231</a>
     */
    MAX_FORWARDS: 'Max-Forwards',
    /**
     * The HTTP Origin header field name.
     * @see <a href="https://tools.ietf.org/html/rfc6454">RFC 6454</a>
     */
    ORIGIN: 'Origin',
    /**
     * The HTTP Pragma header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.4">Section 5.4 of RFC 7234</a>
     */
    PRAGMA: 'Pragma',
    /**
     * The HTTP Proxy-Authenticate header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.3">Section 4.3 of RFC 7235</a>
     */
    PROXY_AUTHENTICATE: 'Proxy-Authenticate',
    /**
     * The HTTP Proxy-Authorization header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.4">Section 4.4 of RFC 7235</a>
     */
    PROXY_AUTHORIZATION: 'Proxy-Authorization',
    /**
     * The HTTP Range header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-3.1">Section 3.1 of RFC 7233</a>
     */
    RANGE: 'Range',
    /**
     * The HTTP Referer header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.5.2">Section 5.5.2 of RFC 7231</a>
     */
    REFERER: 'Referer',
    /**
     * The HTTP Retry-After header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.3">Section 7.1.3 of RFC 7231</a>
     */
    RETRY_AFTER: 'Retry-After',
    /**
     * The HTTP Server header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.4.2">Section 7.4.2 of RFC 7231</a>
     */
    SERVER: 'Server',
    /**
     * The HTTP Set-Cookie header field name.
     * @see <a href="https://tools.ietf.org/html/rfc2109#section-4.2.2">Section 4.2.2 of RFC 2109</a>
     */
    SET_COOKIE: 'Set-Cookie',
    /**
     * The HTTP Set-Cookie2 header field name.
     * @see <a href="https://tools.ietf.org/html/rfc2965">RFC 2965</a>
     */
    SET_COOKIE2: 'Set-Cookie2',
    /**
     * The HTTP TE header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-4.3">Section 4.3 of RFC 7230</a>
     */
    TE: 'TE',
    /**
     * The HTTP Trailer header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-4.4">Section 4.4 of RFC 7230</a>
     */
    TRAILER: 'Trailer',
    /**
     * The HTTP Transfer-Encoding header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-3.3.1">Section 3.3.1 of RFC 7230</a>
     */
    TRANSFER_ENCODING: 'Transfer-Encoding',
    /**
     * The HTTP Upgrade header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-6.7">Section 6.7 of RFC 7230</a>
     */
    UPGRADE: 'Upgrade',
    /**
     * The HTTP User-Agent header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.5.3">Section 5.5.3 of RFC 7231</a>
     */
    USER_AGENT: 'User-Agent',
    /**
     * The HTTP Vary header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.4">Section 7.1.4 of RFC 7231</a>
     */
    VARY: 'Vary',
    /**
     * The HTTP Via header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-5.7.1">Section 5.7.1 of RFC 7230</a>
     */
    VIA: 'Via',
    /**
     * The HTTP Warning header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.5">Section 5.5 of RFC 7234</a>
     */
    WARNING: 'Warning',
    /**
     * The HTTP WWW-Authenticate header field name.
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.1">Section 4.1 of RFC 7235</a>
     */
    WWW_AUTHENTICATE: 'WWW-Authenticate',

};

const initStates = () => {
    PubSub.subscribe(TOPIC_NAME, (_msg:any, value:any) => {
        Object.assign(credentials, value || {});
    });
};

const request = (url:string, options:any) : Promise<Response> => {
    options = options || { headers: {} };
    let authHeaders : any = {};
    if (credentials.authToken)
        authHeaders['Authentication'] = 'Bearer ' + credentials.authToken;
    let headers = Object.assign(options.headers || {}, authHeaders);
    return new Promise((resolve, reject) => {
        fetch(url, Object.assign({}, options || {}, { headers }))
            .then(resp => {
                if (resp.status >= 400) {
                    let error : any = new Error('HTTP status ' + resp.status + ': ' + resp.statusText);
                    error.response = resp;
                    reject(error);
                }
                resolve(resp);
            })
            .catch(reject);
    });
};

const insertQueryParams = (url:string, params:any) => {
    let url1 = url.indexOf('?') != -1 ? url + '?' : (url.indexOf('&') == url.length - 1 ? url : (url + '&'));
    Object.entries(params).forEach((e, i) => {
        url1 += (i == 0 ? '' : '&') + escape(e[0]) + '=' + escape(<string>e[1]);
    });
    return url1;
};

const get = (url:string, options:any) : Promise<Response> => {
    options = options || {};
    if (options.hasOwnProperty('params'))
        url = insertQueryParams(url, options.params);
    return request(url, Object.assign({}, options, { method: 'GET' }));
};

const post = (url:string, options:any) : Promise<Response> => {
    return request(url, Object.assign({}, options || {}, { method: 'POST' }));
};

const put = (url:string, options:any) : Promise<Response> => {
    return request(url, Object.assign({}, options || {}, { method: 'PUT' }));
};

const http_delete = (url:string, options:any) : Promise<Response> => {
    return request(url, Object.assign({}, options || {}, { method: 'DELETE' }));
};

const APPLICATION_JSON_UTF_8 = 'application/json; charset=utf-8';

function createPostOptions(ent:any, options:any) {
    let bodyData = JSON.stringify(ent);
    let headers :any = {};
    headers[httpheaders.ACCEPT] = APPLICATION_JSON_UTF_8;
    headers[httpheaders.CONTENT_TYPE] = APPLICATION_JSON_UTF_8;
    headers[httpheaders.CONTENT_LENGTH] = bodyData.length;
    return Object.assign({}, options || {}, {
        headers,
        body: bodyData
    });
}

type Constructor = new (...args: any[]) => {};

const _RestService_create = <TRestBase extends Constructor>(baseUrl:string, RestBase:TRestBase) => {
    return class extends RestBase {
        /**
         * Execute a POST request to the base URL.
         * @param {*} entity the entity to create
         * @param {*?} options the options to the request
         * @returns the created entity
         */
        async create(entity:any, options:any) {
            options = createPostOptions(entity, options);
            return await (await post(baseUrl, options)).json();
        }
    };
};

const _RestService_retrieve = <TRestBase extends Constructor>(baseUrl:string, RestBase:TRestBase) => {
    return class extends RestBase {
        /**
         * Execute a GET request to the base URL
         * @param {*?} params the query parameters of the request
         * @param {*?} options the options to the request
         * @returns {Array} the result of the query
         */
        async retrieve(params:any, options:any) {
            params = params || {};
            let headers : any = {};
            headers[httpheaders.ACCEPT] = APPLICATION_JSON_UTF_8;
            return await (await get(baseUrl, Object.assign({}, options || {}, { params, headers }))).json();
        }
    };
};

const _RestService_update = <TRestBase extends Constructor>(baseUrl:string, RestBase:TRestBase) => {
    return class extends RestBase {
        /**
         * Execute a PUT request to the base URL
         * @param {*} entity the entity to be updated
         * @param {*?} options the options to the request
         * @returns the updated entity
         */
        async update(entity:any, options:any) {
            options = createPostOptions(entity, options);
            return await (await put(baseUrl, options)).json();
        }
    };
};

const _RestService_delete = <TRestBase extends Constructor>(baseUrl:string, RestBase:TRestBase) => {
    return class extends RestBase {
        /**
         * Execute a DELETE request to the base URL
         * @param {*} entity the entity to be updated
         * @param {*?} options the options to the request
         * @returns nothing 
         */
        async delete(entity:any, options:any) {
            options = createPostOptions(entity, options);
            return await (await http_delete(baseUrl, options)).json();
        }
    };
};

/**
 * Extends the base class to create a rest service
 * @param {string} baseUrl the base url
 * @param {Function?} RestBase the base class to the rest service. If it is null, the function will use the Object class
 * @param {*?} options the options to extend. Setting false to the properties create, retrieve, update and delete will remove the methods.
 * @returns {Function} the rest service implementation
 */
const RestService = <TRestBase extends Constructor>(baseUrl:string, RestBase:TRestBase, options:any) => {
    let opt = Object.assign({ create: true, retrieve: true, update: true, delete: true }, options || {});
    let clazz = RestBase || Object;
    if (opt.create)
        clazz = _RestService_create(baseUrl, clazz);
    if (opt.retrieve)
        clazz = _RestService_retrieve(baseUrl, clazz);
    if (opt.update)
        clazz = _RestService_update(baseUrl, clazz);
    if (opt.delete)
        clazz = _RestService_delete(baseUrl, clazz);
    return clazz;
};

initStates();

const moduleExports = { request, get, post, put, delete: http_delete, RestService, Headers: httpheaders };

export default moduleExports;

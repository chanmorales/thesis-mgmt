import { BACKEND_BASE_URL, RequestMethod } from "../common/constants";
import CommonHelper from "./CommonHelper";

export default {
  async doRemoteCallAsync(
    url: string,
    action: string,
    method: RequestMethod = RequestMethod.GET,
    body?: unknown
  ) {
    try {
      // Initialize the request
      const request: RequestInit = {
        method: method,
      };

      // Add the content type and body if there are any
      if (body) {
        request.headers = {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        };
        request.body = JSON.stringify(body);
      } else {
        request.headers = {
          "Access-Control-Allow-Origin": "*",
        };
      }

      // Perform the request and
      const response = await fetch(BACKEND_BASE_URL + url, request);
      switch (response.status) {
        case 400:
        case 401:
        case 403:
        case 404:
          throw response;
        case 500:
          throw Object.assign(
            new Error(`Unable to perform ${action}. Internal Server Error.`)
          );
        case 204:
          return;
        default: {
          const responseData = await response.text();
          return Promise.resolve(
            CommonHelper.isJsonParseable(responseData)
              ? JSON.parse(responseData)
              : responseData
          );
        }
      }
    } catch (err: any) {
      if ([400, 401, 403, 404].includes(err.status)) {
        throw await err.text();
      } else if (err) {
        console.error(err);
      }
    }
  },
};

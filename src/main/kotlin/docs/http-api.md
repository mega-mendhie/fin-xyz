Base Url: https://api.sqube.org

**Endpoint** 1 — Health check
1. Method & path: GET /health
2. Headers: {"Accept": "application/json"}
3. Sample Request Body: None
4. Successful response status code: 200
5. Sample Response Body: {
   "status": "UP"
   }
6. Possible error responses: 404 Not Found. The requested URL or resource does not exist on the server; 405 Method Not Allowed. The HTTP method used (such as POST or DELETE) is not supported for that endpoint.



**Endpoint 2** — Create transaction
1. Method & path: POST /transactions
2. Headers: {"Accept": "application/json", "Authorization": "Bearer xyz123"}
3. Sample Request Body: {
   "amount": 60000,
   "description": "For bread",
   "accountId": "UU3hgjfj"
   }
4. Successful response status code: 201
5. Sample Response Body: {
   "id": "tnx123",
   "amount": 60000,
   "description": "For bread",
   "accountId": "UU3hgjfj"
   }
6. Possible error responses: 400 - Bad Request (The server cannot process the request); 401 - Unauthorized (Missing/Invalid Auth); 403 - User is authenticated but lacks permission; 404 - The specified account doesn't exist or isn't accessible; 405 - Method Not Allowed (The HTTP method used is not supported for that endpoint).

**Endpoint 3** — Retrieve transactions
1. Method & path: GET /transactions
2. Headers: {"Content-Type": "application/json",
   "Accept": "application/json", "Authorization": "Bearer xyz123"}
3. Sample Request Body: None
4. Successful response status code; 200
5. Sample Response Body: [{
   
   "id": "tnx123",
   "amount": 60000,
   "description": "For bread",
   "accountId": "UU3hgjfj"
   },
   {
   "id": "tnx456",
   "amount": 60000,
   "description": "For bread",
   "accountId": "UU3hgjfj"
   }]
   
Sample Empty Response Body:
   []
6. Possible error responses: 400 - Bad Request (The server cannot process the request); 401 - Unauthorized (Missing/Invalid Auth); 403 - User is authenticated but lacks permission; 404 - The specified account doesn't exist or isn't accessible; 405 - Method Not Allowed (The HTTP method used is not supported for that endpoint).



**Short Explanation**
- POST is designed to submit data to the parent collection. Also it's non-idempotent.
- GET should not modify transaction data because the HTTP standard defines it as a safe, read-only method.
- If the client sends the same create-transaction request twice, it creates same transaction record twice with different IDs.
- The backend should generate the transaction ID rather than trusting the client to provide one because the backend should be the single source of truth, ensuring data integrity and security, regardless of how many connected clients.
- Duplicate POST requests are a potential problem because since POST is not idempotent, the server will treat is as a new request and same action will be executed multiple times. This can cause duplicate records and corrupted data. In the case of financial records like this, it can lead to lots of miscalculations.
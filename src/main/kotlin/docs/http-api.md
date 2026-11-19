**Endpoint** 1 — Health check
1. Method & url: GET https://sqube.org/health
2. Headers: {"Content-Type": "application/json",
   "Accept": "application/json"}
3. Sample Request Body: None
4. Successful response status code: 200
5. Sample Response Body: {
   "status": "UP"
   }
6. Possible error responses: 400 Bad Request. The server cannot process the request; 404 Not Found. The requested URL or resource does not exist on the server; 405 Method Not Allowed. The HTTP method used (such as POST or DELETE) is not supported for that endpoint.



**Endpoint 2** — Create transaction
1. Method & url: POST https://sqube.org/transactions
2. Headers: {"Content-Type": "application/json",
   "Accept": "application/json", "Authorization": "Bearer xyz123"}
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
6. Possible error responses: 400 Bad Request. The server cannot process the request; 401 Unauthorized (Missing/Invalid Auth); 404 Not Found. The requested URL or resource does not exist on the server; 405 Method Not Allowed. The HTTP method used (such as POST or DELETE) is not supported for that endpoint.

**Endpoint 3** — Retrieve transactions
1. Method & url: GET https://sqube.org/transactions
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
   "id": "tnx123",
   "amount": 60000,
   "description": "For bread",
   "accountId": "UU3hgjfj"
   }]
6. Possible error responses: 400 Bad Request. The server cannot process the request; 401 Unauthorized (Missing/Invalid Auth); 404 Not Found. The requested URL or resource does not exist on the server; 405 Method Not Allowed. The HTTP method used (such as POST or DELETE) is not supported for that endpoint.



**Short Explanation**
- POST is designed to submit data to the parent collection. Also it's non-idempotent.
- GET should not modify transaction data because the HTTP standard defines it as a safe, read-only method.
- If the client sends the same create-transaction request twice, it creates same transaction record twice with different IDs.
- The backend should generate the transaction ID rather than trusting the client to provide one because the backend should be the single source of truth, ensuring data integrity and security, regardless of how many connected clients.
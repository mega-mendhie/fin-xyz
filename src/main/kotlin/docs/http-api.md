**Endpoint** 1 — Health check
1. GET https://sqube.org/health
2. Required headers: Content-Type, Accept
3. None
4. 200
5. {
   "status": "UP"
   }
6. 400 Bad Request. The server cannot process the request; 404 Not Found. The requested URL or resource does not exist on the server; 405 Method Not Allowed. The HTTP method used (such as POST or DELETE) is not supported for that endpoint.



**Endpoint 2** — Create transaction
1. POST https://sqube.org/transactions
2. Required headers: Content-Type, Accept
3. {
   "amount": 60000,
   "description": "For bread",
   "accountId": "UU3hgjfj"
   }
4. 201
5. {
   "id": "tnx123",
   "amount": 60000,
   "description": "For bread",
   "accountId": "UU3hgjfj"
   }
6. 400 Bad Request. The server cannot process the request; 404 Not Found. The requested URL or resource does not exist on the server; 405 Method Not Allowed. The HTTP method used (such as POST or DELETE) is not supported for that endpoint.

**Endpoint 3** — Retrieve transactions
1. GET https://sqube.org/transactions
2. Required headers: Content-Type, Accept
3. None
4. 200
5. [{
   
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
6. 400 Bad Request. The server cannot process the request; 404 Not Found. The requested URL or resource does not exist on the server; 405 Method Not Allowed. The HTTP method used (such as POST or DELETE) is not supported for that endpoint.



**Short Explanation**
- POST is designed to submit data to the parent collection. Also it's non-idempotent.
- GET should not modify transaction data because the HTTP standard defines it as a safe, read-only method.
- If the client sends the same create-transaction request twice, it creates same transaction record twice with different IDs.
- The backend should generate the transaction ID rather than trusting the client to provide one because the backend should be the single source of truth, ensuring data integrity and security, regardless of how many connected clients.
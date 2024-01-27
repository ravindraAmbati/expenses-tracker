"# expenses-tracker"
# Expenses Details

<details>
<summary>GET | /expenses | to fetch ALL Expense Details</summary>

## Request:
#### URI: /expenses
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 99,
    "amount": 123.45,
    "paidBy":"userId or alias",
    "paidTo":"receiverId or alias",
    "dateAndTime":"localDateTime",
    "description":"sample test",
    "currency": {
      "id": 33,
      "currency": "INR",
      "exchangeRate":"",
      "defaultCurrency":"INR",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "paymentMode": {
      "id": 99,
      "paymentMode": "CASH",
      "cardDetails":"",
      "isDebitCard":"false",
      "isCreditCard":"false",
      "accountDetails":"",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "category":{
      "id": 33,
      "expensesCategory": "FOOD",
      "alias":["breakfast","lunch","dinner"],
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 98,
    "amount": 123.45,
    "paidBy":"userId or alias",
    "paidTo":"receiverId or alias",
    "dateAndTime":"localDateTime",
    "description":"sample test",
    "currency": {
      "id": 33,
      "currency": "INR",
      "exchangeRate":"",
      "defaultCurrency":"INR",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "paymentMode": {
      "id": 99,
      "paymentMode": "CASH",
      "cardDetails":"",
      "isDebitCard":"false",
      "isCreditCard":"false",
      "accountDetails":"",
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "category":{
      "id": 33,
      "expensesCategory": "FOOD",
      "alias":["breakfast","lunch","dinner"],
      "description":"sample test",
      "isDeleted":"false",
      "lastUpdatedBy":"userId or alias",
      "lastUpdatedDateAndTime":"localDateTime"
    },
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /expenses/{id} | to fetch Expense Details</summary>

## Request:
#### URI: /expenses/{id}
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": {
    "id": 33,
    "currency": "INR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "paymentMode": {
    "id": 99,
    "paymentMode": "CASH",
    "cardDetails":"",
    "isDebitCard":"false",
    "isCreditCard":"false",
    "accountDetails":"",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "category":{
    "id": 33,
    "expensesCategory": "FOOD",
    "alias":["breakfast","lunch","dinner"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>POST | /expenses | Create Expenses Details</summary>

## Request:
#### URI: /expenses
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "4315***99",
  "category":"lunch",
  "isDeleted":"false"
}
```

## Response:
### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

### Body: None
</details>


<details>
<summary>PUT | /expenses/{id} | Create or update Expenses Details</summary>

## Request:
#### URI: /expenses
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "4315***99",
  "category":"breakfast",
  "isDeleted":"false"
}
```

## Response:
### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the expenses details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the expenses details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

### Body: None
</details>


<details>
<summary>PATCH | /expenses/{id} | update partial Expenses Details</summary>

## Request:
#### URI: /expenses/{id}
#### HTTP Verb: PATCH
#### Request Body Type: JSON
Example Request Body:
```
{
  "amount": 123.45,
  "category": "dinner"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": {
    "id": 33,
    "currency": "INR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  "paymentMode": {
    "paymentMode": "CARD",
    "cardDetails":"4315***99",
    "isDebitCard":"true",
    "isCreditCard":"false",
    "accountDetails":"",
    "description":"sample test"
  },
  "category":"FOOD",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /expenses/{id} | delete partial Expenses Details</summary>

## Request:
#### URI: /expenses/{id}
#### HTTP Verb: DELETE
#### Body: None

## Response:
### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the expenses details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expenses details are not found

### Body: None
</details>

# Payment Mode Details

<details>
<summary>GET | /paymentMode | to fetch ALL paymentMode Details</summary>

## Request:
#### URI: /paymentMode
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 99,
    "paymentMode": "CASH",
    "cardDetails":"",
    "isDebitCard":"false",
    "isCreditCard":"false",
    "accountDetails":"",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 99,
    "paymentMode": "CARD",
    "cardDetails":"4315***99",
    "isDebitCard":"false",
    "isCreditCard":"true",
    "accountDetails":"",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 99,
    "paymentMode": "UPI",
    "cardDetails":"",
    "isDebitCard":"false",
    "isCreditCard":"false",
    "accountDetails":"",
    "upiAddress":"*****@upi",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /paymentMode/{id} | to fetch Expense Details</summary>

## Request:
#### URI: /paymentMode/{id}
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 12,
  "paymentMode": "CARD",
  "cardDetails":"4315***99",
  "isDebitCard":"true",
  "isCreditCard":"false",
  "accountDetails":"",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>GET | /paymentMode/search | to fetch Expense Details</summary>

## Request:
#### URI: /paymentMode/search
#### HTTP Verb: GET
#### Request Body Type: JSON
Example Request Body:
```
{
"searchStr":"CASH"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

### Response Body Type: JSON
Example Response Body:
```
[
{
  "id": 12,
  "paymentMode": "CARD",
  "cardDetails":"4315***99",
  "isDebitCard":"true",
  "isCreditCard":"false",
  "accountDetails":"",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
]
```
</details>


<details>
<summary>POST | /paymentMode | Create Expenses Details</summary>

## Request:
#### URI: /paymentMode
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "paymentMode": "CARD",
  "cardDetails":"4315***99",
  "isDebitCard":"true",
  "isCreditCard":"false",
  "accountDetails":"",
  "description":"sample test"
}
```

## Response:
### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

### Body: None
</details>


<details>
<summary>PUT | /paymentMode/{id} | Create or update Expenses Details</summary>

## Request:
#### URI: /paymentMode
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "CASH",
  "category":"FOOD"
}
```

## Response:
### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the paymentMode details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the paymentMode details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

### Body: None
</details>


<details>
<summary>PATCH | /paymentMode/{id} | update partial Expenses Details</summary>

## Request:
#### URI: /paymentMode/{id}
#### HTTP Verb: PATCH
#### Request Body Type: JSON
Example Request Body:
```
{
  "amount": 123.45,
  "paymentMode": "CARD",
  "category":"FOOD"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 99,
  "amount": 123.45,
  "paidBy":"userId or alias",
  "paidTo":"receiverId or alias",
  "dateAndTime":"localDateTime",
  "description":"sample test",
  "currency": "INR",
  "paymentMode": "CASH",
  "category":"FOOD",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /paymentMode/{id} | delete partial Expenses Details</summary>

## Request:
#### URI: /paymentMode/{id}
#### HTTP Verb: DELETE
#### Body: None

## Response:
### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the paymentMode details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested paymentMode details are not found

### Body: None
</details>

# Currency Details

<details>
<summary>GET | /currency | to fetch ALL currency Details</summary>

## Request:
#### URI: /currency
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 33,
    "currency": "INR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 44,
    "currency": "USD",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 55,
    "currency": "EUR",
    "exchangeRate":"",
    "defaultCurrency":"INR",
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /currency/{id} | to fetch Expense Details</summary>

## Request:
#### URI: /currency/{id}
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>GET | /currency/search | to fetch Expense Details</summary>

## Request:
#### URI: /currency/search
#### HTTP Verb: GET
#### Request Body Type: JSON
Example Request Body:
```
{
"searchStr":"INR"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>POST | /currency | Create Expenses Details</summary>

## Request:
#### URI: /currency
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test"
}
```

## Response:
### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

### Body: None
</details>


<details>
<summary>PUT | /currency/{id} | Create or update Expenses Details</summary>

## Request:
#### URI: /currency
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test"
}
```

## Response:
### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the currency details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the currency details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

### Body: None
</details>


<details>
<summary>PATCH | /currency/{id} | update partial Expenses Details</summary>

## Request:
#### URI: /currency/{id}
#### HTTP Verb: PATCH
#### Request Body Type: JSON
Example Request Body:
```
{
  "currency": "EUR",
  "exchangeRate":"",
  "description":"sample test"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "currency": "EUR",
  "exchangeRate":"",
  "defaultCurrency":"INR",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /currency/{id} | delete partial Expenses Details</summary>

## Request:
#### URI: /currency/{id}
#### HTTP Verb: DELETE
#### Body: None

## Response:
### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the currency details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested currency details are not found

### Body: None
</details>

# Expenses Category Details

<details>
<summary>GET | /expensesCategory | to fetch ALL expensesCategory Details</summary>

## Request:
#### URI: /expensesCategory
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 33,
    "expensesCategory": "FOOD",
    "alias":["breakfast","lunch","dinner"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 44,
    "expensesCategory": "TRAVEL",
    "alias":["train","flight","bus","cab"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 55,
    "expensesCategory": "LOAN",
    "alias":["emi","home loan","car loan","gold loan","personal loan","credit card emi"],
    "description":"sample test",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /expensesCategory/{id} | to fetch Expense Details</summary>

## Request:
#### URI: /expensesCategory/{id}
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "EDUCATION",
  "alias":["school fee", "tuition fee","online courses","udemy","certification"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>GET | /expensesCategory/search | to fetch Expense Details</summary>

## Request:
#### URI: /expensesCategory/search
#### HTTP Verb: GET
#### Request Body Type: JSON
Example Request Body: 
```
{
"searchStr":"certification"
}
```
## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Response Body Type: JSON
Example Response Body:
```
[
{
  "id": 55,
  "expensesCategory": "EDUCATION",
  "alias":["school fee", "tuition fee","online courses","udemy","certification"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
]
```
</details>


<details>
<summary>POST | /expensesCategory | Create Expenses Details</summary>

## Request:
#### URI: /expensesCategory
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "expensesCategory": "FOOD",
  "description":"sample test"
}
```

## Response:
### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

### Body: None
</details>


<details>
<summary>PUT | /expensesCategory/{id} | Create or update Expenses Details</summary>

## Request:
#### URI: /expensesCategory
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "id": 55,
  "expensesCategory": "FUEL",
  "alias":["petrol","diesel","cng","charging"],
  "description":"sample test"
}
```

## Response:
### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the expensesCategory details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the expensesCategory details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Body: None
</details>


<details>
<summary>PATCH | /expensesCategory/{id} | update partial Expenses Details</summary>

## Request:
#### URI: /expensesCategory/{id}
#### HTTP Verb: PATCH
#### Request Body Type: JSON
Example Request Body:
```
{
  "expensesCategory": "HOUSEHOLD",
  "alias":["house repairs","flooring","cleaning"],
  "description":"sample test"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "HOUSEHOLD",
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>PATCH | /expensesCategory/{id}/add/alias | update partial Expenses Details</summary>

## Request:
#### URI: /expensesCategory/{id}/add/alias
#### HTTP Verb: PATCH
#### Request Body Type: JSON
Example Request Body:
```
{
  "alias":["water","electricity bill","plumbing repairs"]
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "HOUSEHOLD",
  "alias":["house repairs","flooring","cleaning","water","electricity bill","plumbing repairs"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>PATCH | /expensesCategory/{id}/remove/alias | update partial Expenses Details</summary>

## Request:
#### URI: /expensesCategory/{id}/remove/alias
#### HTTP Verb: PATCH
#### Request Body Type: JSON
Example Request Body:
```
{
  "alias":["water"]
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "expensesCategory": "HOUSEHOLD",
  "alias":["house repairs","flooring","cleaning","electricity bill","plumbing repairs"],
  "description":"sample test",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /expensesCategory/{id} | delete partial Expenses Details</summary>

## Request:
#### URI: /expensesCategory/{id}
#### HTTP Verb: DELETE
#### Body: None

## Response:
### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the expensesCategory details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested expensesCategory details are not found

### Body: None
</details>

# User Details

<details>
<summary>GET | /User | to fetch ALL User Details</summary>

## Request:
#### URI: /User
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve all User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

### Response Body Type: JSON
Example Response Body:
```
[
  {
    "id": 33,
    "firstName": "ravindra",
    "lastName":"reddy",
    "emailId":"ravindra@email.com",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 44,
    "firstName": "ravindra",
    "lastName":"reddy",
    "emailId":"ravindra@email.com",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  },
  {
    "id": 55,
    "firstName": "ambati",
    "lastName":"reddy",
    "emailId":"ravindra@email",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
]
```
</details>


<details>
<summary>GET | /User/{id} | to fetch Expense Details</summary>

## Request:
#### URI: /User/{id}
#### HTTP Verb: GET
#### Body: None

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

### Response Body Type: JSON
Example Response Body:
```
  {
    "id": 55,
    "firstName": "ambati",
    "lastName":"reddy",
    "emailId":"ravindra@email",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
```
</details>


<details>
<summary>GET | /User/search | to fetch Expense Details</summary>

## Request:
#### URI: /User/search
#### HTTP Verb: GET
#### Request Body Type: JSON
Example Request Body:
```
{
"searchStr":"ravindra"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to retrieve the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

### Response Body Type: JSON
Example Response Body:
```
  {
    "id": 55,
    "firstName": "ambati",
    "lastName":"reddy",
    "emailId":"ravindra@email",
    "mobileNo":"0987654321",
    "isDeleted":"false",
    "lastUpdatedBy":"userId or alias",
    "lastUpdatedDateAndTime":"localDateTime"
  }
```
</details>


<details>
<summary>POST | /User | Create Expenses Details</summary>

## Request:
#### URI: /User
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "firstName": "ravindra",
  "lastName":"ambati",
  "emailId":"ravindra@email",
  "mobileNo":"0987654321"
}
```

## Response:
### HTTP Status:
1. **201** CREATED  - When the User is authenticated and authorized to create the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized

### Body: None
</details>


<details>
<summary>PUT | /User/{id} | Create or update Expenses Details</summary>

## Request:
#### URI: /User
#### HTTP Verb: POST
#### Body Type: JSON
Example Request Body:
```
{
  "id": 55,
  "firstName": "ravindra",
  "lastName":"",
  "emailId":"ravindra@email",
  "mobileNo":"0987654321"
}
```

## Response:
### HTTP Status:
1. **201** OK - When the User is authenticated and authorized to create the User details
2. **204** NO CONTENT - When the User is authenticated and authorized to update the User details
3. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
4. **403** FORBIDDEN - When the user is authenticated but unauthorized
5. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

### Body: None
</details>


<details>
<summary>PATCH | /User/{id} | update partial Expenses Details</summary>

## Request:
#### URI: /User/{id}
#### HTTP Verb: PATCH
#### Request Body Type: JSON
Example Request Body:
```
{
  "firstName": "ravindra",
  "lastName":"ambati",
  "mobileNo":"0987654321"
}
```

## Response:
### HTTP Status:
1. **200** OK - When the User is authenticated and authorized to update the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

### Response Body Type: JSON
Example Response Body:
```
{
  "id": 55,
  "firstName": "ravindra",
  "lastName":"ambati",
  "emailId":"ravindra@email",
  "mobileNo":"0987654321",
  "isDeleted":"false",
  "lastUpdatedBy":"userId or alias",
  "lastUpdatedDateAndTime":"localDateTime"
}
```
</details>


<details>
<summary>DELETE | /User/{id} | delete partial Expenses Details</summary>

## Request:
#### URI: /User/{id}
#### HTTP Verb: DELETE
#### Body: None

## Response:
### HTTP Status:
1. **204** NO CONTENT - When the User is authenticated and authorized to delete the User details
2. **401** UNAUTHORIZED - When the user is unauthenticated or unauthorized
3. **403** FORBIDDEN - When the user is authenticated but unauthorized
4. **404** NOT FOUND - When the user is authenticated and authorized but requested User details are not found

### Body: None
</details>
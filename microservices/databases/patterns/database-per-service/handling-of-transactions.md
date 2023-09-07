# Handling of transactions 

## Problem 

  * When we move to a database per service pattern, we cannot use transactions

## Example Problem 

  * You are using database-per-service-pattern

```
x e-commerce store
x customers have a credit limit.
x The application must ensure that a new order will not exceed the customer’s credit limit.
x Orders and Customers are in different databases owned by different services
x because of this: application cannot simply use a local ACID transaction.
```

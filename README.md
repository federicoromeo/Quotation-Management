# Quotation-Management

Project of "Tecnologie informatiche per il WEB" .


#### PURE HTML + thymeleaf VERSION

A web application allows the management of requests for quotes for customized products. A quote is
associated with a product, with the customer who requested it and with the employee who managed it. The quote includes a
or more options for the product it is associated with, which must be among those available for the product. A
product has a code, an image and a name. An option has a code, a type ("normal", "on sale") and a
name. A quote has a price, defined by the employee. When the user (client or employee) logs in
to the application, a LOGIN PAGE appears, through which the user authenticates himself with a username and password.
When a customer logs in, they access a CUSTOMER HOME PAGE page that contains a form to create a
estimate and the list of estimates created by the customer. Using the form, the user first chooses the product;
chosen the product, the form shows the options of that product. The user chooses the options (at least one) and confirms
sending the quote using the SEND QUOTE button. When an employee logs in, they go to a page
EMPLOYEE HOME PAGE which contains the list of estimates previously managed by him and that of estimates
not yet associated with any employee. When the employee selects an item from the list of estimates
not yet associated with anyone, a QUOTE PRICE page appears showing the customer data (username)
and the quote and a form to enter the quote price. When the employee enters the price and sends the
data with the SEND PRICE button, the EMPLOYEE HOME PAGE page appears again with the lists of
updated estimates.

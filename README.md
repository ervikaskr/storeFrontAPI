# storeFrontAPI
REST service that provides API for a storefront application. Implementation done in Java 17 using SpringBoot 3

# Online Store Application API

This project is an online store service implemented using Spring Boot. The service provides APIs that support the following features:

1. **Browsing of items with pagination**: Allows users to browse a list of items, each showing the following details:
   - ItemId
   - URL to the item’s image
   - Name
   - Short description
   - Price
   - Percentage discount from the suggested price

2. **Checkout form submission with data validation**: Validates and processes a checkout form containing:
   - `ItemId`: Must correspond to an existing item.
   - `Full Name`: Should only contain letters A-Z, a-z, and spaces.
   - `Address`: Should be a non-empty string.
   - `Email`: Should have valid email address syntax.
   - `Phone Number`: Should be 10 digits long in the format `xxx-xxx-xxxx`.
   - `Credit Card Number`: Should be 19 digits long and contain only digits.

   Upon successful submission, the service returns a unique `OrderId`.

3. **Order confirmation page**: Displays the details of the order specified by `OrderId`, including item details and personal information provided in the checkout form.

4. **Service KPIs**: Provides a method to get key performance indicators (KPIs) for the service, including:
   - Number of requests
   - Maximum delay of request execution, segmented by request type

   The KPIs are available through Spring Boot Actuator.


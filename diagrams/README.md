# UML Diagrams

| Diagram | Description |
|---------|-------------|
| [Class Diagram](JavazonClassDiagram.png) | Shows the structure and relationship between classes |
| [Use Case Diagram](JavazonUseCaseDiagram.pdf) | Shows the user-system interaction |
| [State Diagram](JavazonStateDiagram.pdf) | Describes the states and transitions of the systems
| [Sequence Diagram](JavazonSequenceDiagram.pdf) | Outlines object interactions and flow 

# Class Diagram

Javazon will serve as the main driver program. The GUI will have different options depending on the user’s role. The user’s role will be stored as a userRole with the enum userRoles defining the possible values of BUYER, SELLER, or GUEST. [Class Diagram](https://github.com/jack-200/CS151-Javazon/blob/main/diagrams/JavazonClassDiagram.png)
- Javazon.java

**Features:**

**Browse through a wide range of products and their details**

Product information is stored in a text file. The ProductManager class will manage any methods related to the Product class.
- Products.txt
- Product.java
- ProductManager.java

**Registration and profile viewing for buyers and sellers**

The user can choose to sign in as a buyer or seller. The user can update their personal information stored in the Account class. Accounts are stored in the text file and will be loaded into an ArrayList of Accounts in the AccountManager class.
- Accounts.txt
- Account.java
- AccountManager.java

**Buyer Shopping Cart and Checkout**

The user can add and remove products from the shopping cart or wish list. The user can select the payment method, select buy for oneself/send a gift, and choose the address.
- ShoppingCart.java

**Use product filters to quickly search and narrow down the choices**

Allow the user to select their interests when creating an account. Recommend products based on user interest, browsing history, and purchase history. Recommend new products, seasonal products, and products on sale.

**Sort the products by popularity, cost, and brand**

Allow the user to select a star rating and leave a review. Rank the products based on the average rating.
- Review.java


# Use Case Diagram

Users looking to browse the store and add items to the shopping cart will be allowed to do so without signing into an account. On the other hand, a seller will have the option to sell an item on the marketplace. Both buyers and sellers will need to enter their correct login credentials to do role-specific tasks like checking out and selling items.
[Use Case Diagram](https://github.com/jack-200/CS151-Javazon/blob/main/diagrams/JavazonUseCaseDiagram.pdf)

# State Diagram

If the user was browsing as a guest and finds an item they want to buy: sign up if they don’t have an account, sign in, and return to the product information page(The user does not need to search for the item again)Then the user chooses to add the item to the shopping cart or wish list.

**Payment process:**

- Confirm order: review the items in the shopping cart (item, quantity, cost) before proceeding to checkout.
- Proceed to checkout: provide shipping address and payment information.
- Once payment authorization is complete, place the order.

[State Diagram](https://github.com/jack-200/CS151-Javazon/blob/main/diagrams/JavazonStateDiagram.pdf)

# Sequence Diagram

The sequence diagram depicts a user's journey on the "Javazon" online marketplace. Initially, the user either creates an account or logs in. After accessing the platform, the user views products, selects one for detailed information, and can leave a review. The chosen product can be added to the shopping cart, and the user proceeds to checkout. The interaction concludes with a transaction confirmation.

[Sequence Diagram](https://github.com/jack-200/CS151-Javazon/blob/main/diagrams/JavazonSequenceDiagram.pdf)
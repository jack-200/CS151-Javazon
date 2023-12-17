# CS151-Javazon

An Amazon-inspired e-commerce Java application by Jack Liang, Youkyoung Kim, and Jenny Le.

![image](https://github.com/jack-200/CS151-Javazon/assets/86848773/bc6b422b-9412-47ab-86ab-4b54e84e60ee)

### Breakdown of Responsibilities & Contributions:

**Diagrams:**

- [Class](diagrams/JavazonClassDiagram.png): Jack
- [Sequence](diagrams/JavazonSequenceDiagram.pdf): Jenny
- [State](diagrams/JavazonStateDiagram.pdf): Youkyoung
- [Use Case](diagrams/JavazonUseCaseDiagram.pdf): Jack

**User Features:**

- Users can create accounts that are stored in a database (Youkyoung).
    - Input validation on information entered by the user.
    - Sets a minimum for password complexity.
    - Saves new accounts to an ArrayList, which contains all existing accounts.
    - Serialized the data and saved it to a text file.
- Users can log in to accounts stored in the database (Youkyoung).
    - Confirm that the username exists and matches the provided password.
    - Adjust the size of the scene and inner contents, achieved by wrapping AnchorPane inside StackPane.
- Users are notified of program flow through toast notifications (Jack).
- Users can provide a rating and review for products (Jack).

**Product Features:**

- Main Product Page: Jack
    - Dynamic layout updates based on window dimensions.
    - Hosts and connects to various buttons and options.
    - Sort by Price and Ratings (Youkyoung)
- Individual Product Pages: Youkyoung
    - Click on the product name, which serves as a hyperlink, to open the product information page when pressed. Here,
      users can view product details, add reviews, make a purchase, or add the product to the shopping cart. After
      selecting the quantity of the product, users can proceed to checkout.
- Sell Product Page: Youkyoung
    - Sign in and select Seller mode to sell products. Add another product or return to the main page to see the new
      product added to the end of the grid pane.
    - To create a scene that dynamically increases in size, a VBox wrapped in ScrollPane was used to repeatedly load
      the SellProduct scene to the end of the VBox whenever the user presses the add button.
- My Market Page: Youkyoung
    - When the user is in seller mode, they can go to My Market to see a list of products they are selling.
- Shopping Cart: Jenny
    - Products selected to be added to the shopping cart will be stored for checkout. From product pages, the quantity
      in the shopping cart can be adjusted or removed entirely.
- Checkout Page: Jenny
    - Products added to cart can be removed, and the total amount is displayed. Each individual product has their name,
      price, and quantity displayed. Upon checkout, the cart will be cleared and a message will be displayed to confirm
      the checkout.

**Problem and Solution:**

Serialization (Youkyoung)
Serialization was done to convert the arraylist of account and product objects into byte code and save them into the
text file. However, whenever I added new attributes to the class, I had to clear the current serialized data because the
change in the class structure made deserialization not work. If I can start the project again, I would choose a
different method to store and manage data like JSON or MySQL. Then, the stored data would’ve been easier to read and
edit and I wouldn’t have to manually enter data to create objects.

Seamless Window Transitions (Jack)
When clicking on an element that triggers a scene change, such as navigating to an individual product page from the main
product page, the program will smoothly update the current window GUI with the new scene. However, on the journey to
achieving such a seamless interaction, unintended side effects emerged, such as the inability to return properly to the
main product page. The solution involved creating a callable function that could easily facilitate the return to the
main product page. This addressed the issue that the main product page is coded in Java, as opposed to FXML, making
navigation back to it more challenging.

**Assumptions, Operating Environments, and Intended Usage:**

The program has been tested on Windows 10/11 and macOS.

**Operations: Operations are listed for each intended user in list format.**

Guest:

- Browse as guest mode with limited functionality.

Buyer:

- Sign in and choose the Buyer mode.
- Use the search bar or sorting options to quickly locate a desired product.
- Choose a product of interest.
- Specify the quantity and then proceed to either Add to Cart or Buy Now.
- On the Shopping Cart page, review cart contents before proceeding to checkout.

Seller:

- Sign in and select Seller mode.
- Fill out all product information and provide an image.
- Check if the product got added by returning to the main page or checking My Market.

Buyer & Seller:

- Provide a rating and review for products.

**Steps to run Javazon:**

1. `git clone https://github.com/jack-200/CS151-Javazon.git`
2. Open project in IntelliJ IDEA to handle all the dependencies automatically.
3. Run Javazon.java in the IDE.

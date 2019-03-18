# Assignment5:Sofeware Design (design information) 
1. A grocery list consists of items the users want to buy at a grocery store. The application
must allow users to add items to a list, delete items from a list, and change the quantity
of items in the list (e.g., change from one to two pounds of apples).
* Grocery List Manager that you can add, remove, and rename shopping list.   
* Grocery List class that you can add,delete,and update the item (change the quantity and unit).
* item class for an item, with attributes, itemType, itemName, itemQuantity, unit, and isChecked.


2. The application must contain a database (DB) of items and corresponding item types .
* To realize this requirement, the Database has Items with itemName and itemType.

3. Users must be able to add items to a list by picking them from a hierarchical list, where
the first level is the item type (e.g., cereal), and the second level is the name of the
actual item (e.g., shredded wheat). After adding an item, users must be able to specify a
quantity for that item.
* User can add item with name and type.  
* User can using QuantityofItem to change quantity and unit.
* In Grocery List, by accessDatabase to use getItemType operation to list all of type grocery products on UI.
* In Grocery List, by accessDatabase to use getItemByType operation to list all the items in such item type on UI.

4. Users must also be able to specify an item by typing its name. In this case, the
application must look in its DB for items with similar names and ask the users, for each
of them, whether that is the item they intended to add. If a match cannot be found, the
application must ask the user to select a type for the item and then save the new item,
together with its type, in its DB.
* In Grocery List, by accessDatabase to use getItemByName operation that database is able to search the item name in the database.if item is not exist, then itemisAdded kick in that allow add new item name and types.

5. Lists must be saved automatically and immediately after they are modified.
* Since List is in the grocery list manager class, so create list is automatically save and any change will automatically save.

6. Users must be able to check off items in a list (without deleting them).
* In Grocery List class, CheckedItem operation to check off item, Item class have isChecked attribute. 
 
7. Users must also be able to clear all the check-off marks in a list at once.
* checkOffAll and clearAll allow user to check off and clear the check-off of items at onece. 

8. Check-off marks for a list are persistent and must also be saved immediately.
* Checked attribute and CheckedList method will automatically save persistently after check-off.

9. The application must present the items in a list grouped by type, so as to allow users to
shop for a specific type of products at once (i.e., without having to go back and forth
between aisles).
* In Grocery List, by accessDatabase to added getItemTypes and getItemsByType on UI which can use by UI to get list of item types and item.

10. The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly
farmer’s market list”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete lists.
* Grocery manager class have create, rename and delete operation.

11. The User Interface (UI) must be intuitive and responsive.
* Not considered because it does not affect the design directly.
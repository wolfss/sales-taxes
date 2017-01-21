# sales-taxes

***FUNCTIONAL SPECIFICATIONS***

Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

When I purchase items I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.

Write an application that prints out the receipt details for these shopping baskets...

INPUT:

Input 1:
1 book at 12.49
1 music CD at 14.99
1 chocolate bar at 0.85

Input 2:
1 imported box of chocolates at 10.00
1 imported bottle of perfume at 47.50

Input 3:
1 imported bottle of perfume at 27.99
1 bottle of perfume at 18.99
1 packet of headache pills at 9.75
1 box of imported chocolates at 11.25

OUTPUT

Output 1:
1 book : 12.49
1 music CD: 16.49
1 chocolate bar: 0.85
Sales Taxes: 1.50
Total: 29.83

Output 2:
1 imported box of chocolates: 10.50
1 imported bottle of perfume: 54.65
Sales Taxes: 7.65
Total: 65.15

Output 3:
1 imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 9.75
1 imported box of chocolates: 11.85
Sales Taxes: 6.70
Total: 74.68


*** Comments ***

In order to satisfy the requirements, a service class named SalesTaxesService.java has been created.
That service exposes a single method for calculating the receipt.
Starts reading the list of goods reported in the file located at the path specified by the first input parameter and produces the output at the path specified by the second input parameter.

Service class contains the whole logic.
It parses the input file creating the basket which corresponds to a List of Good objects.
The basket is then cycled in order to calculate the taxes to apply for each Good.
Good quantity, description, taxed price are printed within a StringBuilder object: the receipt.
The receipt is finalized with the sales taxes and the grand total.
Finally, the resulting receipt is printed out in the output file.

NOTE:
A feature has been introduced for recognizing and aggregating the duplicated goods present in the list. 

A set of test cases has been implemented in SalesTaxesTest.java.
A test for each example reported in the functional specification plus a test for testing the feature.  

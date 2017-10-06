Narrative:
As a logged-in user
I would like to be able to make reservations for collective tickets for certain routes in certain time periods
in order to use any discounts.

Scenario: Reservation is not possible for the given period, because there are no offers available
Given logged in customer
And possible reservation between 10:00 and 10:30
When reserve a shared taxi
Then the reservation is not successful

Scenario: Successful reservation of a collective taxi ride
Given possible reservation between 10:00 and 10:30
And between 10:00 and 10:30 the price is 30,50 € with 0 passengers
When reserve a shared taxi
Then the reservation is successful
And between 10:00 and 10:30 the price is 30,50 € at 0 passengers

Scenario: View updated prices for already booked reservations
Given reservation already made
And between 10:00 and 10:15 the price is 30,50 € with 0 passengers
And between 10:15 and 10:30 the price is 15,50 € with 2 passengers
When the user open the reservation page
Then the reservation is successful
And between 10:00 and 10:15 Uhr the price is 30,50 € at 0 passengers
And between 10:15 and 10:30 Uhr the price is 15,50 € at 2 passengers

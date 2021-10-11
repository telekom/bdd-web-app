Narrative:
As a logged-in user
I would like to be able to make reservations for collective tickets for certain routes in certain time periods
in order to use any discounts.

Scenario: Reservation is not possible for the given period, because there are no offers available
Given logged in customer user
And example reservation between 10:00 and 10:30
When reserve a shared taxi
Then the reservation is not successful

Scenario: View updated prices for already booked reservations with other passengers
Given the price is <price> € with <passengers> other passengers between <startTime> and <endTime>
When reserve a shared taxi
Then the reservation is successful
And between <startTime> and <endTime> the price is <price> at <passengers> passengers

Examples:
| price | passengers | startTime | endTime |
| 30,50 | 0          | 10:00     | 10:30   |
| 20    | 2          | 10:00     | 10:30   |
| 17    | 3          | 10:30     | 11:00   |

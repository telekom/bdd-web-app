Feature: Reservation
  As a logged-in user
  I would like to be able to make reservations for collective tickets for certain routes in certain time periods
  in order to use any discounts.

  Scenario: Reservation is not possible for the given period, because there are no offers available
    Given example reservation between 10:00 and 10:30
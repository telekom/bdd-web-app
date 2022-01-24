Narrative:
As a framework user
I would like to filter on scenarios
to execute some tests in different contexts

Scenario: Filter based on execute successful
Meta: @execute successful
When set parameter to x
Then parameter is x

Scenario: Filter based on execute error
Meta: @execute error
When set parameter to x
Then parameter is y
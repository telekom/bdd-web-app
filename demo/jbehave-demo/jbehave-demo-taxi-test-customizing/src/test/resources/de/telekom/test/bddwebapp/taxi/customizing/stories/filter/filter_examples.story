Narrative:
As a framework user
I would like to filter on examples
to execute some tests in different contexts

Scenario: Filter based on examples
When set parameter to <param>
Then parameter is <expectedParam>

Examples:
| param | expectedParam | Meta:
| x     | x             | @execute successful
| y     | x             | @execute error
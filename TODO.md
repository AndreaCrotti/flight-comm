
# TODO

## High-level

### Input
- Take input from stdin
- covert input to data model
- store data in db

### Update
- Take update from stdin
- covert to update request
- update record in db

- take deletion event
- delete matching event from db

### Output
- accept a time from stdin
- output the status of all aircraft at that given time

## Low-level

- test multiple saves
- test upsert - x
- make print helper object and dependency of input reader - x
- make update confirm if update or insert was done - x
- print confirmation of insert - x
- print confirmation of upsert - x
- make event readers recursive - x
- help functions
- switch modes
- exit
- print input format for each mode
- validate input / catch errors
- global error catcher ?
- make sure find all results are sorted - x

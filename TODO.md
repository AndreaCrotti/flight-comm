
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
- test upsert
- make update confirm if update or insert was done
- print confirmation of insert
- print confirmation of upsert
- make event readers recursive
- help functions
- switch modes
- exit
- make print helper object and dependency of input reader
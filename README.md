# Flight Comm

A Command Line Flight Control application, for accepting, 
updating and deleting Flight Events.

## Running Instructions

### Prerequisites

To run this application requires:

>- [Java 17](https://openjdk.java.net/projects/jdk/17/)
>- [Maven 3.6+](https://maven.apache.org/)
>- [MongoDB]() or [Docker](https://docs.docker.com/)

### MongoDB

This application uses MongoDB running on its default port: **_27017_**.

If you do not have MongoDB already installed it can be easily ran in 
Docker with command:

```shell
docker run -d -p 27017:27017 --name mongo mongo:latest
```

### Maven && Spring-Boot

To project uses Maven and Spring boot. To run the application use command:
```shell
mvn spring-boot:run
```

## Usage

Interaction with the app is done directly through the command-line console.
When you first boot up the app you will be presented with the main menu.

```shell
Select input mode mode:
1 : Input new Flight Event
2 : Update Flight Event
3 : Delete Flight Event
4 : Get Flight Status
5 : (Dev mode) Run test data
```

From here you can select which input mode you wish to use.

### 1. Input new Flight Event

This allows for inputting of new flight events, in the following format:

```
$plane-id $model $origin $destination $event-type $timestamp $fuel-delta
```

For example:
```shell
F222 747 DUBLIN LONDON Re-Fuel 2021-03-29T10:00:00 200
```

### 2. Update Flight Event

This allows for updating of existing flight events. Updates follow the same format as the 
new flight events, above.

```
$plane-id $model $origin $destination $event-type $timestamp $fuel-delta
```

__Note:__ If an existing flight event is not found for your update request, 
a new event will be created.

### 3. Delete Flight Event

This allows for deleting of existing flight events. Delete request should be in format:

```shell
$plane-id $timestamp
```

This will remove any flight events for the given `plane-id` at the given `timestamp`.
If no matching flight event is found, nothing is deleted. If multiple matching flight events 
are found, they will all be deleted.

### 4. Get Flight Status

This produces a report of all flights with 
their flight status and fuel levels for any given point in time. To get a
status report input a timestamp in format: `yyyy-MM-ddTHH:mm:ss`.

For example:
```shell
Get Flight Status - enter timestamp in format :
yyyy-MM-ddTHH:mm:ss
2022-05-09T22:00:00
====
F551 Landed -300
F324 In-Flight 0
F123 In-Flight 0
F222 Awaiting-Takeoff 400
====
```


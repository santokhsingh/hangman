# Hangaman Spring Boot Service

Hangman service exposes three REST API endpoints 

 - API to start new game
 - API to retrieve started game
 - API to modify the game state by guessing new character

# Starting the service

Start service from command line using Gradle or from with-in the IDE

    ./gradlew bootRun

## API Design

 1. Start new game API
	 - Method: POST 
	 - Uri: : http:/localhost:80801/hangman
	 - Status Codes: 200 (Ok), 404 (NOT_FOUND), 400 (BAD_REQUEST)
	 - Sample Response: 
		 - `{
    "stateId": "b5edb1be-3675-4fc1-90d3-18c332b97b70",
    "wrongGuesses": "",
    "guessesLeft": 10,
    "solved": false,
    "gameSolution": "__________"}`
 2. Retrieve game API
	 - Method: GET 
	 - Uri: : http:/localhost:80801/hangman/{stateid}
	 - Status Codes: 200 (Ok), 404 (NOT_FOUND), 400 (BAD_REQUEST)
	 - Sample Response:
		- `{
    "stateId": "b5edb1be-3675-4fc1-90d3-18c332b97b70",
    "wrongGuesses": "x",
    "guessesLeft": 9,
    "solved": false,
    "gameSolution": "ha_______"}`
 3. Make a guess API
	 - Method: PUT 
	 - Uri: : http:/localhost:80801/hangman/{stateid}/{guess}
	 - Status Codes: 200 (Ok), 404 (NOT_FOUND), 400 (BAD_REQUEST)
	 - Sample Response:
		- `{
    "stateId": "b5edb1be-3675-4fc1-90d3-18c332b97b70",
    "wrongGuesses": "xkl",
    "guessesLeft": 5,
    "solved": true,
    "gameSolution": "hangman"}`

## Scalability 

 - Assuming the service is to be run in a clustered environment behind load balancer
 - Perhaps adding API Gateway layer for routing, throttling (by ip), monitoring, analytics and security
 - Distributable caching layer (write through cache) with LRU eviction policy to maintain hot games in cache

## Persistence 

 -  Obvious choice is NoSql DB, since we don't need JOINS and transaction ACID kind of properties to persist game/game state, using stateId as partition key 
	 - Three replicas/replication factor for failover
	 - Schema: 
		 - stateid: UUID (partition key)
		 - content: Varchar
		 - expire_time
	 - Enforce time to live for automatic cleanup of stale data (30 days) based on expire_time



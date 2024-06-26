*** Settings ***
Library           Collections
Library           RequestsLibrary
Test Timeout      30 seconds

*** Test Cases ***
addActorPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    name=WillSmith  actorId=ws1
    ${resp}=    Put Request    localhost    /api/v1/addActor    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addActorPass2
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    name=KevinBacon  actorId=nm0000102
    ${resp}=    Put Request    localhost    /api/v1/addActor    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addActorPass3
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    name=TomCruise  actorId=tc1
    ${resp}=    Put Request    localhost    /api/v1/addActor    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addActorFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorName=WillSmith   actorId=wl1 
    ${resp}=    Put Request    localhost    /api/v1/addActor    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    400

addMoviePass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    name=KingRichard  movieId=kr1
    ${resp}=    Put Request    localhost    /api/v1/addMovie    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addMoviePass2
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    name=AFewGoodMen  movieId=fgm1
    ${resp}=    Put Request    localhost    /api/v1/addMovie    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addMovieFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    name=KingRichard  Id=kr1
    ${resp}=    Put Request    localhost    /api/v1/addMovie    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    400

addRelationshipPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=kr1  actorId=ws1 
    ${resp}=    Put Request    localhost    /api/v1/addRelationship    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addRelationshipPass2
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=fgm1  actorId=tc1 
    ${resp}=    Put Request    localhost    /api/v1/addRelationship    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addRelationshipPass3
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=fgm1  actorId=nm0000102 
    ${resp}=    Put Request    localhost    /api/v1/addRelationship    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addRelationshipFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=kr1  actorId=wsdf8 
    ${resp}=    Put Request    localhost    /api/v1/addRelationship    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    404

addMovieRatingPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=kr1  rating=8.5 
    ${resp}=    Put Request    localhost    /api/v1/addMovieRating    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

addMovieRatingFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=kr2  rating=8.5 
    ${resp}=    Put Request    localhost    /api/v1/addMovieRating    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    404

getActorPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=ws1  
    ${resp}=    Get Request    localhost    /api/v1/getActor    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200	

getActorFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=WillSmith  
    ${resp}=    Get Request    localhost    /api/v1/getActor    json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    404

getMoviePass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=kr1
    ${resp}=    Get Request    localhost    /api/v1/getMovie   json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

getMovieFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=kr1
    ${resp}=    Get Request    localhost    /api/v1/getMovie   json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    400

hasRelationshipPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=kr1  actorId=ws1 
    ${resp}=    Get Request    localhost    /api/v1/hasRelationship   json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

hasRelationshipFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    movieId=kr2  actorId=ws1 
    ${resp}=    Get Request    localhost    /api/v1/hasRelationship   json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    404

computeBaconNumberPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=nm0000102 
    ${resp}=    Get Request    localhost    /api/v1/computeBaconNumber  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

computeBaconNumberPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=tc1
    ${resp}=    Get Request    localhost    /api/v1/computeBaconNumber  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

computeBaconNumberFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=ws1 
    ${resp}=    Get Request    localhost    /api/v1/computeBaconNumber  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    404

computeBaconPathPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=tc1 
    ${resp}=    Get Request    localhost    /api/v1/computeBaconPath  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    200

computeBaconPathFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=notInDatabase 
    ${resp}=    Get Request    localhost    /api/v1/computeBaconPath  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}    404

getNumberOfMoviesPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    actorId=tc1 
    ${resp}=    Get Request    localhost    /api/v1/getNumberOfMovies  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}   200

getNumberOfMoviesFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    notGoodParameter=tc1 
    ${resp}=    Get Request    localhost    /api/v1/getNumberOfMovies  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}   400

getMoviesOfRatingPass
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    rating=8.5 
    ${resp}=    Get Request    localhost    /api/v1/getMoviesOfRating  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}   200

getMoviesOfRatingFail
    Create Session    localhost    http://localhost:8080
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${params}=    Create Dictionary    BadParameter=8.5 
    ${resp}=    Get Request    localhost    /api/v1/getMoviesOfRating  json=${params}    headers=${headers}
    Should Be Equal As Strings    ${resp.status_code}  400
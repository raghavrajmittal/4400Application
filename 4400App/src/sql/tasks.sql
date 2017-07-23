
-- CS 4400 SQL Queries for Tasks:
-- Contact Raghav Mittal for any queries (pun intended) :P


-- 1.0 Login (Raghav)
SELECT Email, IsSuspended, IsManager
FROM USER
WHERE Email = "maya2@gmail.com" AND Password = 2;

-- 2.0 Registration (Raghav)
SELECT Email 
FROM USER
WHERE Email = "raghav@gmail.com";

INSERT INTO USER
VALUES ("raghav2@gmail.com", 2, now(), FALSE, FALSE);


-- 3.0 Regular User Welcome (Raghav)

 
SELECT DISTINCT Name
FROM CITY as C, REVIEWABLE_ENTITY as E-- drop down for city
WHERE C.CityID = E.EntityID AND E.IsPending = FALSE;

SELECT *
FROM CATEGORY ;-- drop down for category

DELETE FROM USER
WHERE Email = "email_field"; -- delete account


-- 4.0 All Cities List (Raghav)
SELECT res1.CityID, res1.Name, Country, State, res1.avgRat, res1.totalRat, res2.numAttr
FROM (


	(SELECT C.CityID, C.Name, C.Country, C.State, AVG(Rating) as avgRat, COUNT(Rating) as totalRat
	FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E
	WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = FALSE
	GROUP BY C.CityID ) as res1
    
    left join 
	
	(SELECT A.LocatedIn, COUNT(*) as numAttr
	FROM ATTRACTION AS A, REVIEWABLE_ENTITY as E
	WHERE A.AttrID = E.EntityID and E.IsPending = FALSE
	GROUP BY A.LocatedIn) as res2
    
    on res1.CityID = res2.LocatedIn
    order by res1.Name asc) ;
    -- also order it by something?


-- 4.1 New City Form (R)
INSERT INTO REVIEWABLE_ENTITY
VALUES ("username_field", EntityID, now(), !IsManager);
INSERT INTO CITY
VALUES (EntityID, "name_field", 'country_field', 'state_field');
INSERT INTO REVIEW
VALUES ("username_field", EntityID, now(), "comment_field", "rating_field");


-- 5.0 Sample City Page (R)
SELECT res1.AttrID, Name, Address, Category, avgRat, numRat
FROM(

	(SELECT A.AttrID, LocatedIn, Name, Address
	FROM ATTRACTION as A, REVIEWABLE_ENTITY as E
	WHERE LocatedIn = "cityid_field" and A.AttrID = E.EntityID and E.IsPending = FALSE) as res1
inner join 
	(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat
	FROM ATTRACTION AS A, REVIEW AS R
	WHERE A.AttrID = R.EntityID AND A.LocatedIn = "cityid_field"
	GROUP BY A.AttrID) as res2
on res1.AttrID = res2.AttrID 

inner join 
	(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category
	FROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E
	WHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE
	GROUP BY A.AttrID) as res3
on res1.AttrID = res3.AttrID);



-- 5.1 City Review Form/ Update Form
SELECT Comment, Rating
FROM REVIEW
WHERE Email = "email_field" AND EntityID = "entityID_field";

UPDATE REVIEW
SET Rating = "rating_field", Comment="comment_field", DateSubmitted = now()
WHERE Email = "email_field" AND EntityID = "entityID_field";


INSERT INTO REVIEW
VALUES ("email_field", "entityID_field", now(), "comment_field", "rating_field");

DELETE FROM REVIEW
WHERE Email = "email_field" AND EntityID = "entityID_field";



-- 5.2 City's Review Page
SELECT Email, Rating, Comment
FROM REVIEW AS R
WHERE "cityID_field" = R.EntityID
ORDER BY Rating desc;


-- 5.3 Sample City Page narrowed down by category
SELECT res1.AttrID, Name, Address, Category, avgRat, numRat
FROM(

	(SELECT A.AttrID, LocatedIn, Name, Address
	FROM ATTRACTION as A, REVIEWABLE_ENTITY as E
	WHERE LocatedIn = "CityID_field" and A.AttrID = E.EntityID and E.IsPending = FALSE) as res1
inner join 
	(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat
	FROM ATTRACTION AS A, REVIEW AS R
	WHERE A.AttrID = R.EntityID AND A.LocatedIn = "CityID_field"
	GROUP BY A.AttrID) as res2
on res1.AttrID = res2.AttrID 

inner join 
	(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category
	FROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E
	WHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE
	GROUP BY A.AttrID) as res3
on res1.AttrID = res3.AttrID)

inner join
	(SELECT AttrID
	FROM FALLS_UNDER
	WHERE Category = "category_field") as res4
ON res1.AttrID = res4.AttrID

order by numRat desc;



-- 6.0 User's Review Page
(SELECT C.cityID as EntityID, C.Name, R.Rating, R.Comment
FROM REVIEW AS R, CITY AS C
WHERE "Email_field" = R.Email AND C.CityID = R.EntityID)
UNION
(SELECT A.AttrID as EntityID, A.Name , R.Rating,  R.Comment
FROM REVIEW AS R, ATTRACTION AS A
WHERE "Email_field" = R.Email AND A.AttrID = R.EntityID);
-- do we display IsPending reviews?




-- 7.0 Attraction's List
SELECT res1.AttrID, res1.Name, Category, res3.Name, res1.LocatedIn, avgRat, numRat
from
	(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat
	FROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E
	WHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE
	GROUP BY R.EntityID) as res1
inner join 
	(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category
	FROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E
	WHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE
	GROUP BY A.AttrID) as res2
on res1.AttrID = res2.AttrID

inner join
	(SELECT Name, CityID
    FROM CITY) as res3
on res1.LocatedIn = res3.CityID
order by res3.Name asc;


-- 7.1 New Attraction Form
SELECT DISTINCT Name
FROM CITY as C, REVIEWABLE_ENTITY as E-- drop down for city
WHERE C.CityID = E.EntityID AND E.IsPending = FALSE;

INSERT INTO REVIEWABLE_ENTITY
VALUES ("email_field", "entityID_field", now(), !IsManager);
INSERT INTO ATTRACTION
VALUES ("entityID_field", "LocatedIn_field", "name_field", "Address_field", "description_field");
INSERT INTO REVIEW
VALUES ("Email_field", "entityID_field", now(), "comment_field", "rating_field");
INSERT INTO FALLS_UNDER
VALUES ("entityID_field", "category_field"); -- do this in a for loop in the java code
INSERT INTO CONTACT_INFO
VALUES ("entityID_field", "Contact_method_field", "method_value_field"); -- only if it exists?
INSERT INTO HOURS_OF_OPERATION
VALUES ("entityID_field", "day_of_the_week_field", "open_time_field", "close_time_field");

-- 8.0 Sample Attraction Page
SELECT Name, Address, Description
FROM ATTRACTION
WHERE AttrID = "attrID_field";

SELECT AVG(Rating) as avgRat, COUNT(Rating) as numRat
FROM REVIEW
WHERE EntityID = "attrID_field";

SELECT GROUP_CONCAT(DayOfTheWeek + ": " + OpenTime + "-" + CloseTime + " ") as HoursOfoperation
FROM HOURS_OF_OPERATION
WHERE AttrID = "attrID_field"
GROUP by AttrID;

SELECT GROUP_CONCAT(ContactMethod + ":" +MethodValue + " ") as ContactInfo
FROM CONTACT_INFO
WHERE AttrID = "attrID_field";

SELECT GROUP_CONCAT(Category) as Category
FROM FALLS_UNDER
WHERE AttrID = "attrID_field";


-- 8.1 Attraction Review/update form
SELECT Comment, Rating
FROM REVIEW
WHERE Email = "email_field" AND EntityID = "entityID_field";

UPDATE REVIEW
SET Rating="rating_field", Comment="comment_field", DateSubmitted = now()
WHERE Email = "email_field" AND EntityID = "entityID_field";

INSERT INTO REVIEW
VALUES ("email_field", "entityID_field", now(), "comment_field", "rating_field");

DELETE FROM REVIEW
WHERE Email = "email_field" AND EntityID = "entityID_field";


-- 8.2 Attraction's Review Page
SELECT SubmittedBy, Rating, Comment
FROM REVIEW
WHERE EntityID = "attrID_field"
ORDER BY Rating DESC;
-- options to sort too


-- 9.0 Manager Welcome Page
 
SELECT DISTINCT Name
FROM CITY as C, REVIEWABLE_ENTITY as E-- drop down for city
WHERE C.CityID = E.EntityID AND E.IsPending = FALSE;

SELECT *
FROM CATEGORY ;-- drop down for category

DELETE FROM USER
WHERE Email = "email_field"; -- delete accountCITY


-- Category Page
SELECT res2.Category, res2.numAttr
FROM (

(SELECT CName
FROM CATEGORY) as res1

inner join

(SELECT F.Category, COUNT(F.AttrID) as numAttr
FROM FALLS_UNDER AS F, REVIEWABLE_ENTITY AS E
WHERE F.AttrID = E.EntityID and E.IsPending = FALSE
GROUP BY F.Category) as res2

on res1.CName = res2.Category)
order by Category ASC;
-- checks for whether deletign the category will delete an attraction

INSERT INTO CATEGORY
VALUES ("category_field");

DELETE FROM CATEGORY
WHERE CName = 'category_field';

UPDATE CATEGORY
SET CName = "new_category_field"
WHERE CName = "old_category_field";

-- 10.0/ 10.1 user's list
SELECT Email, DateJoined, IsSuspended, IsManager
FROM USER
ORDER BY DateJoined ASC;  -- sorting is needed

UPDATE USER
SET IsManager = TRUE
WHERE Email = "email_field";

UPDATE USER
SET IsManager = FALSE
WHERE Email = "email_field";

UPDATE USER
SET IsSuspended = TRUE
WHERE Email = "email_field";

UPDATE USER
SET IsSuspended = FALSE
WHERE Email = "email_field";

DELETE FROM USER
WHERE Email = "email_field";



-- 11.0 Pending Cities List
SELECT E.EntityID, C.Name, C.Country, E.SubmittedBy, R.Rating, R.Comment
FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E
WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = TRUE
ORDER by C.Name ASC;

UPDATE REVIEWABLE_ENTITY
SET IsPending = FALSE
WHERE EntityID = "entityID_field";

DELETE FROM REVIEWABLE_ENTITY
WHERE EntityID = "entityID_field";


-- 12.0 Pending Attraction's Page

SELECT res1.AttrID, Name, LocatedIn, Address, Category, Description, HoursOfOperation, ContactInfo, SubmittedBy, Rating, Comment
from

	(SELECT A.AttrID, A.Name, A.LocatedIn, A.Address, A.Description, E.SubmittedBy, R.Rating, R.Comment
	FROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E
	WHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = TRUE) as res1
inner join 
	(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category
	FROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E
	WHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = TRUE
	GROUP BY A.AttrID) as res2
on res1.AttrID = res2.AttrID

left join
	(SELECT AttrID, GROUP_CONCAT(DayOfTheWeek, ": ", OpenTime, "-", CloseTime ," ") as HoursOfoperation
	FROM HOURS_OF_OPERATION
	GROUP by AttrID) as res3
on res1.AttrID = res3.AttrID

left join
	(SELECT AttrID, GROUP_CONCAT(ContactMethod, ":" ,MethodValue , " ") as ContactInfo
	FROM CONTACT_INFO
    GROUP BY AttrID) as res4
on res1.AttrID = res4.AttrID

order by res1.LocatedIn asc;

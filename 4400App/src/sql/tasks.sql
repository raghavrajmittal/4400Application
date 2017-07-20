-- CS 4400 SQL Queries for Tasks:

-- 1.0 Login (Raghav)
SELECT Email, IsSuspended, IsManager
FROM USER
WHERE Email = " " AND Password = " ";


-- 2.0 Registration (Raghav)
SELECT Email 
FROM USER
WHERE Email = " ";

INSERT INTO USER
VALUES ("email", "Password", now(), FALSE, FALSE)


-- 3.0 Regular User Welcome (Raghav)
SELECT DISTINCT NAME
FROM CITY -- drop down for city

SELECT *
FROM CATEGORY -- drop down for category

DELETE FROM USER
WHERE Email = "" -- delete account


-- 4.0 All Cities List (Raghav)
(
	SELECT C.Name, C.Country, C.State, AVG(Rating), COUNT(Rating)
	FROM CITY AS C, REVIEW AS R
	WHERE C.CityID = R.EntityID 
	GROUP BY DISTINCT C.CityID
) inner join (
	SELECT C.Name, COUNT(*)
	FROM CITY AS T, ATTRACTION AS A
	WHERE T.CityID = A.LocatedIn
	GROUP BY DISTINCT T.NAME
) on C.Name = T.Name; -- TEST IT OUT (Also need to add a check to not display isPending cities)


-- 4.1 New City Form (R)
INSERT INTO REVIEWABLE_ENTITY
VALUES ("username_field", EntityID, now(), TRUE)
INSERT INTO CITY
VALUES (EntityID, "name_field", 'country_field', 'state_field')
-- !TODO Check if city already exists


-- 5.0 Sample City Page (R)
(
	SELECT AttrID, LocatedIn, Name, Address, Category
	FROM ATTRACTION, FALLS_UNDER
	WHERE LocatedIn = "CityID_field" AND ATTRACTION.AttrID = FALLS_UNDER.AttrID
) inner join (	
	SELECT A.AttrID, AVG(Rating), COUNT(Rating)
	FROM ATTRACTION AS A, REVIEW AS R
	WHERE A.AttrID = R.EntityID AND LocatedIn = "CityID_field"
	GROUP BY DISTINCT A.AttrID
) on AttrID = A.AttrID;
--check this, seems different from All cities list (4.0)





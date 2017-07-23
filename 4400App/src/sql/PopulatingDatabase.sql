-- populating the DB

INSERT INTO User
VALUES ("maya@gmail.com", 1, now(), FALSE, TRUE);

INSERT INTO User
VALUES ("raghav@gmail.com", 938491003, now(), FALSE, TRUE);

INSERT INTO user
VALUES ("pravan@gmail.com", 3, now(), FALSE, TRUE);
 
INSERT INTO user
VALUES ("alyssa@gmail.com", 4, now(), FALSE, TRUE);
 
INSERT INTO user
VALUES ("kendall@gmail.com", 5, now(), FALSE, FALSE);
 
INSERT INTO user
VALUES ("annalise@gmail.com",6 , now(), TRUE, FALSE);
 
INSERT INTO user
VALUES ("bent@gmail.com", 7, now(), FALSE, FALSE);
 
INSERT INTO user
VALUES ("adya@gmail.com", 8, now(), TRUE, FALSE);


INSERT INTO user
VALUES ("mattl@gmail.com", 9, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("sarahg@gmail.com", 10, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("joseph@gmail.com", 11, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("walker@gmail.com", 12, now(), FALSE, TRUE);
INSERT INTO user
VALUES ("anthony@gmail.com", 13, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("chloe@gmail.com", 14, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("eugenia@gmail.com", 15, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("chris@gmail.com", 16, now(), TRUE, FALSE);

INSERT INTO user
VALUES ("thy@gmail.com", 17, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("xing@gmail.com", 18, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("abbie@gmail.com", 19, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("elizabeth@gmail.com", 20, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("kyra@gmail.com", 21, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("ruchi@gmail.com", 22, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("michael@gmail.com", 22, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("savy@gmail.com", 23, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("stephan@gmail.com", 24, now(), FALSE, FALSE);

INSERT INTO user
VALUES ("charleston@gmail.com", 25, now(), FALSE, FALSE);



INSERT INTO REVIEWABLE_ENTITY
VALUES ("thy@gmail.com", 23, now(), TRUE), ("xing@gmail.com", 25, now(), TRUE);  -- PENDING CITIES

INSERT INTO REVIEWABLE_ENTITY
VALUES ("michael@gmail.com", 13, now(), TRUE), ("abbie@gmail.com", 7, now(), TRUE); -- pending attractions

INSERT INTO REVIEWABLE_ENTITY
VALUES ("kendall@gmail.com", 20, now(), FALSE), ("bent@gmail.com", 21, now(), FALSE), ("mattl@gmail.com", 22, now(), FALSE), ("sarahg@gmail.com", 24, now(), FALSE), ("joseph@gmail.com", 26, now(), FALSE); -- cities

INSERT INTO REVIEWABLE_ENTITY
VALUES ("walker@gmail.com", 1, now(), FALSE), ("anthony@gmail.com", 2, now(), FALSE), ("chloe@gmail.com", 3, now(), FALSE), ("eugenia@gmail.com", 4, now(), FALSE), ("elizabeth@gmail.com", 5, now(), FALSE), ("kyra@gmail.com", 6, now(), FALSE), ("ruchi@gmail.com", 9, now(), FALSE), ("savy@gmail.com", 8, now(), FALSE), ("Stephan@gmail.com", 10, now(), FALSE), ("Charleston@gmail.com", 11, now(), FALSE), ("maya@gmail.com", 12, now(), FALSE), ("pravan@gmail.com", 14, now(), FALSE), ("alyssa@gmail.com", 15, now(), FALSE); -- attractions 

INSERT INTO REVIEW
VALUES ("kendall@gmail.com", 20, now(),"Amazing beaches. Very crowded", 4), ("bent@gmail.com", 21, now(),"lots of good food! Very expensive", 4), ("mattl@gmail.com", 22, now(),"Lots to see. Love the accent!", 4), ("sarahg@gmail.com", 24, now(),"Not a fan of the nightlife. Not much to do", 1), ("joseph@gmail.com", 26, now(),"Very good culture; very hipster", 4); -- cities

INSERT INTO REVIEW
VALUES ("walker@gmail.com", 1, now(), "Beautiful Church. Not worth the price", 3), ("anthony@gmail.com", 2, now(), "Great spot to take photos—lots of tourists", 4), ("chloe@gmail.com", 3, now(), "I SAW NEYMAR! LOVE IT",4), ("eugenia@gmail.com", 4, now(), "Stunning but very crowded", 4), ("elizabeth@gmail.com", 5, now(),  "Beautiful. Felt very spiritual", 4), ("kyra@gmail.com", 6, now(), "Beautful paintings. Saw the Mona Lisa!", 4), ("ruchi@gmail.com", 9, now(), "Great views", 3), ("savy@gmail.com", 8, now(), "Not worth the price", 1), ("Stephan@gmail.com", 10, now(), "too many paintings", 1), ("Charleston@gmail.com", 11, now(), "Had lots of fun", 4), ("maya@gmail.com", 12, now(), "Love learning about Netherlands!", 4), ("pravan@gmail.com", 14, now(), "Great place to walk around but very pricey", 3), ("alyssa@gmail.com", 15, now(), "beautiful but very windy", 4); -- attractions 

INSERT INTO REVIEW
VALUES ("raghav@gmail.com", 23, now(), "too rainy for me", 2), ("maya@gmail.com", 25, now(), "communist" , 3);

INSERT INTO CITY
VALUES (20, "Barcelona", "Spain", NULL);

INSERT INTO City
VALUES (21, "Paris", "France", NULL);

INSERT INTO City
VALUES (22, "London", "England", NULL);

INSERT INTO City
VALUES (23, "Dublin", "Ireland", NULL); -- pending

INSERT INTO City
VALUES (24, "Amsterdam", "Nethlands", NULL);

INSERT INTO City
VALUES (25, "Bucharest", "Romania", NULL); -- pending

INSERT INTO City
VALUES (26, "San Francisco", "United States", "California");





INSERT INTO ATTRACTION
VALUES (1,20, "Sagrada Familia", "Carrer de Mallorca, 401, 08013 Barcelona, Spain", "Beautiful Church. Gaudi’s Masterpiece");

INSERT INTO ATTRACTION
VALUES (2,20, "Parc Guell", "08024 Barcelona", "Public park system composed of gardens and architectonic elements");

INSERT INTO ATTRACTION
VALUES (3,20, "Camp Nou", "Carrer d'Aristides Maillol, 12, 08028 Barcelona, Spain", "Home of Barcelona’s premier football team");

INSERT INTO ATTRACTION
VALUES (4,21, "Eiffel Tower", "Champ de Mars, 5 Avenue Anatole France, 75007 Paris, France", "Wrought iron lattice tower on Champ de Mars");

INSERT INTO ATTRACTION
VALUES (5,21, "Notre Dame", "6 Paris Notre-Dame - Pl. Jean-Paul II, 75004 Paris, France
", "Medieval Catholic cathedral");

INSERT INTO ATTRACTION
VALUES (6,21, "Louvre Museum", "Rue de Rivoli, 75001 Paris, France", "Worlds largest museum");

INSERT INTO ATTRACTION
VALUES (7,22, "Big Ben", "Westminster, London SW1A 0AA, UK", "Great Bell of the clock of Palace of Westminister");

INSERT INTO ATTRACTION
VALUES (8,22, "London Eye", " Lambeth, London SE1 7PB, UK", "Giant Ferris wheel on the South Bank of the River Thames");

INSERT INTO ATTRACTION
VALUES (9,22, "Tower of London", "St Katharine's & Wapping, London EC3N 4AB, UK
", "Officially Her Majesty’s Royal Palace and Fortress of the Tower of London");

INSERT INTO ATTRACTION
VALUES (10,24, "Van Gough Museum", "Museumplein 6, 1071 DJ Amsterdam, Netherlands", "Art museum dedicated to the works of Vincent Van Gough");

INSERT INTO ATTRACTION
VALUES (11,24, "Efteling", " Europalaan 1, 5171 KW Kaatsheuvel, Netherlands
", "Fantasy-themed amusement park based on elements of ancient myths");

INSERT INTO ATTRACTION
VALUES (12,24, "Rijksmuseum", " Museumstraat 1, 1071 XX Amsterdam, Netherlands", "Dutch cultural and history museum");

INSERT INTO ATTRACTION
VALUES (13,26, "Golden Gate Bridge", " Golden Gate Bridge, San Francisco, CA, USA
", "Suspension bridge spanning the Golden Gate strait");

INSERT INTO ATTRACTION
VALUES (14,26, "Union Square", "Union Square, San Francisco, CA, USA
", "2.6 public plaza in the center of San Francisco");

INSERT INTO ATTRACTION
VALUES (15,26, "Fishermans Warf", "Fishermans Warf, San Francisco, CA, USA
", "Neighborhood and popular tourist attraction that borders the coast");


INSERT INTO CONTACT_INFO
VALUES (1, "Phone Number", "3815647890"), (2, "Email", "gaudi@gmail.com"),
(3, "Email", "Neymar@gmail.com"), (4, "Phone Number", "56789345670"), 
(5, "Email", "hunchback@gmail.com"), (6, "Email", "monalisa@gmail.com"), (8, "Phone Number", "8975431212"), (9, "Phone Number", "7684325567"), 
(10, "Email", "starrynight@gmail.com"), (11, "Phone Number", "8976543321"), (12, "Phone Number", "9897765656"), 
(13, "Phone Number", "7890009090"), (14, "Email", "shopping@gmail.com");

INSERT INTO HOURS_OF_OPERATION
VALUES (1,"Mon-Sun","090000","150000"), (2,"Mon-Sun", 100000,"210000"), (3,"Mon-Sun","080000","173000"), 
(4,"Mon-Sun","090000","200000"), (5,"Mon-Sun","090000","190000"), (6,"Mon-Sun","100000","180000"), 
(7,"Mon-Sun", "090000","180000"), (8,"Mon-Sun","110000","180000"), (9,"Mon-Sun","100000","150000"), 
(10,"Mon-Sun","090000","180000"), (11,"Mon-Sun","103000","210000"), (12,"Mon-Sun","090000","200000"), 
(13,"Mon-Sun","000000","000000"), (14,"Mon-Sun","000000","000000"), (15,"Mon-Sun","000000","000000");


INSERT INTO CATEGORY
VALUES ("Museum"),("Religious"),("Parks/Public Space"),("Amusement Park"),("Shopping"),("Food"),("Nightlife"),("Adventure"),("Monument"),("Sports");






INSERT INTO FALLS_UNDER
VALUES (1, "Religious"), (2, "Parks/Public Space"), (3, "Sports"), (4, "Monument"), (5, "Religious"), (6, "Museum"), (7, "Monument"), (8, "Monument"), (9, "Monument"), (10, "Museum"), (11, "Amusement Park"), (12, "Museum"), (13, "Monument"), (14, "Parks/Public Space"), (15, "Food");


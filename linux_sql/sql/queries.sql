INSERT INTO cd.facilities(
        facid,
        name,
        membercost,
        guestcost,
        initialoutlay,
        monthlymaintenance
        )
        
        VALUES(
                9,
                'Spa',
                20,
                30,
                100000,
                800
);

INSERT INTO cd.facilities      
        VALUES(
                (SELECT MAX(faceid) FROM cd.facilities)+1,
                'Spa',
                20,
                30,
                100000,
                800
);

UPDATE cd.facilities
       SET initialoutlay = 10000
       WHERE facid = 1
;

UPDATE cd.facilities
        SET 
                membercost = (SELECT membercost FROM cd.facilities WHERE facid = 1)*1.1,
                guestcost = (SELECT guestcost FROM cd.facilities WHERE facid = 1)*1.1
        
        WHERE facid = 1
;

DELETE FROM cd.bookings;

DELETE FROM cd.members WHERE memid=37;

SELECT facid, name, membercost, monthlymaintenance 
        FROM cd.facilities 
        WHERE 
                membercost < (monthlymaintenance/50) AND
                membercost > 0
                
;

SELECT * FROM cd.facilities WHERE name like '%Tennis%';

SELECT * FROM cd.facilities WHERE facid in (1, 5);

SELECT memid, surname, firstname, joindate FROM cd.members WHERE joindate > '2012-09-01';

SELECT surname FROM cd.members 
        UNION
SELECT name FROM cd.facilities;

SELECT starttime FROM cd.bookings
        INNER JOIN cd.members
                ON cd.members.memid = cd.bookings.memid
        WHERE
                firstname = 'David' AND
                surname = 'Farrell'
;

SELECT bk.starttime, fa.name 
        FROM cd.bookings bk
        INNER JOIN cd.facilities fa
                ON bk.facid = fa.facid
        WHERE
                bk.starttime >= '2012-09-21' AND
                bk.starttime < '2012-09-22' AND
                fa.name LIKE '%Tennis Court%'
        ORDER BY bk.starttime
;
                
SELECT m1.firstname, m1.surname, m2.firstname AS refFname, m2.surname AS refSname 
        FROM cd.members m1
        LEFT JOIN cd.members m2
        ON m1.recommendedby = m2.memid
        ORDER BY m1.surname, m1.firstname
;

SELECT DISTINCT m2.firstname AS refFname, m2.surname AS refSname 
        FROM cd.members m1
        INNER JOIN cd.members m2
        ON m1.recommendedby = m2.memid
        WHERE m2.firstname IS NOT NULL
        ORDER BY m2.surname, m2.firstname
;

SELECT DISTINCT m1.firstname ||' '|| m1.surname as name, 
        (SELECT firstname ||' '|| m2.surname AS reccname FROM cd.members m2 WHERE m1.recommendedby = m2.memid)
        FROM cd.members m1
        ORDER BY name
;

SELECT recommendedby, COUNT(*) 
        FROM cd.members
        WHERE recommendedby IS NOT NULL
        GROUP BY recommendedby
        ORDER BY recommendedby  
;

SELECT facid, SUM(slots) 
        FROM cd.bookings
        GROUP BY facid
        ORDER BY facid
;

SELECT facid, SUM(slots) AS "total slots"
        FROM cd.bookings
        WHERE 
                starttime >= '2012-09-01' AND
                starttime < '2012-10-01'
        GROUP BY facid
        ORDER BY "total slots"
;

SELECT facid, CAST(EXTRACT(month FROM starttime) AS INT) AS month, SUM(slots) AS "total slots"
        FROM cd.bookings
        WHERE EXTRACT(year from starttime) = '2012'               
        GROUP BY EXTRACT(month FROM starttime), facid
        ORDER BY facid, month
;

SELECT COUNT(DISTINCT memid)
        FROM cd.bookings
;

SELECT surname, firstname AS name, m.memid, MIN(bk.starttime)
        FROM cd.members m
        LEFT JOIN cd.bookings bk
        ON m.memid = bk.memid
        WHERE bk.starttime > '2012-09-01'
        GROUP BY m.memid
        ORDER BY m.memid
;

SELECT  COUNT(*) over(), firstname, surname
        FROM cd.members
        ORDER BY joindate
;
  
SELECT  ROW_NUMBER() OVER(), firstname, surname
        FROM cd.members
        ORDER BY joindate
;
        
SELECT facid, sum 
        FROM (
                SELECT facid, SUM(slots) sum, rank() OVER(ORDER BY SUM(slots) DESC)
                FROM cd.bookings
                GROUP BY facid
                ) as ranked
        WHERE ranked.rank = 1
;  
  
SELECT surname||', '|| firstname as NAME
        FROM cd.members
;    

SELECT memid, telephone 
        FROM cd.members
        WHERE telephone LIKE '(___)%'
        ORDER BY memid
;
        
SELECT SUBSTRING(surname, 1, 1) as letter, COUNT(*) as count
        FROM cd.members
        GROUP BY letter
        ORDER BY letter 
;        
        
        
        
        
        
        
        
        
        
        
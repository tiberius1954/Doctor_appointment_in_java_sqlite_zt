BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Nurse" (
	"nid"	INTEGER NOT NULL,
	"nname"	TEXT NOT NULL DEFAULT ' ',
	"position"	TEXT NOT NULL DEFAULT ' ',
	"phone"	TEXT NOT NULL DEFAULT ' ',
	"qualifications"	TEXT DEFAULT ' ',
	"gen"	TEXT DEFAULT ' ',
	"ssn"	TEXT NOT NULL DEFAULT ' ',
	"note"	TEXT DEFAULT ' ',
	PRIMARY KEY("nid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "appointments" (
	"aid"	INTEGER NOT NULL,
	"did"	INTEGER NOT NULL,
	"pid"	INTEGER NOT NULL,
	"app_date"	TEXT,
	"app_time"	TEXT,
	"day"	TEXT DEFAULT ' ',
	"examinationroom"	TEXT DEFAULT ' ',
	"status"	TEXT DEFAULT ' ',
	"note"	TEXT DEFAULT ' ',
	PRIMARY KEY("aid")
);
CREATE TABLE IF NOT EXISTS "docappticancel" (
	"cid"	INTEGER,
	"did"	INTEGER,
	"startdate"	TEXT,
	"enddate"	TEXT,
	"starttime"	TEXT,
	"endtime"	TEXT,
	PRIMARY KEY("cid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "doctorapptimes" (
	"tid"	INTEGER,
	"did"	INTEGER,
	"dayname"	INTEGER,
	"starttime"	TEXT,
	"endtime"	TEXT,
	PRIMARY KEY("tid" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "doctors" (
	"did"	INTEGER,
	"dname"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"qualifications"	TEXT DEFAULT ' ',
	"gen"	TEXT DEFAULT ' ',
	"address"	TEXT DEFAULT ' ',
	"ssn"	TEXT NOT NULL DEFAULT ' ',
	PRIMARY KEY("did" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "patient" (
	"pid"	INTEGER,
	"pname"	TEXT DEFAULT ' ',
	"email"	TEXT DEFAULT ' ',
	"phone"	TEXT DEFAULT ' ',
	"dob"	TEXT DEFAULT ' ',
	"Remarks"	TEXT DEFAULT ' ',
	"gen"	TEXT DEFAULT ' ',
	"insuranceid"	TEXT DEFAULT ' ',
	"address"	TEXT DEFAULT ' ',
	PRIMARY KEY("pid" AUTOINCREMENT)
);
INSERT INTO "Nurse" ("nid","nname","position","phone","qualifications","gen","ssn","note") VALUES (2,'Carla Espinosa','Head Nurse',' ',' ','F','111111110',' ');
INSERT INTO "Nurse" ("nid","nname","position","phone","qualifications","gen","ssn","note") VALUES (3,'Laverne Roberts','Nurse',' ',' ','F','222222220',' ');
INSERT INTO "Nurse" ("nid","nname","position","phone","qualifications","gen","ssn","note") VALUES (4,'Paul Flowers','Nurse',' ',' ','M','333333330',' ');
INSERT INTO "appointments" ("aid","did","pid","app_date","app_time","day","examinationroom","status","note") VALUES (2,1,1,'2025-01-22','08:00','Wednesday',' ',' ',' ');
INSERT INTO "appointments" ("aid","did","pid","app_date","app_time","day","examinationroom","status","note") VALUES (3,5,1,'2025-01-22','09:00','Wednesday',' ',' ',' ');
INSERT INTO "appointments" ("aid","did","pid","app_date","app_time","day","examinationroom","status","note") VALUES (4,1,6,'2025-01-22','08:15','Wednesday',' ',' ',' ');
INSERT INTO "appointments" ("aid","did","pid","app_date","app_time","day","examinationroom","status","note") VALUES (5,12,19,'2025-01-30','13:00','Thursday',' ',' ',' ');
INSERT INTO "docappticancel" ("cid","did","startdate","enddate","starttime","endtime") VALUES (1,2,'2025-12-31','2025-12-31','07:00','11:00');
INSERT INTO "docappticancel" ("cid","did","startdate","enddate","starttime","endtime") VALUES (2,1,'2025-01-18','2025-01-18','07:00','18:00');
INSERT INTO "docappticancel" ("cid","did","startdate","enddate","starttime","endtime") VALUES (4,12,'2025-02-03','2025-02-03','08:00','12:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (2,1,'Monday','10:00','14:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (3,1,'Wednesday','08:00','12:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (4,12,'Monday','08:00','12:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (5,12,'Thursday','13:00','18:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (6,16,'Wednesday','08:00','11:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (7,16,'Tuesday','13:00','17:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (8,10,'Tuesday','10:00','12:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (9,10,'Monday','13:00','16:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (10,7,'Thursday','08:00','13:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (11,7,'Tuesday','14:00','18:00');
INSERT INTO "doctorapptimes" ("tid","did","dayname","starttime","endtime") VALUES (12,12,'Monday','13:00','18:00');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (7,'Dr. John Smith',' email','123-456-7890','Cardiologist','M',' ',' 23411');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (8,'Dr. Sarah Johnson',' ','123-456-7891','Neurologist','F',' ',' ');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (9,'Dr. Michael Brown',' ','123-456-7892','Orthopedic','M',' ',' ');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (10,'Dr. Emily Davis',' email','123-456-7893','Pediatrician','F',' ',' 1122');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (11,'Dr. James Wilson',' ','123-456-7894','Oncologist','M',' ',' ');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (12,'Dr. Aisha Hassan',' ','123-456-7895','Dermatologist','F',' ',' ');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (13,'Dr. Kamal Adel',' ','123-456-7896','ENT Specialist','M',' ',' ');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (14,'Dr. Noha Saber',' ','123-456-7897','Psychiatrist','N',' ',' ');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (15,'Dr. Tamer Hosny',' ','123-456-7898','Radiologist','M',' ',' ');
INSERT INTO "doctors" ("did","dname","email","phone","qualifications","gen","address","ssn") VALUES (16,'Dr. Dina Farid',' email','123-456-7899','Emergency Medicine','F',' ',' 123456');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (1,'Tibi','email','21222','1917-11-11',' Jó beteg','M','','address');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (4,'Alice Johnson',' email','555-0101','2024-01-15',' oke','F',' 1','123 Main St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (5,'Bob Wilson',' ','555-0102','2024-01-16',' ','M',' ','456 Oak Ave');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (6,'Carol Smith',' ','555-0103','2024-01-17 ',' ','F',' ','789 Pine St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (7,'David Brown',' ','555-0104','2024-01-18',' ','M',' ','321 Elm St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (8,'Eve Davis',' ','555-0105','2024-01-19',' oke','F',' ','654 Maple St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (9,'Hassan Ahmed',' ','555-0106','2024-01-20',' ','M',' ','789 Cedar St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (10,'Fatima Omar',' ','555-0107','2024-01-21',' ','F',' ','456 Birch St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (11,'Mahmoud Samy',' ','555-0108','2024-01-22',' ','M',' ','123 Walnut St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (12,'Nadia Kamal',' ','555-0109','2024-01-23',' ','F',' ','987 Pine St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (13,'Yasser Hany',' ','555-0110','2024-01-24',' ','M',' ','654 Oak St');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (14,'John Smith',' ','555-0256','2001-11-11',' oke','M','68476213','42 Foobar Lane');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (16,'Grace Ritchie',' ','555-0512','1954-11-26',' ','F','36546321','37 Snafu Drive');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (17,'Random J. Patient',' ','555-1204',' 1984-12-24',' ','M','65465421','101 Omgbbq Street');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (18,'Dennis Doe',' ','555-2048','1981-12-01',' hello hello hello','M','68421879','1100 Foobaz Avenue');
INSERT INTO "patient" ("pid","pname","email","phone","dob","Remarks","gen","insuranceid","address") VALUES (19,'Julia Roberts','julia.roberts@gmail.com','134568899','1981-02-02',' ','M',' ',' ');
COMMIT;

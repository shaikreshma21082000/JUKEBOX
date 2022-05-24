create database jukebox;
use jukebox;

create table users(
uid integer primary key auto_increment,
uname varchar(20) not null,
phno long ,
age integer,
city varchar(20));
insert into users(uname,phno,age,city) values ('Reshma',7386768425,23,'Manuguru');

create table login(
lid integer primary key auto_increment,
uid integer, foreign key (uid) references users (uid),
username varchar(20) not null,
upassword varchar(20) not null);
insert into login (uid,username,upassword) values(1,'Reshma@21','Reshu21@');
insert into login (uid,username,upassword) values(1,'.','.');
 
 create table artists(
 arid integer primary key auto_increment,
 arname varchar(20) not null);
 
 insert into artists(arname) values ('Ankit Tiwari');
 insert into artists(arname) values ('Palak Muchhal');
 insert into artists(arname) values ('Akasa Singh');
 insert into artists(arname) values ('Darshan Raval');
 insert into artists(arname) values ('Neeti Mohan');
 insert into artists(arname) values ('Himesh Reshmmiya');
 insert into artists(arname) values ('Arjit Singh');
 insert into artists(arname) values ('Atif Aslam');
 insert into artists(arname) values ('Utkarsh Sharma');
 insert into artists(arname) values ('Ishitha Chauhan');
 insert into artists(arname) values ('Jubin Nautiyal');
 insert into artists(arname) values ('Vineet Singh');
 insert into artists(arname) values ('Dev Negi');
 insert into artists(arname) values ('Ikka');
 insert into artists(arname) values ('Iulia Vantur');
 insert into artists(arname) values ('Payal Dev');
 insert into artists(arname) values ('Tanish Bagchi');
 insert into artists(arname) values ('Dhavani Bhanushali');
 insert into artists(arname) values ('Lijo George');
 insert into artists(arname) values ('RCR');
 insert into artists(arname) values ('Narayanacharya');
 insert into artists(arname) values ('Panky');
 insert into artists(arname) values ('Sunandha');
 insert into artists(arname) values ('Gopal');
 insert into artists(arname) values ('Ravi');
 insert into artists(arname) values ('Shilpa');
 insert into artists(arname) values ('Manjunath');
 insert into artists(arname) values ('Noel');
 insert into artists(arname) values ('Omer');
 insert into artists(arname) values ('Priyanka');
 insert into artists(arname) values ('Rahul');
 insert into artists(arname) values ('Shareef');
 insert into artists(arname) values ('Andrea');
 insert into artists(arname) values ('Rea Kasandra');
 insert into artists(arname) values ('John');
 insert into artists(arname) values ('Ellie');

 
 
 create table genre(
 gid integer primary key auto_increment,
 gname varchar(20));
insert into genre (gname) values ('Pop');
insert into genre (gname) values ('Popular');
insert into genre (gname) values ('Jazz');
insert into genre (gname) values ('Soul Music');
insert into genre (gname) values ('Trance Music');
insert into genre (gname) values ('New Age Music');
insert into genre (gname) values ('Festive');
insert into genre (gname) values ('Baby Sleep');
insert into genre (gname) values ('Rhymes');
insert into genre (gname) values ('Health');
insert into genre (gname) values ('Religion');
insert into genre (gname) values ('Comedy');
insert into genre (gname) values ('Technology');
insert into genre (gname) values ('Child Stories');
insert into genre (gname) values  ('Rap');
 
 
 create table Adult_album(
 alid integer primary key auto_increment,
 alname varchar(20) not null,
 ReleaseDate Date); 
insert into Adult_album(alname,ReleaseDate) values('Sanam Teri Kasam','2016-02-05');
insert into Adult_album(alname,ReleaseDate) values ('Genius','2018-08-24');
insert into Adult_album(alname) values ('Private Albumn');

create table Adult_song(
sid integer,
sname varchar(40) not null,
arid integer,foreign key (arid) references artists(arid),
alid integer,foreign key (alid) references adult_album(alid),
duration time not null,
gid integer,foreign key (gid) references genre(gid),
descriptionofSong varchar(1000),
primary key(sid,arid));
Alter table Adult_song
add link varchar(1000) not null;
insert into Adult_song values(1,'Ae Dil Rap',20,3,'00:03:10',15,'Mtv Hustle performer Rcr Sang song.This rap song Ae Dil Hai Mushkil viral now these days in all over social media sites like YouTube, Facebook, Instagram and many more','Resources/songs/AeDil -RCR Rap.wav');
insert into Adult_song values(2,'Bewajah',6,1,'00:05:08',5,'The song is sung by Himesh Reshammiya and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Bewajah-Sanam Teri Kasam.wav');
insert into Adult_song values(3,'Dil Meri Na Sune',6,2,'00:03:55',6,'Atif Aslam is among the greatest singers in India.Atif Aslam manages to bring something fresh to his tunes every time because of his audience.Atif Aslam was able to Get a Whole Lot of admiration and love in the World.','Resources/songs/Dil Meri Na Sune - Genius.wav');
insert into Adult_song values(4,'Dil Meri Na Sune - Reprise',8,2,'00:03:55',4,'Dil Meri Na Sune (Reprise) Genius Mp3 Song by Atif Aslam, Payal Dev, Himesh Reshammiya.From  Album "Genius (2018) Hindi Movie','Resources/songs/Dil Meri Na Sune (Reprise) Genius.wav');
insert into Adult_song values(4,'Dil Meri Na Sune - Reprise',6,2,'00:03:55',4,'Dil Meri Na Sune (Reprise) Genius Mp3 Song by Atif Aslam, Payal Dev, Himesh Reshammiya.From  Album "Genius (2018) Hindi Movie','Resources/songs/Dil Meri Na Sune (Reprise) Genius.wav');
insert into Adult_song values(4,'Dil Meri Na Sune - Reprise',16,2,'00:03:55',4,'Dil Meri Na Sune (Reprise) Genius Mp3 Song by Atif Aslam, Payal Dev, Himesh Reshammiya.From  Album "Genius (2018) Hindi Movie','Resources/songs/Dil Meri Na Sune (Reprise) Genius.wav');
insert into Adult_song values(5,'Ek Number',5,1,'00:04:20',3,'The song is sung by Himesh Reshammiya,Neeti Mohan and composed by Himesh Reshammiya while lyrics written by Himesh Reshammiya.','Resources/songs/Ek Number-Sanam Teri Kasam.wav');
insert into Adult_song values(5,'Ek Number',6,1,'00:04:20',3,'The song is sung by Himesh Reshammiya,Neeti Mohan and composed by Himesh Reshammiya while lyrics written by Himesh Reshammiya.','Resources/songs/Ek Number-Sanam Teri Kasam.wav');
insert into Adult_song values(6,'Haal-E-Dil',5,1,'00:06:24',5,'The song is sung by Neeti Mohan and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Haal - E - Dil=Sanam Teri Kasam.wav');
insert into Adult_song values(6,'Haal-E-Dil',6,1,'00:06:24',5,'The song is sung by Neeti Mohan and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Haal - E - Dil=Sanam Teri Kasam.wav');
insert into Adult_song values(7,'Holi Biraj Ma',11,2,'00:03:34',7,'Holi Biraj Ma from Genius 2018 Hindi movie feat. Utkarsh Sharma & Ishita Chauhan- Single.Singer(s): Jubin Nautiyal,Music by Himesh Reshammiya,Lyrics by Manoj Muntashir.','Resources/songs/Holi Biraj Ma - Genius.wav');
insert into Adult_song values(8,'Kheech Meri Photo',3,1,'00:4:44',1,'The song is sung by Akasa Singh,Darshan Raval,Neeti Mohan and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Kheech Meri Photo-Sanam Teri Kasam.wav');
insert into Adult_song values(8,'Kheech Meri Photo',4,1,'00:4:44',1,'The song is sung by Akasa Singh,Darshan Raval,Neeti Mohan and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Kheech Meri Photo-Sanam Teri Kasam.wav');
insert into Adult_song values(8,'Kheech Meri Photo',5,1,'00:4:44',1,'The song is sung by Akasa Singh,Darshan Raval,Neeti Mohan and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Kheech Meri Photo-Sanam Teri Kasam.wav');
insert into Adult_song values(9,'Lut Gaye',11,3,'00:03:48',4,'Lut Gaye - Jubin Nautiyal Ft Emraan Hashmi Song.','Resources/songs/Lut Gaye - Jubin Nautiyal.wav');
insert into Adult_song values(10,'Main Teri Yadoon mein',7,1,'00:4:16',2,'The song is sung by Arijit Singh and composed by Himesh Reshammiya while lyrics written by Subrat Sinha.','Resources/songs/Main Teri Yaadon Mein-Sanam Teri Kasam.wav');
insert into Adult_song values(10,'Main Teri Yadoon mein',6,1,'00:4:16',2,'The song is sung by Arijit Singh and composed by Himesh Reshammiya while lyrics written by Subrat Sinha.','Resources/songs/Main Teri Yaadon Mein-Sanam Teri Kasam.wav');
insert into Adult_song values(11,'Nayan',18,3,'00:04:15',1,'Indian Pop Artists-Dhvani Bhanushali, Jubin Nautiyal, Lijo George','Resources/songs/Nayan - Dhvani Bhanushali.wav');
insert into Adult_song values(12,'Pyaar Le Pyaar De',13,2,'00:05:10',3,'Pyar Le Pyar De - Genius Mp3 Song by Dev Negi, Ikka, Iulia Vantur, Himesh Reshammiya in 190kbs & 320Kbps only on Pagalworld.From New Music Album Genius (2018) Hindi Movie Songs','Resources/songs/Pyar Le Pyar De - Genius.wav');
insert into Adult_song values(12,'Pyaar Le Pyaar De',14,2,'00:05:10',3,'Pyar Le Pyar De - Genius Mp3 Song by Dev Negi, Ikka, Iulia Vantur, Himesh Reshammiya in 190kbs & 320Kbps only on Pagalworld.From New Music Album Genius (2018) Hindi Movie Songs','Resources/songs/Pyar Le Pyar De - Genius.wav');
insert into Adult_song values(12,'Pyaar Le Pyaar De',15,2,'00:05:10',3,'Pyar Le Pyar De - Genius Mp3 Song by Dev Negi, Ikka, Iulia Vantur, Himesh Reshammiya in 190kbs & 320Kbps only on Pagalworld.From New Music Album Genius (2018) Hindi Movie Songs','Resources/songs/Pyar Le Pyar De - Genius.wav');
insert into Adult_song values(12,'Pyaar Le Pyaar De',6,2,'00:05:10',3,'Pyar Le Pyar De - Genius Mp3 Song by Dev Negi, Ikka, Iulia Vantur, Himesh Reshammiya in 190kbs & 320Kbps only on Pagalworld.From New Music Album Genius (2018) Hindi Movie Songs','Resources/songs/Pyar Le Pyar De - Genius.wav');
insert into Adult_song values(13,'Sanam Teri Kasam',1,1,'00:05:14',2,'The song is sung by Ankit Tiwari,Palak Muchhal and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Sanam Teri kasam -Title song.wav');
insert into Adult_song values(13,'Sanam Teri Kasam',2,1,'00:05:14',2,'The song is sung by Ankit Tiwari,Palak Muchhal and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Sanam Teri kasam -Title song.wav');
insert into Adult_song values(13,'Sanam Teri Kasam',6,1,'00:05:14',2,'The song is sung by Ankit Tiwari,Palak Muchhal and composed by Himesh Reshammiya while lyrics written by Sameer Anjaan.','Resources/songs/Sanam Teri kasam -Title song.wav');
insert into Adult_song values(14,'Tera Chehra',7,1,'00:04:34',4,'The song is sung by Arijit Singh and composed by Himesh Reshammiya while lyrics written by Shabbir Ahmed.','Resources/songs/Tera Chehra-Sanam Teri Kasam.wav');
insert into Adult_song values(14,'Tera Chehra',6,1,'00:04:34',4,'The song is sung by Arijit Singh and composed by Himesh Reshammiya while lyrics written by Shabbir Ahmed.','Resources/songs/Tera Chehra-Sanam Teri Kasam.wav');
insert into Adult_song values(15,'Tujhse Kahan Juda Hoon Main',6,2,'00:04:42','6','Genius Hindi Movie Songs Artists-Himesh Reshammiya, Neeti Mohan, Vineet Singh','Resources/songs/Tujhse Kahan Juda Hoon Main - Genius.wav');
insert into Adult_song values(15,'Tujhse Kahan Juda Hoon Main',5,2,'00:04:42','6','Genius Hindi Movie Songs Artists-Himesh Reshammiya, Neeti Mohan, Vineet Singh','Resources/songs/Tujhse Kahan Juda Hoon Main - Genius.wav');
insert into Adult_song values(15,'Tujhse Kahan Juda Hoon Main',12,2,'00:04:42','6','Genius Hindi Movie Songs Artists-Himesh Reshammiya, Neeti Mohan, Vineet Singh','Resources/songs/Tujhse Kahan Juda Hoon Main - Genius.wav');



 create table child_album(
 alid integer primary key auto_increment,
 alname varchar(20) not null,
 ReleaseDate Date); 
insert into child_album(alname) values ('Baby Sleep');
insert into child_album(alname) values ('Rhymes');


create table child_song(
sid integer,
sname varchar(40) not null,
arid integer,foreign key (arid) references artists(arid),
alid integer,foreign key (alid) references child_album(alid),
duration time not null,
gid integer,foreign key (gid) references genre(gid),
descriptionofSong varchar(1000),
primary key(sid,arid));
Alter table child_song
add link varchar(1000) not null;
                                                     
insert into child_song values(1,'Its Time to Sleep',22,1,'00:02:18',8,'Child favourite bedtime song sung by panky','Resources/songs/Baby Sleep-BabySong.wav');

create table child_rhymes(
sid integer,
sname varchar(40) not null,
arid integer,foreign key (arid) references artists(arid),
alid integer,foreign key (alid) references child_album(alid),
duration time not null,
gid integer,foreign key (gid) references genre(gid),
descriptionofSong varchar(1000),
primary key(sid,arid));
Alter table child_rhymes
add link varchar(1000) not null;
insert into child_rhymes values(2,'Twinkle Twinkle Little Star',35,2,'00:02:27',6,'Rhymes for kids.New Way of childen to learng','Resources/songs/Twinkle_Twinkle_Little_Star_-Baby Rhymes.wav');
insert into child_rhymes values(1,'Johny-Johny-yes-papa',35,2,'00:01:40',9,'Rhymes for kids.New Way of childen to learn','Resources/songs/Johny_Johny_Yes_Papa-Baby Rhymes.wav');
insert into child_rhymes values(1,'Johny-Johny-yes-papa',36,2,'00:01:40',9,'Rhymes for kids.New Way of childen to learn','Resources/songs/Johny_Johny_Yes_Papa-Baby Rhymes.wav');




create table Adult_podcast(
podid Integer primary key auto_increment,
podname varchar(20) not null
);
insert into Adult_podcast(podname) values('Health is Wealth');
insert into Adult_podcast(podname) values('Spirituality');
insert into Adult_podcast(podname) values('StandUp comedy');
insert into Adult_podcast(podname) values('What is Technology??');



create table adult_podcast_episodes(
epid integer,
podid integer,foreign key(podid) references adult_podcast(podid),
episodeName varchar(30),
onAirDate date,
descriptionofepisode varchar(1000),
gid integer,foreign key (gid) references genre(gid),
arid integer ,foreign key(arid) references artists(arid),
epitime time,
link varchar(1000),
primary key (epid,podid,arid));

insert into adult_podcast_episodes values(1,1,'Corona Diet','2022-01-01','Food to be taken on corona.',10,26,'00:08:19','Resources/podcast/Health-corona diet.wav');
insert into adult_podcast_episodes values(2,1,'Good Health','2022-02-01','Tips to stay healthy.',10,27,'00:05:56','Resources/podcast/Health-tips for good health.wav');
insert into adult_podcast_episodes values(1,2,'Christianity','2022-01-30','About Christians.',11,28,'00:04:58','Resources/podcast/Religion-Christianity.wav');
insert into adult_podcast_episodes values(2,2,'Islam','2022-02-2','About beliefs of islam.',11,29,'00:05:00','Resources/podcast/Religion-Islam.wav');
insert into adult_podcast_episodes values(3,2,'Hinduism','2022-02-3','About Hinduism.',11,21,'00:06:18','Resources/podcast/Religion-Hinduism.wav');
insert into adult_podcast_episodes values(1,3,'School life','2022-02-1','Incedents in school life.',12,30,'00:08:22','Resources/podcast/StandUpComedy-Indian parents.wav');
insert into adult_podcast_episodes values(2,3,'Marraige','2022-01-31','Discussing about marraige with parents.',12,31,'00:08:08','Resources/podcast/StandUpComedy-Marraige.wav');
insert into adult_podcast_episodes values(2,3,'Marraige','2022-01-31','Discussing about marraige with parents.',12,32,'00:08:08','Resources/podcast/StandUpComedy-Marraige.wav');
insert into adult_podcast_episodes values(1,4,'Big Data','2022-01-31','Big data in 5 minutes.',13,33,'00:05:11','Resources/podcast/Technology-BigData.wav');
insert into adult_podcast_episodes values(2,4,'Data Science','2022-02-2','Data Science in 5 minutes.',13,34,'00:04:37','Resources/podcast/Technology-Data Science.wav');


create table child_podcast(
podid Integer primary key auto_increment,
podname varchar(20) not null
);
insert into child_podcast(podname) values('Bed Time Stories');
insert into child_podcast(podname) values('Children Stories');



create table child_podcast_episodes(
epid integer,
podid integer,foreign key(podid) references child_podcast(podid),
episodeName varchar(30),
onAirDate date,
descriptionofepisode varchar(1000),
gid integer,foreign key (gid) references genre(gid),
arid integer ,foreign key(arid) references artists(arid),
epitime time,
link varchar(1000),
primary key (epid,podid,arid));
insert into child_podcast_episodes values(1,1,'Thirsty Crow','2022-01-31','Intelligance is key to get out of all difficulties.',14,22,'00:02:25','Resources/podcast/ChildrenStory-thirsty crow.wav');
insert into child_podcast_episodes values(1,2,'Ziddi Bacha','2022-02-01','Consequences of Stubborness.',14,23,'00:08:20','Resources/podcast/ChildrenStory-Ziddi Bacha.wav');
insert into child_podcast_episodes values(1,2,'Ziddi Bacha','2022-02-01','Consequences of Stubborness.',14,24,'00:08:20','Resources/podcast/ChildrenStory-Ziddi Bacha.wav');
insert into child_podcast_episodes values(1,2,'Ziddi Bacha','2022-02-01','Consequences of Stubborness.',14,25,'00:08:20','Resources/podcast/ChildrenStory-Ziddi Bacha.wav');


create table playlist(
plid integer primary key auto_increment,
pname varchar(30),
CreatedOn Date,
lid integer,foreign key (lid) references login(lid));

 
 create table adult_songs_playlist(
 spid integer auto_increment,
 plid integer,foreign key (plid) references playlist(plid),
 sid integer,foreign key (sid) references adult_song(sid),
 primary key(spid,plid));
 
  
 create table child_songs_playlist(
 spid integer auto_increment,
 plid integer,foreign key (plid) references playlist(plid),
 sid integer,foreign key (sid) references child_song(sid),
 primary key(spid,plid));
 
create table child_rhymes_playlist(
 spid integer auto_increment,
 plid integer,foreign key (plid) references playlist(plid),
 sid integer,foreign key (sid) references child_rhymes(sid),
 primary key(spid,plid));


create table adult_podcast_playlist(
 ppid integer auto_increment,
 plid integer,foreign key (plid) references playlist(plid),
 podid integer,foreign key (podid) references adult_podcast(podid),
 primary key(ppid,plid));
 
 create table child_podcast_playlist(
 ppid integer auto_increment,
 plid integer,foreign key (plid) references playlist(plid),
 podid integer,foreign key (podid) references child_podcast(podid),
 primary key(ppid,plid));


use jukebox;
set sql_safe_updates=0;

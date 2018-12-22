/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.24-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `ticket_record` (
	`id` int (11),
	`ticket_num` text ,
	`story_point` double ,
	`users` text ,
	`description` text ,
	`date` date 
); 
insert into `ticket_record` (`id`, `ticket_num`, `story_point`, `users`, `description`, `date`) values('1','DOM-123','3','jianyang,jason,xianchen,da-long,yangliu','DOM-123:description','2018-12-22');

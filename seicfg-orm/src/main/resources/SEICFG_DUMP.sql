CREATE DATABASE  IF NOT EXISTS `seicfg`;
USE `seicfg`;

DROP TABLE IF EXISTS `seiuser`;
CREATE TABLE `seiuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `EmailId` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Type` varchar(255) NOT NULL,
  `isActive` varchar(1) NOT NULL,
  `Remarks` varchar(255) DEFAULT NULL,
  `CreationDateTime` datetime DEFAULT NULL,
  `Createdby` bigint(20) DEFAULT NULL,
  `Modifiedby` bigint(20) DEFAULT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `LastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
;

DROP TABLE IF EXISTS `seimessage`;
CREATE TABLE `seimessage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `Type` varchar(255) NOT NULL,
  `Text` varchar(255) DEFAULT NULL,
  `Solved` datetime DEFAULT NULL,
  `Remarks` varchar(255) DEFAULT NULL,
  `CreationDateTime` datetime DEFAULT NULL,
  `Createdby` bigint(20) DEFAULT NULL,
  `Modifiedby` bigint(20) DEFAULT NULL,
  `LastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_message_uid` (`uid`),
  CONSTRAINT `FK_message_uid` FOREIGN KEY (`uid`) REFERENCES `seiuser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `seiupdate`;
CREATE TABLE `seiupdate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `Type` varchar(255) NOT NULL,
  `Value` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `Remarks` varchar(255) DEFAULT NULL,
  `CreationDateTime` datetime DEFAULT NULL,
  `Createdby` bigint(20) DEFAULT NULL,
  `Modifiedby` bigint(20) DEFAULT NULL,
  `LastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_update_uid` (`uid`),
  CONSTRAINT `FK_update_uid` FOREIGN KEY (`uid`) REFERENCES `seiuser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `seimetric`;
CREATE TABLE `seimetric` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `Type` varchar(255) NOT NULL,
  `Value` bigint(20) DEFAULT NULL,
  `Remarks` varchar(255) DEFAULT NULL,
  `CreationDateTime` datetime DEFAULT NULL,
  `Createdby` bigint(20) DEFAULT NULL,
  `Modifiedby` bigint(20) DEFAULT NULL,
  `LastModifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_metric_uid` (`uid`),
  CONSTRAINT `FK_metric_uid` FOREIGN KEY (`uid`) REFERENCES `seiuser` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1;

commit;

INSERT INTO `seicfg`.`seiuser` (`id`, `EmailId`, `Password`, `Type`, `isActive`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastLogin`, `LastModifiedDate`) VALUES ('1', 'admin@sei.com', 'admin', 'Admin', '1', 'Administrator', '2013-01-20 11:28:44', '1', '1', '2013-01-20 11:28:44', '2013-01-20 11:28:44');
INSERT INTO `seicfg`.`seiuser` (`id`, `EmailId`, `Password`, `Type`, `isActive`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastLogin`, `LastModifiedDate`) VALUES ('2', 'client@abc.com', 'client', 'Admin', '1', 'Client', '2013-01-20 11:28:44', '1', '1', '2013-01-20 11:28:44', '2013-01-20 11:28:44');

INSERT INTO `seicfg`.`seimessage` (`id`, `uid`, `Type`, `Text`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastModifiedDate`) VALUES ('1', '2', 'Issue', 'Issue Number 1', 'Not yet solved', '2013-01-20 11:28:44', '2', '2', '2013-01-20 11:28:44');
INSERT INTO `seicfg`.`seimessage` (`id`, `uid`, `Type`, `Text`, `Solved`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastModifiedDate`) VALUES ('2', '2', 'Question', 'Question Number 2', '2013-01-20 11:28:44', 'Solved', '2013-01-20 11:28:44', '2', '2', '2013-01-20 11:28:44');

INSERT INTO `seicfg`.`seimetric` (`id`, `uid`, `Type`, `Value`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastModifiedDate`) VALUES ('1', '2', 'Money', '10000', 'Money spent', '2013-01-20 11:28:44', '2', '2', '2013-01-20 11:28:44');
INSERT INTO `seicfg`.`seimetric` (`id`, `uid`, `Type`, `Value`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastModifiedDate`) VALUES ('2', '2', 'Employee', '10', 'Employees', '2013-01-20 11:28:44', '2', '2', '2013-01-20 11:28:44');

INSERT INTO `seicfg`.`seiupdate` (`id`, `uid`, `Type`, `Value`, `Status`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastModifiedDate`) VALUES ('1', '2', 'Milestone', 'Description of Milestone', 'Achieved', 'Solved', '2013-01-20 11:28:44', '2', '2', '2013-01-20 11:28:44');
INSERT INTO `seicfg`.`seiupdate` (`id`, `uid`, `Type`, `Value`, `Status`, `Remarks`, `CreationDateTime`, `Createdby`, `Modifiedby`, `LastModifiedDate`) VALUES ('2', '2', 'Milestone2', 'Description of Milestone2', 'Not yet Achieved', 'Not yet Solved', '2013-01-20 11:28:44', '2', '2', '2013-01-20 11:28:44');





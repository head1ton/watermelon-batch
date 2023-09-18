CREATE TABLE `customer`
(
    `id` bigint unsigned NOT NULL auto_increment,
    `firstName` varchar(255) default NULL,
    `lastName`  varchar(255) default NULL,
    `birthDate` varchar(255),
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=1;
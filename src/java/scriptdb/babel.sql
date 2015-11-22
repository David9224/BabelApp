/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9-log : Database - babel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`babel` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `babel`;

/*Table structure for table `accesos_usuarios` */

DROP TABLE IF EXISTS `accesos_usuarios`;

CREATE TABLE `accesos_usuarios` (
  `Ac_Codi` int(11) NOT NULL AUTO_INCREMENT,
  `Ac_Cedu` int(11) NOT NULL,
  `Ac_Contra` varchar(10) NOT NULL,
  `Ac_Rol` int(11) NOT NULL,
  PRIMARY KEY (`Ac_Codi`),
  KEY `AC_USUA_FK` (`Ac_Cedu`),
  KEY `AC_rol_fk` (`Ac_Rol`),
  CONSTRAINT `AC_USUA_FK` FOREIGN KEY (`Ac_Cedu`) REFERENCES `usuarios` (`Cedula`),
  CONSTRAINT `AC_rol_fk` FOREIGN KEY (`Ac_Rol`) REFERENCES `roles` (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `accesos_usuarios` */

insert  into `accesos_usuarios`(`Ac_Codi`,`Ac_Cedu`,`Ac_Contra`,`Ac_Rol`) values (1,1112226107,'1234',1);

/*Table structure for table `categoria` */

DROP TABLE IF EXISTS `categoria`;

CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `categoria` */

insert  into `categoria`(`id_categoria`,`nombre`,`descripcion`) values (1,'lacteos','productos derivados de la leche'),(2,'2','2'),(3,'3','3'),(4,'a','5');

/*Table structure for table `cliente` */

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `id_cliente` int(12) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `direccion` varchar(30) NOT NULL,
  `telefono` int(15) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cliente` */

/*Table structure for table `detalle` */

DROP TABLE IF EXISTS `detalle`;

CREATE TABLE `detalle` (
  `num_detalle` int(11) NOT NULL,
  `id_factura` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` double NOT NULL,
  PRIMARY KEY (`num_detalle`,`id_factura`),
  KEY `id_factura` (`id_factura`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `detalle_ibfk_1` FOREIGN KEY (`id_factura`) REFERENCES `factura` (`num_factura`),
  CONSTRAINT `detalle_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `detalle` */

/*Table structure for table `factura` */

DROP TABLE IF EXISTS `factura`;

CREATE TABLE `factura` (
  `num_factura` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(12) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`num_factura`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `factura` */

/*Table structure for table `producto` */

DROP TABLE IF EXISTS `producto`;

CREATE TABLE `producto` (
  `id_producto` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` float NOT NULL,
  `cantidad_disponible` int(11) NOT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `producto` */

insert  into `producto`(`id_producto`,`id_categoria`,`nombre`,`precio`,`cantidad_disponible`) values (1,2,'v',1,1);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id_rol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_rol` varchar(50) NOT NULL,
  PRIMARY KEY (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`id_rol`,`nombre_rol`) values (1,'admin'),(2,'cajero'),(3,'mesero');

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `Cedula` int(11) NOT NULL,
  `Nombres` varchar(50) NOT NULL,
  `Apellidos` varchar(50) NOT NULL,
  `Direccion` varchar(100) NOT NULL,
  `Telefono` int(11) DEFAULT NULL,
  PRIMARY KEY (`Cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `usuarios` */

insert  into `usuarios`(`Cedula`,`Nombres`,`Apellidos`,`Direccion`,`Telefono`) values (3,'3','a','3',3),(111111,'anderson','hincapie','cll 41 # 26 - 41',222222222),(1112226107,'DAVID','ARISTIZABAL PEÑARANDA','CALLE 27#16-83',2865646);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

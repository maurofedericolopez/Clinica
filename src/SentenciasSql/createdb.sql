SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `clinica` ;
CREATE SCHEMA IF NOT EXISTS `clinica` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci ;
USE `clinica` ;

-- -----------------------------------------------------
-- Table `clinica`.`Paciente`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Paciente` (
  `idPaciente` INT NOT NULL AUTO_INCREMENT ,
  `dni` INT NOT NULL ,
  `apellido` VARCHAR(45) NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `telefono` VARCHAR(45) NULL ,
  `domicilio` VARCHAR(100) NULL ,
  PRIMARY KEY (`idPaciente`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clinica`.`Droga`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Droga` (
  `idDroga` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(45) NOT NULL ,
  `referencia` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`idDroga`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clinica`.`Edificio`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Edificio` (
  `idEdificio` INT NOT NULL AUTO_INCREMENT ,
  `numero` INT NOT NULL ,
  `direccion` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(45) NULL ,
  PRIMARY KEY (`idEdificio`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clinica`.`Enfermera`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Enfermera` (
  `idEnfermera` INT NOT NULL AUTO_INCREMENT ,
  `apellido` VARCHAR(45) NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `Enfermera_idEnfermera` INT NULL ,
  PRIMARY KEY (`idEnfermera`) ,
  CONSTRAINT `fk_Enfermera_Enfermera`
    FOREIGN KEY (`Enfermera_idEnfermera` )
    REFERENCES `clinica`.`Enfermera` (`idEnfermera` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Enfermera_Enfermera1` ON `clinica`.`Enfermera` (`Enfermera_idEnfermera` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`Piso`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Piso` (
  `idPiso` INT NOT NULL AUTO_INCREMENT ,
  `numero` INT NOT NULL ,
  `Edificio_idEdificio` INT NOT NULL ,
  `Enfermera_idEnfermera` INT NOT NULL ,
  PRIMARY KEY (`idPiso`) ,
  CONSTRAINT `fk_Piso_Edificio`
    FOREIGN KEY (`Edificio_idEdificio` )
    REFERENCES `clinica`.`Edificio` (`idEdificio` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Piso_Enfermera`
    FOREIGN KEY (`Enfermera_idEnfermera` )
    REFERENCES `clinica`.`Enfermera` (`idEnfermera` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Piso_Edificio1` ON `clinica`.`Piso` (`Edificio_idEdificio` ASC) ;

CREATE INDEX `fk_Piso_Enfermera1` ON `clinica`.`Piso` (`Enfermera_idEnfermera` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`Propietario`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Propietario` (
  `idPropietario` INT NOT NULL AUTO_INCREMENT ,
  `dni` INT NOT NULL ,
  `apellido` VARCHAR(45) NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `telefono` VARCHAR(45) NULL ,
  `domicilio` VARCHAR(100) NULL ,
  PRIMARY KEY (`idPropietario`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clinica`.`Consultorio`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Consultorio` (
  `idConsultorio` INT NOT NULL AUTO_INCREMENT ,
  `numero` INT NOT NULL ,
  `longitud` DECIMAL NOT NULL ,
  `ventanas` INT NOT NULL ,
  `baño` TINYINT(1)  NOT NULL ,
  `cama` TINYINT(1)  NOT NULL ,
  `closet` TINYINT(1)  NOT NULL ,
  `ultimaFechaRemodelacion` DATE NULL ,
  `valor` DOUBLE NOT NULL ,
  `Piso_idPiso` INT NOT NULL ,
  `Propietario_idPropietario` INT NOT NULL ,
  PRIMARY KEY (`idConsultorio`) ,
  CONSTRAINT `fk_Consultorio_Piso`
    FOREIGN KEY (`Piso_idPiso` )
    REFERENCES `clinica`.`Piso` (`idPiso` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Consultorio_Propietario`
    FOREIGN KEY (`Propietario_idPropietario` )
    REFERENCES `clinica`.`Propietario` (`idPropietario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Consultorio_Piso1` ON `clinica`.`Consultorio` (`Piso_idPiso` ASC) ;

CREATE INDEX `fk_Consultorio_Propietario1` ON `clinica`.`Consultorio` (`Propietario_idPropietario` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`Habitacion`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Habitacion` (
  `idHabitacion` INT NOT NULL AUTO_INCREMENT ,
  `numero` INT NOT NULL ,
  `longitud` DECIMAL NOT NULL ,
  `ventanas` INT NOT NULL ,
  `baño` TINYINT(1)  NOT NULL ,
  `cama` TINYINT(1)  NOT NULL ,
  `closet` TINYINT(1)  NOT NULL ,
  `ultimaFechaRemodelacion` DATE NULL ,
  `valor` DOUBLE NOT NULL ,
  `Piso_idPiso` INT NOT NULL ,
  `Propietario_idPropietario` INT NOT NULL ,
  PRIMARY KEY (`idHabitacion`) ,
  CONSTRAINT `fk_Habitacion_Piso`
    FOREIGN KEY (`Piso_idPiso` )
    REFERENCES `clinica`.`Piso` (`idPiso` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Habitacion_Propietario`
    FOREIGN KEY (`Propietario_idPropietario` )
    REFERENCES `clinica`.`Propietario` (`idPropietario` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Habitacion_Piso1` ON `clinica`.`Habitacion` (`Piso_idPiso` ASC) ;

CREATE INDEX `fk_Habitacion_Propietario1` ON `clinica`.`Habitacion` (`Propietario_idPropietario` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`TipoServicio`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`TipoServicio` (
  `idTipoServicio` INT NOT NULL AUTO_INCREMENT ,
  `descripcion` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`idTipoServicio`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clinica`.`Doctor`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Doctor` (
  `idDoctor` INT NOT NULL AUTO_INCREMENT ,
  `apellido` VARCHAR(45) NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `especialidad` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idDoctor`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `clinica`.`Servicio`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Servicio` (
  `idServicio` INT NOT NULL AUTO_INCREMENT ,
  `fechaConsultaIngreso` DATE NOT NULL ,
  `fechaSalida` DATE NULL ,
  `obraSocial` VARCHAR(45) NOT NULL ,
  `valor` DOUBLE NOT NULL ,
  `TipoServicio_idTipoServicio` INT NOT NULL ,
  `tipoExamen` VARCHAR(45) NULL ,
  `maquinasExamen` INT NULL ,
  `resultadoExamen` VARCHAR(100) NULL ,
  `valorRestauranteHospitalizacion` DOUBLE NULL ,
  `diasHospitalizacion` INT NULL ,
  `visitasHospitalizacion` INT NULL ,
  `suministroHospitalizacion` VARCHAR(100) NULL ,
  `valorSuministroHospitalizacion` DOUBLE NULL ,
  `Doctor_idDoctor` INT NOT NULL ,
  `Paciente_idPaciente` INT NOT NULL ,
  PRIMARY KEY (`idServicio`) ,
  CONSTRAINT `fk_Servicio_TipoServicio`
    FOREIGN KEY (`TipoServicio_idTipoServicio` )
    REFERENCES `clinica`.`TipoServicio` (`idTipoServicio` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Servicio_Doctor`
    FOREIGN KEY (`Doctor_idDoctor` )
    REFERENCES `clinica`.`Doctor` (`idDoctor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Servicio_Paciente`
    FOREIGN KEY (`Paciente_idPaciente` )
    REFERENCES `clinica`.`Paciente` (`idPaciente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Servicio_TipoServicio1` ON `clinica`.`Servicio` (`TipoServicio_idTipoServicio` ASC) ;

CREATE INDEX `fk_Servicio_Doctor1` ON `clinica`.`Servicio` (`Doctor_idDoctor` ASC) ;

CREATE INDEX `fk_Servicio_Paciente1` ON `clinica`.`Servicio` (`Paciente_idPaciente` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`Visita`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Visita` (
  `idVisita` INT NOT NULL AUTO_INCREMENT ,
  `fecha` DATE NOT NULL ,
  `hora` TIME NULL ,
  `tratamiento` VARCHAR(200) NOT NULL ,
  `estadoPaciente` VARCHAR(200) NOT NULL ,
  `Paciente_idPaciente` INT NOT NULL ,
  `Doctor_idDoctor` INT NOT NULL ,
  PRIMARY KEY (`idVisita`) ,
  CONSTRAINT `fk_Visita_Paciente`
    FOREIGN KEY (`Paciente_idPaciente` )
    REFERENCES `clinica`.`Paciente` (`idPaciente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Visita_Doctor`
    FOREIGN KEY (`Doctor_idDoctor` )
    REFERENCES `clinica`.`Doctor` (`idDoctor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Visita_Paciente1` ON `clinica`.`Visita` (`Paciente_idPaciente` ASC) ;

CREATE INDEX `fk_Visita_Doctor1` ON `clinica`.`Visita` (`Doctor_idDoctor` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`DrogaServicio`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`DrogaServicio` (
  `idDrogaServicio` INT NOT NULL AUTO_INCREMENT ,
  `dosis` VARCHAR(100) NOT NULL ,
  `Servicio_idServicio` INT NOT NULL ,
  `Droga_idDroga` INT NOT NULL ,
  PRIMARY KEY (`idDrogaServicio`) ,
  CONSTRAINT `fk_DrogaServicio_Servicio`
    FOREIGN KEY (`Servicio_idServicio` )
    REFERENCES `clinica`.`Servicio` (`idServicio` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DrogaServicio_Droga`
    FOREIGN KEY (`Droga_idDroga` )
    REFERENCES `clinica`.`Droga` (`idDroga` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_DrogaServicio_Servicio1` ON `clinica`.`DrogaServicio` (`Servicio_idServicio` ASC) ;

CREATE INDEX `fk_DrogaServicio_Droga1` ON `clinica`.`DrogaServicio` (`Droga_idDroga` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`DrogaVisita`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`DrogaVisita` (
  `idDrogaVisita` INT NOT NULL ,
  `Visita_idVisita` INT NOT NULL ,
  `Droga_idDroga` INT NOT NULL ,
  PRIMARY KEY (`idDrogaVisita`) ,
  CONSTRAINT `fk_DrogaVisita_Visita`
    FOREIGN KEY (`Visita_idVisita` )
    REFERENCES `clinica`.`Visita` (`idVisita` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DrogaVisita_Droga`
    FOREIGN KEY (`Droga_idDroga` )
    REFERENCES `clinica`.`Droga` (`idDroga` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_DrogaVisita_Visita1` ON `clinica`.`DrogaVisita` (`Visita_idVisita` ASC) ;

CREATE INDEX `fk_DrogaVisita_Droga1` ON `clinica`.`DrogaVisita` (`Droga_idDroga` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`Auxiliar`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`Auxiliar` (
  `idAuxiliar` INT NOT NULL AUTO_INCREMENT ,
  `apellido` VARCHAR(45) NOT NULL ,
  `nombre` VARCHAR(45) NOT NULL ,
  `Enfermera_idEnfermera` INT NOT NULL ,
  PRIMARY KEY (`idAuxiliar`) ,
  CONSTRAINT `fk_Auxiliar_Enfermera`
    FOREIGN KEY (`Enfermera_idEnfermera` )
    REFERENCES `clinica`.`Enfermera` (`idEnfermera` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Auxiliar_Enfermera1` ON `clinica`.`Auxiliar` (`Enfermera_idEnfermera` ASC) ;


-- -----------------------------------------------------
-- Table `clinica`.`HabitacionPaciente`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`HabitacionPaciente` (
  `idHabitacionPaciente` INT NOT NULL AUTO_INCREMENT ,
  `Paciente_idPaciente` INT NOT NULL ,
  `Habitacion_idHabitacion` INT NOT NULL ,
  `fecha` DATE NOT NULL ,
  `tiempo` INT NULL ,
  PRIMARY KEY (`idHabitacionPaciente`) ,
  CONSTRAINT `fk_HabitacionPaciente_Paciente`
    FOREIGN KEY (`Paciente_idPaciente` )
    REFERENCES `clinica`.`Paciente` (`idPaciente` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HabitacionPaciente_Habitacion`
    FOREIGN KEY (`Habitacion_idHabitacion` )
    REFERENCES `clinica`.`Habitacion` (`idHabitacion` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_HabitacionPaciente_Paciente1` ON `clinica`.`HabitacionPaciente` (`Paciente_idPaciente` ASC) ;

CREATE INDEX `fk_HabitacionPaciente_Habitacion1` ON `clinica`.`HabitacionPaciente` (`Habitacion_idHabitacion` ASC) ;

-- -----------------------------------------------------
-- Table `clinica`.`sequence`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `clinica`.`sequence` (
  `SEQ_NAME` VARCHAR(45) NOT NULL ,
  `SEQ_COUNT` decimal(38,0) ,
  PRIMARY KEY (`SEQ_NAME`) )
ENGINE = InnoDB;




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


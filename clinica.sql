-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-11-2023 a las 17:28:28
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `clinica`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargos`
--

CREATE TABLE `cargos` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidades`
--

CREATE TABLE `especialidades` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarios`
--

CREATE TABLE `horarios` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `orden` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `obras_sociales`
--

CREATE TABLE `obras_sociales` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `obras_sociales`
--

INSERT INTO `obras_sociales` (`id`, `descripcion`) VALUES
(1, 'INSSEP'),
(2, 'FADFSD'),
(3, 'ADSFASD');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pacientes`
--

CREATE TABLE `pacientes` (
  `id` int(11) NOT NULL,
  `apellido` varchar(20) DEFAULT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `domicilio` varchar(60) DEFAULT NULL,
  `dni_paciente` varchar(10) DEFAULT NULL,
  `localidad` varchar(60) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  `celulares` varchar(50) DEFAULT NULL,
  `telefonos` varchar(50) DEFAULT NULL,
  `emails` varchar(200) DEFAULT NULL,
  `id_obrasocial` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `pacientes`
--

INSERT INTO `pacientes` (`id`, `apellido`, `nombre`, `domicilio`, `dni_paciente`, `localidad`, `provincia`, `celulares`, `telefonos`, `emails`, `id_obrasocial`) VALUES
(1, 'ARAUJO', 'LUCIANO', NULL, '4295654', NULL, NULL, NULL, '(3644)-7-2443333', 'fdhsdfghjsdfgj', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personales`
--

CREATE TABLE `personales` (
  `id` int(11) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `cargo_id` int(11) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesionales`
--

CREATE TABLE `profesionales` (
  `id` int(11) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `matricula` int(11) NOT NULL,
  `titulo_profesional` varchar(40) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `foto` varchar(50) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesionales_especialidades`
--

CREATE TABLE `profesionales_especialidades` (
  `id` int(11) NOT NULL,
  `profesional_id` int(11) NOT NULL,
  `especialidad_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turnos`
--

CREATE TABLE `turnos` (
  `id` int(11) NOT NULL,
  `profesional_id` int(11) NOT NULL,
  `especialidad_id` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `apellido_paciente` varchar(20) NOT NULL,
  `nombre_paciente` varchar(40) NOT NULL,
  `dni_paciente` varchar(10) NOT NULL,
  `obra_social` int(11) DEFAULT NULL,
  `nro_credencial` varchar(10) DEFAULT NULL,
  `atencion_particular` tinyint(1) DEFAULT NULL,
  `motivo_turno` text NOT NULL,
  `estado` smallint(6) NOT NULL,
  `diagnostico` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cargos`
--
ALTER TABLE `cargos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `obras_sociales`
--
ALTER TABLE `obras_sociales`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_obrasocial` (`id_obrasocial`);

--
-- Indices de la tabla `personales`
--
ALTER TABLE `personales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cargo_id` (`cargo_id`);

--
-- Indices de la tabla `profesionales`
--
ALTER TABLE `profesionales`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `profesionales_especialidades`
--
ALTER TABLE `profesionales_especialidades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_especialidad` (`especialidad_id`),
  ADD KEY `fk_profesional` (`profesional_id`);

--
-- Indices de la tabla `turnos`
--
ALTER TABLE `turnos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_profesional_turnos` (`profesional_id`),
  ADD KEY `fk_especialidad_turnos` (`especialidad_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cargos`
--
ALTER TABLE `cargos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `horarios`
--
ALTER TABLE `horarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `obras_sociales`
--
ALTER TABLE `obras_sociales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `personales`
--
ALTER TABLE `personales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `profesionales`
--
ALTER TABLE `profesionales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `profesionales_especialidades`
--
ALTER TABLE `profesionales_especialidades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `turnos`
--
ALTER TABLE `turnos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD CONSTRAINT `pacientes_ibfk_1` FOREIGN KEY (`id_obrasocial`) REFERENCES `obras_sociales` (`id`);

--
-- Filtros para la tabla `personales`
--
ALTER TABLE `personales`
  ADD CONSTRAINT `personales_ibfk_1` FOREIGN KEY (`cargo_id`) REFERENCES `cargos` (`id`);

--
-- Filtros para la tabla `profesionales_especialidades`
--
ALTER TABLE `profesionales_especialidades`
  ADD CONSTRAINT `fk_especialidad` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidades` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_profesional` FOREIGN KEY (`profesional_id`) REFERENCES `profesionales` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `turnos`
--
ALTER TABLE `turnos`
  ADD CONSTRAINT `fk_especialidad_turnos` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidades` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_profesional_turnos` FOREIGN KEY (`profesional_id`) REFERENCES `profesionales` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

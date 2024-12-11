Documentación del Sistema de Gestión de Clínica
1. Descripción General del Sistema

El sistema de gestión de clínica tiene como objetivo automatizar la administración de pacientes, profesionales de la salud, secretarios y administradores de una clínica. A través de una interfaz gráfica, los usuarios pueden realizar diferentes tareas según el rol que se les asigne, con controles de acceso y funciones específicas.

Principales características del sistema:

    Gestión de usuarios (administradores, secretarios, profesionales).
    Carga y gestión de pacientes, turnos, horarios, especialidades y más.
    Conexión con base de datos MySQL para el almacenamiento de la información.

2. Requerimientos del Sistema

Para la correcta ejecución del sistema, es necesario tener instalado lo siguiente:

    Java Development Kit (JDK): Se recomienda utilizar la versión 8 o superior de JDK.
    NetBeans IDE: La plataforma de desarrollo recomendada para la ejecución y modificación del proyecto.
    Base de Datos MySQL: Se debe contar con una base de datos MySQL que el sistema utilizará para almacenar la información.
    Conector MySQL (JDBC): El conector MySQL version 5.1.36 debe estar en el proyecto para establecer la conexión con la base de datos.

3. Instalación del Sistema

Pasos para la instalación:

    Descargar los archivos del proyecto: Obtén el proyecto de la clínica (4 MB) y asegúrate de tener la carpeta de la base de datos (ubicada en src/BD_Clinica).

    Configurar la Base de Datos:
        Dentro de la carpeta src/BD_Clinica, encontrarás los scripts SQL necesarios para crear la base de datos y las tablas correspondientes.
        Abre MySQL Workbench o cualquier cliente SQL y ejecuta el script para crear la base de datos.

    Conectar el Proyecto con la Base de Datos:
        Dentro del proyecto en NetBeans, asegúrate de tener el conector JDBC de MySQL versión 5.1.36, que puedes encontrar en la carpeta raíz del proyecto.
        Este conector debe ser cargado para que el sistema pueda realizar las operaciones con la base de datos.

    Configuración en NetBeans:
        Abre el proyecto en NetBeans.
        Verifica que el driver JDBC esté correctamente agregado al proyecto. Si no es así, agrega el archivo JAR correspondiente.
        Ejecuta el proyecto desde NetBeans. Al iniciar, el sistema solicitará la confirmación de la base de datos y las credenciales de acceso.

4. Estructura de la Aplicación

El sistema está dividido en diferentes paneles de acuerdo con los roles de usuario: Administrador, Secretario y Profesional (Médico).

    Pantalla Principal: La primera pantalla solicita la confirmación de la base de datos y las credenciales del usuario. Dependiendo del rol del usuario, se redirige a una interfaz diferente.

    Panel de Administrador:
        El administrador tiene acceso completo al sistema y puede gestionar usuarios (otros administradores, secretarios, profesionales).
        También puede agregar y editar especialidades, turnos, horarios, pacientes, cargos y obras sociales.

    Panel de Secretario:
        Los secretarios pueden registrar nuevos pacientes y agendar turnos para ellos.

    Panel de Profesional (Médico):
        Los médicos tienen acceso a los pacientes que están bajo su cargo.
        Pueden visualizar los horarios de consulta y administrar los pacientes.

5. Funcionalidad Detallada del Sistema

Panel de Administrador:

    Gestión de Usuarios: Permite al administrador agregar o eliminar otros administradores, secretarios y profesionales.
    Gestión de Pacientes: Los administradores pueden agregar, editar o eliminar pacientes.
    Gestión de Cargos y Especialidades: Se pueden agregar nuevos cargos para los profesionales y especialidades para los pacientes.
    Gestión de Turnos y Horarios: Se pueden establecer y modificar los turnos y horarios de atención de los profesionales.
    Gestión de Obras Sociales: El administrador puede registrar y gestionar las obras sociales disponibles.

Panel de Secretario:

    Registro de Pacientes: Los secretarios pueden ingresar nuevos pacientes al sistema.
    Agendar Turnos: Los secretarios pueden programar turnos para los pacientes, asignándoles profesionales y horarios.

Panel de Profesional (Médico):

    Administración de Pacientes: Los médicos pueden gestionar a los pacientes asignados a su consulta.
    Visualización de Horarios: Los médicos pueden ver sus horarios de consulta disponibles y ocupados.

6. Conexión a la Base de Datos

El sistema se conecta a la base de datos MySQL utilizando JDBC (Java Database Connectivity). Asegúrate de que el conector esté bien configurado en el proyecto. La conexión con la base de datos debe ser establecida con las credenciales correctas.

La cadena de conexión JDBC típica se ve así:

String url = "jdbc:mysql://localhost:3306/nombre_de_base_de_datos";
String user = "usuario";
String password = "contraseña";

7. Detalles Técnicos

El proyecto está basado en una arquitectura MVC (Modelo-Vista-Controlador), donde:

    Modelo: Maneja las operaciones de la base de datos y la lógica del negocio.
    Vista: Presenta la interfaz gráfica de usuario (GUI) utilizando Java Swing o JavaFX.
    Controlador: Se encarga de gestionar la interacción entre el modelo y la vista.

8. Problemas Conocidos y Soluciones

    Problema 1: La base de datos no se conecta.
        Solución: Asegúrate de que el archivo mysql-connector-java-5.1.36.jar esté correctamente añadido al proyecto y que las credenciales de la base de datos sean correctas.
    Problema 2: Los horarios no se guardan correctamente.
        Solución: Verifica que las consultas SQL para la inserción de turnos estén correctamente configuradas en el modelo.

9. Futuras Mejoras

    Mejorar la interfaz de usuario con tecnologías más modernas como JavaFX.
    Añadir la capacidad de generar reportes de pacientes, turnos y obras sociales.
    Implementar la funcionalidad de enviar recordatorios de turnos por correo electrónico.

Conclusión

Este sistema de gestión de clínica tiene como objetivo mejorar la eficiencia en la administración de pacientes y profesionales de la salud, asegurando un flujo de trabajo organizado y eficiente. Con un diseño modular, es fácilmente extensible para adaptarse a nuevas funcionalidades en el futuro.
# TallerMecanicoPA2023

A continuacion se describen los pasos a seguir para llevar a cabo la **__configuracion basica necesaria__** para el correcto funcionamiento del sistema "Taller Mecanico". 


## Configuracion Base de Datos

El sistema se encuntra desarollado utilizando como motor de base de datos "MySQL". Debemos asegurarnos nombrar a la base de datos/schema como **"tallermecanico"**  para garantizar el correcto funcionamiento del mismo. 
En caso de ser necesario **cambiar el nombre de la base de datos**, se debe modificar la configuracion definida dentro del archivo __application.properties__.

* spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306 **nombre_base_datos**?serverTimeZone=UTC

Una vez configurada la base de datos, se debera compilar y ejecutar el sistema para que el gestor (JPA) pueda crear automaticamente las entidades necesarias. 


### Implementacion de SCRIPT SQL

El repositorio cuenta con un script SQL con datos personalizados para poder probar el sistema. 

Los scripts pueden ser ejecutados a traves de la `terminal` y/o importar el mismo en `MySQL Workbench` en caso de que sea utilizado. 

**PASOS PARA IMPORTAR SCRIPT EN MySQL Workbench**

* Dentro de MySQL Workbench creamos un nuevo "schema" con el nombre --> tallermecanico (tal cual esta, sin Mayus)
* Ejecutamos el codigo spring boot para que JPA pueda crear las entidades correspondientes (Si es schema creado anteriormente tiene otro nombre, no funcionara. Se debera cambiar la configuracion de la DB en el sistema)
* Dentro del apartado "administration" de MySQL Workbench seleccionamos la opcion "Data Import/Resotre" (EL SISTEMA DEBE ESTAR APAGADO ANTES DE IMPORTAR LA DATA)
* Seleccionamos la opcion "Import from Self-contained file" y buscamos el script .sql incluido en este directorio.
* En el apartado "Default target schema" seleccionamos el schema "tallermecanico" 
* Finalmente seleccionamos la opcion "Start import" 
* Iniciamos nuevamente el sistema. En este momento ya tendremos informacion cargada.  



__Antes de hacer utilizacion de los scripts SQL asegurarse que el sistema se encuentre apagado__ 

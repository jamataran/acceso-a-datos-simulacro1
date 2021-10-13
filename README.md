# Simulacro de Examen de Acceso a Datos

A continuación, podéis encontrar un examen de cursos pasados de la asignatura Acceso a Datos, con su estructura, preguntas y ponderación.

Se recomienda la realización para adquirir el bagaje necesario para superar el módulo.

## Instrucciones

Antes de empezar, verifica que puedes ejecutar Jhipster en tu ordenador, si no es así, prueba a ejecutar el comando:

`npm install -g generator-jhipster`

Antes de realizar el examen deberás haber generado un proyecto jhipster con las instrucciones que os hemos proporcionado previamente.
Hay que partir de dicho proyecto para hacer los ejercicios que os solicitamos a continuación.

**El tiempo estimado para la realización de este ejercicio es de 150 minutos**.

## Entrega

La entrega consistirá en un proyecto spring generado con Jhipster.
Deberás entregar un fichero comprimido que contenga la carpeta del proyecto, **eliminando la subcarpeta `target` antes de comprimirlo** para que ocupe menos espacio. Puedes eliminar esta carpeta ejecutando el comando mvn clean.
Antes de entregar tu aplicación, no te olvides de verificar que arranca correctamente.

## Ejercicio

### Estructura

_AIR CEV_, la nueva compañía de vuelos de bajo coste nacerá el año 2023 con el objetivo de traer unas tarifas
disruptivas sobre el mercado, ya de por si dañado por la crisis de coronavirus. Como desarrolladores, nos han encargado
realizar una aplicación para la gestión básica de sus recursos.

_AIR CEV_ tendrá tres recursos principales: aviones, pilotos y tripulación de cabina que operarán los vuelos que consigan.
De cada piloto se desea conocer su nombre, DNI/NIF/NIE, dirección, correo electrónico, horas de vuelo así como almacenar
una fotografía del momento de su incorporación a la compañia. En el caso de la tripulación de cabina, recogeremos todos
esos datos a excepción de las horas de vuelo. Cada avión tiene una matrícula, es de un tipo (Boeing 737-8AS con matrícula 9H-QAV), 
tiene una edad un número de serie (12 años, S192817SZZSLD). 

Un vuelo que va desde un origen a un destino y a una hora determinada, tiene un número de
vuelo (por ejemplo, _el vuelo de Palma a Alicante de las 13:50 es el vuelo IB-8830_). De cada vuelo que se va a
realizar durante los próximos tres meses, así como de los vuelos que ya se han realizado, se desea saber el
avión en que se va a hacer o en el que se ha hecho, el piloto (en _AIR CEV_, únicamente habrá un piloto, para ahorrar costes) y  
cada uno de los miembros de la tripulación que iban en él.

Todos los datos que se introduzcan en la aplicación, deben de ser validados, conforme a las siguientes reglas:
* DNI: Debe cumplir el patron: `[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]`.
* Nombres y Apellidos: Requeridos, con longitud entre 10 y 255.
* Correos electrónicos deben cumplir el patrón: `^[^@\s]+@[^@\s]+\.[^@\s]+$`
* La matrícula de un avión debe contener un guión.
* La edad mínima de un avión es 0 años, no puede haber aviones con edades negativas.

### Métricas

#### M1: Métrica 1

Obtener el avión más viejo de la compañía.

#### M2: Métrica 2

Para un piloto, del que se conoce su DNI, listado (paginado) de vuelos en los que ha participado.

#### M3: Métrica 3

Número total de vuelos de un tripulante, por DNI

## Calificaciones

**Debes conseguir los objetivos mínimos PASS para ser valorado de los criterios de MERIT; es decir, que los aspectos nombrados tanto en MERIT como en DISTINCTION no se tomarán en cuenta si no se cumplen con todos los requisitos PASS.**

### Pass (Nota de 50 a 64)

* Objetivo P1 _(5 puntos)_: Se ha creado el proyecto utilizando JHipster
* Objetivo P2 _(27 puntos)_: Se han definido las entidades correctamente.
* Objetivo P3 _(27 puntos)_: Se han definido las relaciones entre entidades correctamente.
* Objetivo P4 _(5 puntos)_: Se han generado, por cada entidad, controladores, servicios y repositorios.

### Merit (Nota de 64 a 84)

* Objetivo M1 _(5 puntos)_: Se ha definido la consulta asociada a la métrica 1 (M1).
* Objetivo M2 _(5 puntos)_: Se ha definido la consulta asociada a la métrica 2 (M2).
* Objetivo M3 _(5 puntos)_: Se ha definido la consulta asociada a la métrica 3 (M3).
* Objetivo M4 _(5 puntos)_: Las trés métricas (M1, M2 y M3) son accesibles (se ha creado un controlador y/o los métodos en los controladores existentes que permiten su correcta consulta) y se han validado los datos.

### Disctinction (Nota de 85 a 100)

* Objetivo D1 _(15 puntos)_:Se han obtenido las métricas sólo con JPA sin realizar cálculos o filtrados posteriores en memoria. 


# ¿Como abordar un examen de Acceso a Datos? (*)

Para superar el módulo de acceso a datos necesitamos tener bastante práctica con todas las herramientas
que se han explicado en la asignatura y **haber prácticado mucho**, sin esto, nada de lo que hagamos
hará que superemos el módulo.

## Pasos conseguir superar el examen.
1. Leer bien las instrucciones.
2. Antes de liarnos con el teclado, utilizar un papel auxiliar para diseñar un esquema E/R de lo que vamos a hacer.
3. Crear las entidades, **inicialmente sin relaciones**.
4. Arrancar la aplicación y ver que todo funciona OK.
5. Crear las relaciones
   1. Crear primero los sentidos _Many to One_ y, a continuación, los _One to Many_.
   2. Siempre tiene prioridad las relaciones _Owner_
   3. Siempre hacer commit y probar despues de cada relación. Si algo ha fallado, hacer _Rollback_.
6. A por las métricas
   1. ¿Qué debo devolver?: Esta pregunta me dará la pista de en qué repositorio debo atacar.
   2. Construyo la query, de más fácil a más dificil (añadiendo progresivamente filtros)
   3. Enlazo con servicio y controlador (_Resource_)
7. Prueba, comprime y entrega (unos minutos antes del final)
   1. Prueba tu solución.
   2. Elimina el directorio `target`
   3. Comprime la solución
   4. Entrega

(*) Esta sección no estará en el examen.


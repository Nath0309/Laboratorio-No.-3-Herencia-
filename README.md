# Laboratorio-No.-3-Herencia-
Proyecto EnvíosGT, basado en un programa para control empresarial. 

# EnvíaGT — Laboratorio 3

Aplicación de consola en Java basada en el análisis y el diseño proporcionados. Administra cotizaciones, envíos, vehículos, despachos, entregas e ingresos en memoria.

## Compilar y ejecutar

Se necesita un JDK 8 o posterior, con `java` y `javac` disponibles en la terminal. Desde la carpeta `EnviaGT`:

```text
mkdir bin
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Main
```

En Windows, si los acentos no se muestran correctamente, use una terminal UTF-8 (`chcp 65001`) antes de ejecutar. Los decimales se introducen con punto. Las confirmaciones aceptan `s` o `n`. La opción `0` termina el programa; los datos se pierden al cerrarlo.


## Datos iniciales

Todos los envíos comienzan en bodega, con cero intentos y sin generar ingresos. Las medidas se expresan como largo × ancho × alto, en centímetros.

| Código | Categoría | Destino | Peso kg | Datos adicionales | Costo Q | Días hábiles |
|---|---|---|---:|---|---:|---:|
| S001 | Sobre | Urbano | 0.3 | — | 20.00 | 1 |
| S002 | Sobre | Departamental | 0.5 | — | 35.00 | 2 |
| P001 | Paquete | Urbano | 3.2 | 40 × 30 × 20; volumétrico 4.8 kg | 65.00 | 2 |
| P002 | Paquete | Departamental | 5.2 | 10 × 10 × 10; se facturan 6 kg | 88.00 | 4 |
| F001 | Paquete frágil | Urbano | 3.2 | 40 × 30 × 20; valor Q1200 | 116.00 | 3 |
| F002 | Paquete frágil | Departamental | 2.1 | 10 × 10 × 10; valor Q500 | 94.00 | 5 |
| R001 | Carga refrigerada | Urbano | 2.5 | −25 °C | 115.00 | 1 |
| R002 | Carga refrigerada | Urbano | 10 | 8 °C | 205.00 | 1 |

Vehículos: `V001`, 5 kg sin refrigeración; `V002`, 30 kg sin refrigeración; `V003`, 50 kg con refrigeración. Todos comienzan libres.

## Menú

1. Cotizar y, si se acepta, registrar un envío.
2. Registrar un vehículo.
3. Crear un despacho indicando la placa.
4. Asignar un envío por número de despacho y código.
5. Retirar un envío de un despacho en preparación.
6. Hacer salir un despacho.
7. Registrar el resultado de un envío; repetir para los demás envíos del despacho.
8. Consultar todos los envíos.
9. Consultar los vehículos.
10. Consultar el conteo por categoría y estado.
11. Consultar ingresos por registro, reintentos y categoría.
12. Consultar despachos no cerrados.
0. Salir.

## Pruebas

La clase de pruebas está en `pruebas`, fuera de las 16 clases de la aplicación. No se necesita para ejecutar el programa.

```text
javac -encoding UTF-8 -cp bin -d bin pruebas/PruebasLaboratorio.java
java -cp bin PruebasLaboratorio
```



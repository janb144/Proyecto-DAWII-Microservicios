# Proyecto DAWII — Sistema de Taller de Motos (Microservicios)

Sistema de gestión para un **taller de motos** construido con **Spring Boot + Spring Cloud**
(arquitectura de microservicios). Cubre el flujo completo: registro de clientes y proveedores,
gestión de repuestos e inventario, órdenes de mantenimiento, facturación electrónica y envío
de comprobantes a la **SUNAT** (entidad tributaria de Perú).

Proyecto académico de **Cibertec** (curso *Desarrollo de Aplicaciones Web II*). El código,
comentarios y nombres de dominio están en **español**; el package base es `cibertec.pe`.

## Stack

- **Java 21**, **Spring Boot 4.0.6**, **Spring Cloud** (Netflix Eureka + OpenFeign + Gateway)
- **MySQL** en la nube (Aiven) — base de datos compartida `tallermoto_db`, todos los servicios
  apuntan al mismo host con `ddl-auto=update`
- Build con **Maven** (cada servicio tiene su `pom.xml` y wrapper `mvnw`)
- Autenticación con **JWT** (Spring Security en Identity-Service, validación en el Gateway)
- IDE de origen: **Eclipse/STS** (de ahí las carpetas `.metadata/`, `.settings/`)

## Microservicios

| Servicio | Puerto | Rol |
|---|---|---|
| **EUREKA-Server** | 8761 | Service discovery (registro de todos los servicios) |
| **GATEWAY-Service** | 8080 | API Gateway (Spring Cloud Gateway) + filtro de autenticación JWT |
| **Identity-Service** | 9007 | Registro/login de usuarios, emisión y validación de tokens JWT |
| **Taller-Service** | 9001 | Órdenes de mantenimiento de motos (`Mantenimiento`) |
| **Repuesto-Service** | 9002 | Catálogo de repuestos e inventario/stock (`Repuesto`) |
| **Facturacion-Service** | 9003 | Genera comprobantes (boleta/factura) orquestando otros servicios |
| **Cliente-Service** | 9004 | CRUD de clientes (`Cliente`, con `TipoDocumento` DNI/RUC) |
| **Proveedor-Service** | 9005 | CRUD de proveedores (`Proveedor`) |
| **Sunat-Service** | 9006 | Genera XML UBL, lo firma, lo comprime y lo envía a SUNAT vía SOAP |

> Nota: algunos servicios tienen carpeta anidada (`Cliente-Service/Cliente-Service`,
> `GATEWAY-Service/GATEWAY-Service`); el proyecto Maven real está en la subcarpeta.

## Ruteo del Gateway (todas requieren JWT salvo `/auth/**`)

- `/api/taller/**` → TALLER-SERVICE
- `/api/repuesto/**` → REPUESTO-SERVICE
- `/api/factura/**` → FACTURACION-SERVICE
- `/api/cliente/**` → CLIENTE-SERVICE
- `/api/proveedor/**` → PROVEEDOR-SERVICE
- `/auth/**` → IDENTITY-SERVICE (público: `/auth/register`, `/auth/token`, `/auth/validate`)

## Comunicación entre servicios (OpenFeign)

- **Repuesto-Service → Proveedor-Service**: valida el proveedor de un repuesto.
- **Facturacion-Service → Taller, Cliente y Repuesto**: al crear un comprobante orquesta:
  1. Verifica que el `Mantenimiento` exista y esté en estado **"Finalizado"**.
  2. Obtiene los datos del `Cliente` (decide BOLETA `B001` si DNI, FACTURA `F001` si RUC).
  3. Calcula correlativo, valida stock de cada repuesto y llama a `disminuirStock`.
  4. Calcula subtotal/IGV (18%)/total y guarda en cascada con sus `DetalleComprobante`.
- **Sunat-Service → Facturacion-Service** (Feign): obtiene la factura para enviarla a SUNAT.
  Flujo en `SunatProcesoService`: generar XML UBL → firmar (X.509) → comprimir ZIP → enviar SOAP.

## Flujo de negocio (end-to-end)

Cliente registrado → ingresa moto a mantenimiento (Taller) → se usan repuestos (descuenta stock)
→ al finalizar se genera el comprobante (Facturacion) → se envía a SUNAT (Sunat-Service).

## Convenciones de código

- Estructura por servicio: `controller/`, `model|modelo|entity/`, `repository/`, `service/`
  (interfaz `IXxxService` + implementación `XxxImplement`), `feignclient|feign/`, `dto|entity/`.
- Entidades JPA con campos en español (`cod_Cliente`, `nomRazSocial`, etc.); ids autogenerados.
- Endpoints REST con verbos en español (`/listarClientes`, `/crearCliente`, `/editarCliente/{id}`).

## Orden de arranque local

1. **EUREKA-Server** (8761) → 2. **Identity-Service** → 3. servicios de dominio
(Cliente, Proveedor, Repuesto, Taller, Facturacion, Sunat) → 4. **GATEWAY-Service** (8080).
Todos se registran en Eureka en `http://localhost:8761/eureka/`.

## ⚠️ Notas importantes

- Las **credenciales de la BD MySQL (Aiven) están hardcodeadas** en cada
  `application.properties`, igual que las credenciales SOL de prueba de SUNAT. Son secretos
  expuestos en el repo — no deberían commitearse a un repo público.
- Los **Feign clients usan URLs `localhost` fijas** (`url="http://localhost:90xx"`) en lugar de
  resolución por nombre vía Eureka/load-balancer; esto rompe el despliegue fuera de local.
- `ddl-auto=update` sobre una **BD compartida** por todos los servicios: cuidado con cambios de
  esquema concurrentes.
- Trabajo reciente (rama `kmendez2`): incorporación de **Sunat-Service** (XML UBL + firma + SOAP).

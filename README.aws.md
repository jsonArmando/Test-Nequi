# Despliegue de API de Franquicias en AWS

Este documento describe cómo desplegar la API de Franquicias en AWS utilizando CloudFormation para la infraestructura como código (IaC).

## Arquitectura en AWS

La aplicación se despliega con la siguiente arquitectura en AWS:

- **Amazon VPC**: Red virtual aislada con subredes públicas y privadas
- **Amazon ECS (Fargate)**: Servicio de contenedores para ejecutar la aplicación sin administrar servidores
- **Amazon DocumentDB**: Base de datos compatible con MongoDB
- **Elastic Load Balancer**: Balanceador de carga para distribuir el tráfico
- **Amazon ECR**: Repositorio de imágenes Docker
- **CloudWatch Logs**: Para monitoreo y logs de la aplicación

## Requisitos previos

1. **Cuenta de AWS**:
   - Debes tener una cuenta en AWS con permisos para crear recursos
   - Si usas una cuenta nueva, podrías estar en el tier gratuito para algunos servicios

2. **AWS CLI**:
   - Instala la [AWS Command Line Interface](https://aws.amazon.com/cli/)
   - Configúrala con credenciales válidas usando `aws configure`

3. **Docker**:
   - Instala [Docker](https://www.docker.com/get-started) en tu máquina local

## Pasos para el despliegue

### 1. Preparar el entorno

Asegúrate de tener todos los archivos necesarios en el directorio de tu proyecto:
- `Dockerfile.aws`: Archivo Docker para la nube
- `application-aws.properties`: Configuración específica para AWS
- `franquicias-stack.yaml`: Plantilla principal de CloudFormation
- `ecr-stack.yaml`: Plantilla de CloudFormation para el repositorio ECR
- `deploy-aws.sh`: Script de despliegue

### 2. Ejecutar el script de despliegue

```bash
# Hacer el script ejecutable (en Linux/Mac)
chmod +x deploy-aws.sh

# Ejecutar el script
./deploy-aws.sh
```

En Windows, si estás usando PowerShell, ejecuta:
```powershell
# Permitir la ejecución del script
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Ejecutar el script con Bash (si tienes Git Bash instalado)
bash deploy-aws.sh
```

### 3. Esperar a que se complete el despliegue

- El despliegue completo puede tardar entre 15 y 30 minutos
- Durante este tiempo, AWS está creando:
  - VPC y subredes
  - Clúster de DocumentDB
  - Repositorio ECR
  - Balanceador de carga
  - Clúster de ECS y servicio

### 4. Verificar el despliegue

Una vez completado el despliegue, el script mostrará la URL para acceder a la aplicación. Puedes visitar:
- `http://franquicias-env-alb-1805609686.us-east-1.elb.amazonaws.com/swagger-ui.html` para la documentación de la API

## Estructura de costos

La solución usa varios servicios de AWS que tienen costos asociados:

| Servicio | Costo aproximado | Notas |
|----------|------------------|-------|
| ECS (Fargate) | ~$30-40/mes | 2 tareas con 0.25 vCPU, 0.5GB RAM |
| DocumentDB | ~$200/mes | 1 instancia db.t3.medium |
| NAT Gateway | ~$35/mes | Incluye transferencia de datos |
| Application Load Balancer | ~$20/mes | |
| ECR | <$1/mes | Para almacenar imágenes Docker |
| CloudWatch Logs | <$1-5/mes | Depende del volumen de logs |

**Total estimado: ~$285-300/mes**

Para reducir costos:
- Detener el servicio ECS cuando no esté en uso
- Usar RDS MongoDB compatible en lugar de DocumentDB
- Reducir el número de instancias o su tamaño

## Limpieza de recursos

Para evitar cargos continuos, elimina todos los recursos cuando ya no los necesites:

```bash
# Eliminar el stack principal
aws cloudformation delete-stack --stack-name franquicias-stack --region us-east-1

# Eliminar el stack de ECR (primero debes eliminar todas las imágenes del repositorio)
aws ecr delete-repository --repository-name franquicias-api --force --region us-east-1
aws cloudformation delete-stack --stack-name franquicias-stack-ecr --region us-east-1
```

## Monitoreo y mantenimiento

### Monitoreo
- Usa CloudWatch para revisar logs y métricas
- Configura alarmas para notificaciones de problemas

### Mantenimiento
- Actualiza la aplicación construyendo y subiendo nuevas imágenes a ECR
- Actualiza el servicio ECS para usar la nueva versión de la imagen

## Solución de problemas

### Problemas comunes y soluciones

1. **El stack falla en la creación**:
   - Revisa los eventos en la consola de CloudFormation
   - Verifica que tienes suficientes recursos en tu cuenta (límites)

2. **La aplicación no es accesible**:
   - Verifica el estado del servicio ECS
   - Revisa los logs de la aplicación en CloudWatch
   - Verifica los grupos de seguridad y reglas de entrada

3. **Errores de conexión a la base de datos**:
   - Verifica que el grupo de seguridad de DocumentDB permite conexiones desde el grupo de seguridad de ECS
   - Asegúrate de que las variables de entorno están configuradas correctamente

## Recursos adicionales

- [Documentación de AWS CloudFormation](https://docs.aws.amazon.com/cloudformation/)
- [Documentación de Amazon ECS](https://docs.aws.amazon.com/ecs/)
- [Documentación de Amazon DocumentDB](https://docs.aws.amazon.com/documentdb/)

# aws cloudformation delete-stack --stack-name franquicias-stack-ecr

# aws ecr delete-repository --repository-name franquicias-api --force